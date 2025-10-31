package com.example.myapplication001.ui.screens.triplist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication001.domain.model.Trip
import com.example.myapplication001.ui.components.AppBottomNavigation
import com.example.myapplication001.ui.components.CommonHeader
import com.example.myapplication001.ui.navigation.Screen
import com.example.myapplication001.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripListScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { CommonHeader(subtitle = "Mis Aventuras") }) },
        bottomBar = { AppBottomNavigation(navController = navController) }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(it).fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(Trip.sampleData) { trip ->
                TripCard(trip = trip, onClick = { navController.navigate(Screen.TripGallery.createRoute(trip.name)) })
            }
        }
    }
}

@Composable
fun TripCard(trip: Trip, onClick: () -> Unit) {
    Card(elevation = CardDefaults.cardElevation(4.dp), modifier = Modifier.clickable(onClick = onClick)) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(trip.imageUrl).crossfade(true).build(),
                contentDescription = trip.name,
                modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Column(Modifier.padding(8.dp)) {
                Text(trip.name, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Text(trip.description, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TripListScreenPreview() {
    MyApplicationTheme {
        TripListScreen(rememberNavController())
    }
}