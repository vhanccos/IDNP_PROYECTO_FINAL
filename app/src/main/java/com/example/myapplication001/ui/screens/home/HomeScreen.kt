package com.example.myapplication001.ui.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication001.ui.components.AppBottomNavigation
import com.example.myapplication001.ui.components.CommonHeader
import com.example.myapplication001.ui.navigation.Screen
import com.example.myapplication001.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var showPopup by remember { mutableStateOf(false) }
    var activeMuseumName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CommonHeader(subtitle = "Mapa de Museos")
        },
        bottomBar = {
            AppBottomNavigation(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // ===== Mapa simulado =====
            SimulatedMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                onMuseumClick = { museum ->
                    activeMuseumName = museum
                    showPopup = true
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ===== Texto guía y botón =====
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Escoger su museo favorito haciendo click en este icono",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { navController.navigate(Screen.MuseumList.route) },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(46.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Icon(imageVector = Icons.Filled.List, contentDescription = null)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Lista de museos")
                }
            }
        }

        // ===== Popup =====
        if (showPopup) {
            MuseumPopup(
                museumName = activeMuseumName,
                onDismiss = { showPopup = false },
                onEnter = {
                    showPopup = false
                    navController.navigate(Screen.ActiveTour.route)
                }
            )
        }
    }
}

/**
 * Simulación de un mapa con marcadores interactivos.
 */
@Composable
fun SimulatedMap(modifier: Modifier = Modifier, onMuseumClick: (String) -> Unit) {
    val density = LocalDensity.current
    val gridStepPx = with(density) { 40.dp.toPx() }
    val gridColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        // Fondo de cuadrícula tipo mapa
        Canvas(modifier = Modifier.matchParentSize()) {
            var x = 0f
            while (x <= size.width) {
                drawLine(gridColor, start = Offset(x, 0f), end = Offset(x, size.height), strokeWidth = 1f)
                x += gridStepPx
            }
            var y = 0f
            while (y <= size.height) {
                drawLine(gridColor, start = Offset(0f, y), end = Offset(size.width, y), strokeWidth = 1f)
                y += gridStepPx
            }
        }

        // Marcadores fijos
        Box(modifier = Modifier.fillMaxSize()) {
            val markers = listOf(
                Triple("Museo de Arte Virreinal", 0.25f, 0.4f),
                Triple("Museo Santuarios Andinos", 0.6f, 0.6f),
                Triple("Museo de la Catedral", 0.8f, 0.3f)
            )

            markers.forEach { (name, relX, relY) ->
                RelativeMarker(
                    name = name,
                    relX = relX,
                    relY = relY,
                    onClick = onMuseumClick
                )
            }
        }
    }
}

/**
 * Coloca un marcador en posición relativa dentro de su padre.
 */
@Composable
fun RelativeMarker(name: String, relX: Float, relY: Float, onClick: (String) -> Unit) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val parentWidthDp = maxWidth
        val parentHeightDp = maxHeight

        val offsetXDp = (parentWidthDp * relX) - 26.dp
        val offsetYDp = (parentHeightDp * relY) - 26.dp

        Box(
            modifier = Modifier
                .offset(x = offsetXDp, y = offsetYDp)
                .size(52.dp)
                .clip(RoundedCornerShape(26.dp))
                .background(MaterialTheme.colorScheme.primary)
                .clickable { onClick(name) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = name,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

/** Popup para "Se encuentra adentro de:" */
@Composable
fun MuseumPopup(museumName: String, onDismiss: () -> Unit, onEnter: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(0.85f),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Se encuentra adentro de:",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = museumName,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                )
                Spacer(modifier = Modifier.height(14.dp))
                Button(
                    onClick = onEnter,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(44.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = "Ingresar")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MyApplicationTheme {
        HomeScreen(navController = rememberNavController())
    }
}
