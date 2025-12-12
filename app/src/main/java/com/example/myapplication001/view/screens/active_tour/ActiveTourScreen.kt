package com.example.myapplication001.view.screens.active_tour

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication001.model.Museum
import com.example.myapplication001.view.components.AppBottomNavigation
import com.example.myapplication001.view.components.CommonHeader
import com.example.myapplication001.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveTourScreen(
    navController: NavController,
    museumId: String?,
    viewModel: com.example.myapplication001.viewmodel.ActiveTourViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = com.example.myapplication001.viewmodel.ActiveTourViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(museumId) {
        if (museumId != null) {
            viewModel.loadMuseum(museumId)
        }
    }

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val museum = uiState.museum ?: return

    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(museum.imageUrl).crossfade(true).build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { CommonHeader(subtitle = "Recorrido en: ${museum.name}") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent, titleContentColor = Color.White, navigationIconContentColor = Color.White)
                )
            },
            bottomBar = { AppBottomNavigation(navController = navController) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    modifier = Modifier
                        .align(Alignment.End)
                        .fillMaxWidth(0.6f),
                    colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.6f))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(museum.funFactTitle, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(museum.funFactText, style = MaterialTheme.typography.bodyMedium, color = Color.White)
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Te encuentras en: ${museum.name}", style = MaterialTheme.typography.labelLarge, color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActiveTourScreenPreview() {
    MyApplicationTheme {
        ActiveTourScreen(rememberNavController(), museumId = "1")
    }
}
