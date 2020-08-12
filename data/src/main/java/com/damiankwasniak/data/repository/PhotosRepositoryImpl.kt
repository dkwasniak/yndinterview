package com.damiankwasniak.data.repository

import com.damiankwasniak.data.mapper.mapToDbModel
import com.damiankwasniak.data.mapper.mapToDomainModel
import com.damiankwasniak.data.model.PhotoDbModel
import com.damiankwasniak.data.realm.RealmProvider
import com.damiankwasniak.data.utils.Encrypter
import com.damiankwasniak.data.utils.SessionManager
import com.damiankwasniak.domain.model.PhotoDomainModel
import com.damiankwasniak.domain.repository.PhotosRepository
import com.damiankwasniak.domain.utils.AESUtils
import com.damiankwasniak.domain.utils.AsyncResult
import com.damiankwasniak.domain.utils.map
import com.damiankwasniak.extensions.photoDao
import javax.crypto.SecretKey

class PhotosRepositoryImpl(
    private val realm: RealmProvider,
    private val encrypter: Encrypter,
    private val sessionManager: SessionManager
) : PhotosRepository {

    override fun savePhoto(model: PhotoDomainModel): AsyncResult<Boolean> {
        val decryptedKey = encrypter.getDecryptedKey(sessionManager.sessionCode)
        return realm.photoDao().savePhoto(model.mapToDbModel().encrypt(decryptedKey))
    }

    override fun getPhotos(): AsyncResult<List<PhotoDomainModel>> {
        val decryptedKey = encrypter.getDecryptedKey(sessionManager.sessionCode)
        return realm.photoDao().getPhotos().map {
            it.map { it.mapToDomainModel().decrypt(decryptedKey) }
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
