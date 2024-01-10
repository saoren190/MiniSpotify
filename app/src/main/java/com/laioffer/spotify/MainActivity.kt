package com.laioffer.spotify

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.room.Room
import coil.compose.AsyncImage
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.laioffer.spotify.database.AppDatabase
import com.laioffer.spotify.database.DatabaseDao
import com.laioffer.spotify.datamodel.Album
import com.laioffer.spotify.network.NetworkApi
import com.laioffer.spotify.player.PlayerBar
import com.laioffer.spotify.player.PlayerViewModel
import com.laioffer.spotify.repository.HomeRepository
import com.laioffer.spotify.ui.theme.SpotifyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var api: NetworkApi
    @Inject
    lateinit var databaseDao: DatabaseDao
    private val playerViewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // findElementById("")
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph)
        NavigationUI.setupWithNavController(navView, navController)
        navView.setOnItemSelectedListener {
            NavigationUI.onNavDestinationSelected(it, navController)
            navController.popBackStack(it.itemId, inclusive = false)
            true
        }
        val playerBar = findViewById<ComposeView>(R.id.player_bar)
        playerBar.apply {
            setContent {
                MaterialTheme(colors = darkColors()) {
                    PlayerBar(
                        playerViewModel
                    )
                }
            }
        }

        // Test retrofit
        GlobalScope.launch(Dispatchers.IO) {
            //val api = NetworkModule.provideRetrofit().create(NetworkApi::class.java)
            val response = api.getHomeFeed().execute().body()
            Log.d("Network", response.toString())
        }

        // remember it runs everytime you start the app
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val album = Album(
                    id = 1,
                    name =  "Hexagonal",
                    year = "2008",
                    cover = "https://upload.wikimedia.org/wikipedia/en/6/6d/Leessang-Hexagonal_%28cover%29.jpg",
                    artists = "Lesssang",
                    description = "Leessang (Korean: 리쌍) was a South Korean hip hop duo, composed of Kang Hee-gun (Gary or Garie) and Gil Seong-joon (Gil)"
                )
                databaseDao.favoriteAlbum(album)
            }
        }

    }
}

@Composable
private fun LoadingSection(text: String) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            color = Color.White
        )
    }
}

@Composable
fun AlbumCover() {
    Column() {
        Box(modifier = Modifier.size(160.dp)) {
            AsyncImage(
                model = "https://upload.wikimedia.org/wikipedia/en/d/d1/Stillfantasy.jpg",
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            Text( // ctrl + J
                text = "Still Fantasy",
                color = Color.White,
                modifier = Modifier.padding(bottom = 4.dp, start = 2.dp).align(Alignment.BottomStart)
            )
        }
        Text(
            text = "jay Chou",
            modifier = Modifier.padding(top = 4.dp),
            style=MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
            color = Color.White)
    }
}


@Preview(showBackground = true, name = "Preview LoadingSection")
@Composable
fun DefaultPreview() {
    SpotifyTheme {
        Surface {
            AlbumCover()
        }
    }
}

