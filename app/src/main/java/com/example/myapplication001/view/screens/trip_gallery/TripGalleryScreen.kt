package com.example.myapplication001.view.screens.trip_gallery

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication001.model.Photo
import com.example.myapplication001.view.components.AppBottomNavigation
import com.example.myapplication001.view.components.CommonHeader
import com.example.myapplication001.ui.navigation.Screen
import com.example.myapplication001.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripGalleryScreen(
    navController: NavController, 
    tripName: String?,
    viewModel: com.example.myapplication001.viewmodel.TripGalleryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = com.example.myapplication001.viewmodel.TripGalleryViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(tripName) {
        if (tripName != null) {
            viewModel.loadPhotos(tripName)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { CommonHeader(subtitle = "Aventuras en: ${tripName ?: "Lugar"}") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = { AppBottomNavigation(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: Open Camera */ }) {
                Icon(Icons.Default.CameraAlt, contentDescription = "Añadir foto")
            }
        }
    ) {
        Box(modifier = Modifier.padding(it).fillMaxSize()) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.photos) { photo ->
                        PhotoCard(photo = photo, onClick = {
                            navController.navigate(Screen.GalleryDetail.createRoute(photo.id))
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun PhotoCard(photo: Photo, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(photo.imageUrl).crossfade(true).build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)),
                            startY = 300f
                        )
                    )
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
            ) {
                Text(
                    text = photo.timestamp,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier
                        .padding(8.dp)
                )
                photo.timeHourStamp?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TripGalleryScreenPreview() {
    MyApplicationTheme {
        TripGalleryScreen(rememberNavController(), "Monasterio de Santa Catalina")
    }
}