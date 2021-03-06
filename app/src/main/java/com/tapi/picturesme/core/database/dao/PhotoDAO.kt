package com.tapi.picturesme.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tapi.picturesme.core.database.entity.PhotoEntity


@Dao
interface PhotoDAO {
    @Insert
    suspend fun savePhoto(photoEntity: PhotoEntity)

    @Query("select * from photo")
    suspend fun getListPhoto(): List<PhotoEntity>

    @Delete
    suspend fun deletePhoto(photoEntity: PhotoEntity)


}
