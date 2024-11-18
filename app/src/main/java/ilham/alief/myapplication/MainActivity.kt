package ilham.alief.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Movie
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieIndonesiaApp()
        }
    }
}

@Composable
fun MovieIndonesiaApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "movies",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("movies") { MoviesScreen(navController) }
            composable("grid") { MovieGridScreen(navController) }
            composable("about") { AboutScreen() }
            composable("detail/{movieId}/{source}") { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieId")?.toInt() ?: 0
                val source = backStackEntry.arguments?.getString("source") ?: "Movies"
                DetailScreen(navController, movieId, source)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Movie, contentDescription = "Movies") },
            label = { Text("Movies") },
            selected = false,
            onClick = { navController.navigate("movies") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.GridOn, contentDescription = "Grid") },
            label = { Text("Grid View") },
            selected = false,
            onClick = { navController.navigate("grid") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "About") },
            label = { Text("About") },
            selected = false,
            onClick = { navController.navigate("about") }
        )
    }
}

@Composable
fun MoviesScreen(navController: NavHostController) {
    val movies = listOf(
        "Laskar Pelangi", "Ada Apa Dengan Cinta?", "Habibie & Ainun",
        "Marlina Si Pembunuh Dalam Empat Babak", "Gundala",
        "Pengabdi Setan", "Dilan 1990", "Imperfect",
        "Keluarga Cemara", "Perempuan Tanah Jahanam"
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Film Indonesia") })
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Film Populer", style = TextStyle(fontSize = 20.sp), modifier = Modifier.padding(bottom = 8.dp))
            LazyColumn {
                items(movies.size) { index ->
                    Text(
                        text = movies[index],
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { navController.navigate("detail/$index/Column") }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Rilis Terbaru", style = TextStyle(fontSize = 20.sp), modifier = Modifier.padding(bottom = 8.dp))
            LazyRow {
                items(movies.size) { index ->
                    Text(
                        text = movies[index],
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { navController.navigate("detail/$index/Row") }
                    )
                }
            }
        }
    }
}

@Composable
fun MovieGridScreen(navController: NavHostController) {
    val movies = listOf(
        "Laskar Pelangi", "Ada Apa Dengan Cinta?", "Habibie & Ainun",
        "Marlina Si Pembunuh Dalam Empat Babak", "Gundala",
        "Pengabdi Setan", "Dilan 1990", "Imperfect",
        "Keluarga Cemara", "Perempuan Tanah Jahanam"
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Grid Film") })
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(movies.size) { index ->
                Text(
                    text = movies[index],
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { navController.navigate("detail/$index/Grid") }
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun DetailScreen(navController: NavHostController, movieId: Int, source: String) {
    val movies = listOf(
        "Laskar Pelangi", "Ada Apa Dengan Cinta?", "Habibie & Ainun",
        "Marlina Si Pembunuh Dalam Empat Babak", "Gundala",
        "Pengabdi Setan", "Dilan 1990", "Imperfect",
        "Keluarga Cemara", "Perempuan Tanah Jahanam"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Film") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Film: ${movies[movieId]}", style = TextStyle(fontSize = 20.sp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Sumber: $source")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Deskripsi: Film populer Indonesia.")
        }
    }
}

@Composable
fun AboutScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Tentang Saya") })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Nama: Ilham Alief Fatih")
            Text("Email: ilhamxalif@gmail.com")
            Text("Asal: Institut Teknologi Batam")
            Text("Jurusan: Sistem Informasi")
        }
    }
}
