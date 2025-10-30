package com.example.myapplication001

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication001.ui.navigation.Screen
import com.example.myapplication001.ui.screens.active_tour.ActiveTourScreen
import com.example.myapplication001.ui.screens.events.EventsScreen
import com.example.myapplication001.ui.screens.gallery_detail.GalleryDetailScreen
import com.example.myapplication001.ui.screens.home.HomeScreen
import com.example.myapplication001.ui.screens.museumdetail.MuseumDetailScreen
import com.example.myapplication001.ui.screens.museumlist.MuseumListScreen
import com.example.myapplication001.ui.screens.profile.ProfileScreen
import com.example.myapplication001.ui.screens.trip_gallery.TripGalleryScreen
import com.example.myapplication001.ui.screens.triplist.TripListScreen
import com.example.myapplication001.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.MuseumList.route) {
            MuseumListScreen(navController = navController)
        }
        composable(
            route = Screen.MuseumDetail.route,
            arguments = listOf(navArgument("museumId") { type = NavType.StringType })
        ) { backStackEntry ->
            MuseumDetailScreen(
                navController = navController,
                museumId = backStackEntry.arguments?.getString("museumId")
            )
        }
        composable(Screen.Events.route) {
            EventsScreen(navController = navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screen.TripList.route) {
            TripListScreen(navController = navController)
        }
        composable(
            route = Screen.TripGallery.route,
            arguments = listOf(navArgument("tripName") { type = NavType.StringType })
        ) { backStackEntry ->
            TripGalleryScreen(
                navController = navController,
                tripName = backStackEntry.arguments?.getString("tripName")
            )
        }
        composable(
            route = Screen.GalleryDetail.route,
            arguments = listOf(navArgument("photoId") { type = NavType.StringType })
        ) { backStackEntry ->
            GalleryDetailScreen(
                navController = navController,
                photoId = backStackEntry.arguments?.getString("photoId")
            )
        }
        composable(
            route = Screen.ActiveTour.route,
            arguments = listOf(navArgument("museumId") { type = NavType.StringType })
        ) { backStackEntry ->
            ActiveTourScreen(
                navController = navController,
                museumId = backStackEntry.arguments?.getString("museumId")
            )
        }
    }
}