package com.damiankwasniak.data.mapper

import com.damiankwasniak.data.model.PhotoDbModel
import com.damiankwasniak.domain.model.PhotoDomainModel

fun PhotoDbModel.mapToDomainModel(): PhotoDomainModel {
    return PhotoDomainModel(
        byteArray = this.byteArray
    )
}

fun PhotoDomainModel.mapToDbModel(): PhotoDbModel {
    return PhotoDbModel(
        byteArray = this.byteArray
    )
}