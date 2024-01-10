package com.laioffer.spotify.repository

import com.laioffer.spotify.database.DatabaseDao
import com.laioffer.spotify.datamodel.Album
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteAlbumRepository @Inject constructor(private val databaseDao: DatabaseDao) {

    fun isFavoriteAlum(id: Int): Flow<Boolean> =
        databaseDao.isFavoriteAlbum(id).flowOn(Dispatchers.IO)

    suspend fun favoriteAlbum(album: Album) = withContext(Dispatchers.IO) {
        databaseDao.favoriteAlbum(album)
    }

    suspend fun unFavoriteAlbum(album: Album) = withContext(Dispatchers.IO) {
        databaseDao.unFavoriteAlbum(album)
    }

    fun fetchFavoriteAlbums(): Flow<List<Album>> =
        databaseDao.fetchFavoriteAlbums().flowOn(Dispatchers.IO)
}

