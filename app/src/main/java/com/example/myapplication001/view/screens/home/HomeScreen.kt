package com.example.myapplication001.view.screens.home

import android.preference.PreferenceManager
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication001.view.components.AppBottomNavigation
import com.example.myapplication001.view.components.CommonHeader
import com.example.myapplication001.view.navigation.Screen
import com.example.myapplication001.viewmodel.HomeViewModel
// Imports de OpenStreetMap
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    // Aquí inyectamos el ViewModel que creamos arriba (SIN repository)
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Configuración obligatoria para OSM
    LaunchedEffect(Unit) {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
        Configuration.getInstance().userAgentValue = context.packageName
    }

    var mapController by remember { mutableStateOf<MapView?>(null) }

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
            // MAPA
            AndroidView(
                factory = { ctx ->
                    MapView(ctx).apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)
                        controller.setZoom(15.0)

                        // Usamos la coordenada del ViewModel
                        controller.setCenter(viewModel.arequipaCenterLocation)
                        mapController = this
                    }
                },
                modifier = Modifier.fillMaxSize(),
                update = { map ->
                    map.overlays.clear()

                    // Obtenemos la lista desde el ViewModel (que tiene los datos fijos)
                    uiState.museums.forEach { museum ->
                        // Pedimos la ubicación al ViewModel
                        val location = viewModel.getMuseumLocation(museum.id)

                        val marker = Marker(map)
                        marker.position = location
                        marker.title = museum.name
                        marker.snippet = museum.infoText
                        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                        marker.setOnMarkerClickListener { m, _ ->
                            m.showInfoWindow()
                            viewModel.onMuseumClick(museum)
                            true
                        }
                        map.overlays.add(marker)
                    }
                    map.invalidate()
                }
            )

            // CONTROLES UI
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FloatingActionButton(
                    onClick = {
                        mapController?.controller?.animateTo(viewModel.arequipaCenterLocation)
                        mapController?.controller?.setZoom(15.0)
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

                Text(
                    text = "Toca un marcador para ver detalles",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 4.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )

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
                    Icon(imageVector = Icons.AutoMirrored.Filled.List, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Ver Lista", fontWeight = FontWeight.Bold)
                }
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        // DIÁLOGO
        if (uiState.showMuseumDialog && uiState.selectedMuseum != null) {
            MuseumInfoDialog(
                museum = uiState.selectedMuseum!!,
                onDismiss = { viewModel.dismissMuseumDialog() },
                onNavigateToDetail = {
                    viewModel.dismissMuseumDialog()
                    navController.navigate(Screen.MuseumDetail.createRoute(uiState.selectedMuseum!!.id))
                },
                onStartTour = {
                    viewModel.dismissMuseumDialog()
                    navController.navigate(Screen.ActiveTour.createRoute(uiState.selectedMuseum!!.id))
                }
            )
        }
    }
}

@Composable
fun MuseumInfoDialog(
    museum: com.example.myapplication001.model.Museum,
    onDismiss: () -> Unit,
    onNavigateToDetail: () -> Unit,
    onStartTour: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = museum.name, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = museum.description.take(150) + "...")

                // NOTA: Usamos HorizontalDivider que es el nuevo nombre de Divider
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = museum.infoText,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (museum.infoText.contains("Abierto")) Color(0xFF4CAF50) else Color(0xFFF44336),
                        fontWeight = FontWeight.Bold
                    )
                    Row {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFC107),
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
            Button(onClick = onNavigateToDetail) { Text("Ver Detalles") }
        },
        dismissButton = {
            OutlinedButton(onClick = onStartTour) { Text("Iniciar Recorrido") }
        }
    )
}
