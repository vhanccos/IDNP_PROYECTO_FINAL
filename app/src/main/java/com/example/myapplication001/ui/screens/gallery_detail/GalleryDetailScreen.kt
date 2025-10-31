package com.example.myapplication001.ui.screens.gallery_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication001.domain.model.Photo
import com.example.myapplication001.ui.components.AppBottomNavigation
import com.example.myapplication001.ui.components.CommonHeader
import com.example.myapplication001.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryDetailScreen(navController: NavController, photoId: String?) {
    val photo = Photo.sampleData.find { it.id == photoId } ?: Photo.sampleData.first()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { CommonHeader(subtitle = "Detalle de Foto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = { AppBottomNavigation(navController = navController) }
    ) {
        Box(
            modifier = Modifier.padding(it).fillMaxSize().padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(photo.imageUrl).crossfade(true).build(),
                    contentDescription = "Foto detallada",
                    modifier = Modifier.clip(RoundedCornerShape(12.dp)).fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
                Row(modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Download, contentDescription = "Descargar")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Share, contentDescription = "Compartir")
                    }
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.GridView, contentDescription = "Vista de Galería")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GalleryDetailScreenPreview() {
    MyApplicationTheme {
        GalleryDetailScreen(rememberNavController(), "1")
    }
}