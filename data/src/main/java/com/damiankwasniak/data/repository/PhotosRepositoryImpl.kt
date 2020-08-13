package com.damiankwasniak.data.repository

import com.damiankwasniak.data.mapper.mapToDbModel
import com.damiankwasniak.data.mapper.mapToDomainModel
import com.damiankwasniak.data.model.PhotoDbModel
import com.damiankwasniak.data.realm.RealmProvider
import com.damiankwasniak.data.utils.SessionManager
import com.damiankwasniak.domain.model.PhotoDomainModel
import com.damiankwasniak.domain.repository.PhotosRepository
import com.damiankwasniak.domain.repository.SecretKeyRepository
import com.damiankwasniak.domain.utils.AESUtils
import com.damiankwasniak.domain.utils.AsyncResult
import com.damiankwasniak.domain.utils.map
import com.damiankwasniak.extensions.photoDao
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class PhotosRepositoryImpl(
    private val realm: RealmProvider,
    private val secretKeyRepository: SecretKeyRepository,
    private val sessionManager: SessionManager
) : PhotosRepository {

    override fun savePhoto(model: PhotoDomainModel): AsyncResult<Boolean> {
        val decryptedKey = getDecryptedKey()
        return realm.photoDao().savePhoto(model.mapToDbModel().encrypt(decryptedKey))
    }

    override fun getPhotos(): AsyncResult<List<PhotoDomainModel>> {
        val decryptedKey = getDecryptedKey()
        return realm.photoDao().getPhotos().map {
            it.map { it.mapToDomainModel().decrypt(decryptedKey) }
        }
    }

    private fun getDecryptedKey(): SecretKey {
        return when (val secretKeyResult = secretKeyRepository.getSecretKey()) {
            is AsyncResult.Success -> {
                if (secretKeyResult.data.isEmpty()) {
                    val key = AESUtils.getRandomKey()
                    val encryptedKey = AESUtils.encrypt(key.encoded, AESUtils.getKeyFromCode(sessionManager.sessionCode))
                    secretKeyRepository.storeSecretKey(encryptedKey)
                    key
                } else {
                    val decryptedKey = AESUtils.decrypt(secretKeyResult.data, AESUtils.getKeyFromCode(sessionManager.sessionCode))
                    SecretKeySpec(decryptedKey, 0, decryptedKey.size, "AES")
                }
            }
            is AsyncResult.Error -> {
                throw Exception("Unable to provide secret key")
            }
        }
    }

}

private fun PhotoDomainModel.decrypt(key: SecretKey): PhotoDomainModel {
    val decryptedPhoto = AESUtils.decrypt(this.byteArray, key)
    return PhotoDomainModel(decryptedPhoto)
}

private fun PhotoDbModel.encrypt(key: SecretKey): PhotoDbModel {
    val encryptedPhoto = AESUtils.encrypt(this.byteArray, key)
    return PhotoDbModel(encryptedPhoto)
}
