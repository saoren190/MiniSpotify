package com.laioffer.spotify.database

import androidx.room.*
import com.laioffer.spotify.datamodel.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun favoriteAlbum(album: Album)

    @Query("SELECT EXISTS(SELECT * FROM Album WHERE id = :id)")
    fun isFavoriteAlbum(id : Int) : Flow<Boolean>

    @Delete
    suspend fun unFavoriteAlbum(album: Album)


    @Query("select * from Album")
    fun fetchFavoriteAlbums(): Flow<List<Album>>

}

