package com.example.myapplication001.view.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication001.view.components.AppBottomNavigation
import com.example.myapplication001.view.components.CommonHeader
import com.example.myapplication001.ui.navigation.Screen
// Imports de Google Maps
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    // Configuración de la Cámara de Google Maps
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(viewModel.arequipaCenterLocation, 15f)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { CommonHeader(subtitle = "Mapa de Museos") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            AppBottomNavigation(navController = navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // --------------------------------------------------
            // COMPONENTE GOOGLE MAPS
            // --------------------------------------------------
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    isMyLocationEnabled = false,
                    mapType = MapType.NORMAL
                ),
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false, // Ocultamos controles por defecto para usar los nuestros
                    myLocationButtonEnabled = false,
                    mapToolbarEnabled = false
                )
            ) {
                // Dibujamos los marcadores (Pines Rojos por defecto)
                uiState.museums.forEach { museum ->
                    val position = viewModel.getMuseumLocation(museum.id)

                    Marker(
                        state = MarkerState(position = position),
                        title = museum.name,
                        snippet = museum.infoText,
                        onClick = {
                            viewModel.onMuseumClick(museum)
                            false // Devolver false para que ocurra el comportamiento por defecto (centrar y mostrar info)
                        }
                    )
                }
            }

            // --------------------------------------------------
            // CONTROLES UI (Botones Flotantes)
            // --------------------------------------------------
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Botón de centrar ubicación
                FloatingActionButton(
                    onClick = {
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(
                            viewModel.arequipaCenterLocation,
                            15f
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = "Centrar mapa",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                // Texto guía
                Text(
                    text = "Toca un marcador para ver detalles",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 4.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Botón para ir a la lista
                Button(
                    onClick = { navController.navigate(Screen.MuseumList.route) },
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Ver Lista de Museos",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Indicador de carga
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        // Diálogo de detalles del museo
        if (uiState.showMuseumDialog && uiState.selectedMuseum != null) {
            MuseumInfoDialog(
                museum = uiState.selectedMuseum!!,
                onDismiss = { viewModel.dismissMuseumDialog() },
                onNavigateToDetail = {
                    viewModel.dismissMuseumDialog()
                    navController.navigate(
                        Screen.MuseumDetail.createRoute(uiState.selectedMuseum!!.id)
                    )
                },
                onStartTour = {
                    viewModel.dismissMuseumDialog()
                    navController.navigate(
                        Screen.ActiveTour.createRoute(uiState.selectedMuseum!!.id)
                    )
                }
            )
        }
    }
}

// Componente del Diálogo (Sin cambios, solo imports correctos)
@Composable
fun MuseumInfoDialog(
    museum: com.example.myapplication001.model.Museum,
    onDismiss: () -> Unit,
    onNavigateToDetail: () -> Unit,
    onStartTour: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = museum.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = museum.description.take(150) + "...",
                    style = MaterialTheme.typography.bodyMedium
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = museum.infoText,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (museum.infoText.contains("Abierto")) {
                            MaterialTheme.colorScheme.tertiary
                        } else {
                            MaterialTheme.colorScheme.error
                        },
                        fontWeight = FontWeight.Bold
                    )

                    Row {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${museum.ratingValue} (${museum.ratingCount})",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onNavigateToDetail,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Ver Detalles")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onStartTour) {
                Text("Iniciar Recorrido")
            }
        }
    )
}
