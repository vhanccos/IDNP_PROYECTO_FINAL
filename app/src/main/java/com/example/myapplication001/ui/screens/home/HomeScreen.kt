package com.example.myapplication001.ui.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication001.ui.navigation.Screen
import com.example.myapplication001.ui.theme.MyApplicationTheme

/**
 * HomeScreen completo con mapa simulado (sin usar APIs externas)
 * Corregido: .weight se aplica desde HomeScreen (Column scope) y SimulatedMap recibe modifier.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var showPopup by remember { mutableStateOf(false) }
    var activeMuseumName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0B0B0B)),
                modifier = Modifier.height(8.dp)
            )
        },
        bottomBar = {
            BottomNavBar(active = "home") { route ->
                when (route) {
                    "eventos" -> navController.navigate(Screen.Events.route)
                    "inicio" -> navController.navigate(Screen.Home.route)
                    "perfil" -> navController.navigate(Screen.Profile.route)
                }
            }
        },
        containerColor = Color(0xFF000000)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // ===== Recuadro PERUSTEAR =====
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 12.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF1B1B1B))
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "PERUSTEAR",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ===== Subtítulos =====
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Mapa de Museos:",
                    color = Color(0xFFFFA500),
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Arequipa",
                    color = Color.White,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ===== Mapa simulado =====
            // Aquí aplicamos weight en el ColumnScope (CORRECCIÓN)
            SimulatedMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // <-- correcto: weight usado desde el scope padre
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
                    color = Color(0xFFBDBDBD),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { navController.navigate(Screen.MuseumList.route) },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(46.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Icon(imageVector = Icons.Filled.List, contentDescription = null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Lista de museos", color = Color.Black)
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
 * Ahora acepta un modifier pasado desde el padre (para poder usar .weight allí).
 */
@Composable
fun SimulatedMap(modifier: Modifier = Modifier, onMuseumClick: (String) -> Unit) {
    // Para convertir dp a px dentro del Canvas
    val density = LocalDensity.current
    val gridStepPx = with(density) { 40.dp.toPx() }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF121212)),
        contentAlignment = Alignment.Center
    ) {
        // Fondo de cuadrícula tipo mapa
        Canvas(modifier = Modifier.matchParentSize()) {
            // Dibuja líneas verticales y horizontales con separación gridStepPx
            var x = 0f
            while (x <= size.width) {
                drawLine(Color(0xFF2A2A2A), start = Offset(x, 0f), end = Offset(x, size.height), strokeWidth = 1f)
                x += gridStepPx
            }
            var y = 0f
            while (y <= size.height) {
                drawLine(Color(0xFF2A2A2A), start = Offset(0f, y), end = Offset(size.width, y), strokeWidth = 1f)
                y += gridStepPx
            }
        }

        // Marcadores fijos: colocamos en una capa superior para que sean interactivos
        Box(modifier = Modifier.fillMaxSize()) {
            // Marker positions (relX, relY between 0f..1f)
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
 * Usamos BoxWithConstraints para calcular offsets en dp (basado en tamaño real del contenedor).
 */
@Composable
fun RelativeMarker(name: String, relX: Float, relY: Float, onClick: (String) -> Unit) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        // dimensiones del padre en dp
        val parentWidthDp = maxWidth
        val parentHeightDp = maxHeight

        // calcular offset en dp; restamos la mitad del tamaño del marcador (26.dp) para centrarlo
        val offsetXDp = (parentWidthDp * relX) - 26.dp
        val offsetYDp = (parentHeightDp * relY) - 26.dp

        Box(
            modifier = Modifier
                .offset(x = offsetXDp, y = offsetYDp)
                .size(52.dp)
                .clip(RoundedCornerShape(26.dp))
                .background(Color(0xFFD32F2F))
                .clickable { onClick(name) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = name,
                tint = Color.White,
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
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Se encuentra adentro de:",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF333333)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = museumName,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF111111)
                )
                Spacer(modifier = Modifier.height(14.dp))
                Button(
                    onClick = onEnter,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(44.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6A00)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = "Ingresar", color = Color.White)
                }
            }
        }
    }
}

/** Barra inferior simple */
@Composable
fun BottomNavBar(active: String, onNavigate: (route: String) -> Unit) {
    Surface(
        color = Color(0xFF0B0B0B),
        tonalElevation = 4.dp,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onNavigate("eventos") }) {
                Icon(
                    imageVector = Icons.Filled.List,
                    contentDescription = "Eventos",
                    tint = if (active == "eventos") Color(0xFFFFA500) else Color(0xFF9E9E9E)
                )
            }

            IconButton(onClick = { onNavigate("inicio") }) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Inicio",
                    tint = if (active == "home") Color(0xFFFFA500) else Color(0xFF9E9E9E),
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(onClick = { onNavigate("perfil") }) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Perfil",
                    tint = if (active == "perfil") Color(0xFFFFA500) else Color(0xFF9E9E9E)
                )
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
