package com.example.myapplication001.ui.screens.museumdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication001.domain.model.Museum
import com.example.myapplication001.ui.navigation.Screen
import com.example.myapplication001.ui.screens.museumlist.RatingDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MuseumDetailScreen(navController: NavController, museumId: String?) {
    val museum = Museum.sampleData.find { it.id == museumId } ?: Museum.sampleData.first()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "PERUSTEAR",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = buildAnnotatedString {
                                append("Lista de Museos próximos en : ")
                                withStyle(style = SpanStyle(color = Color(0xFFFF6B35))) {
                                    append("Arequipa")
                                }
                            },
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 11.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1C1C1C)
                )
            )
        },
        containerColor = Color(0xFF1C1C1C)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Título del museo
            Text(
                text = museum.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Box contenedor para imagen con botones superpuestos
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(horizontal = 8.dp)
            ) {
                // Imagen principal que ocupa todo el ancho
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(museum.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Imagen de ${museum.name}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4 / 3f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                // Botón Siguiente Imagen superpuesto en la parte derecha
                Button(
                    onClick = { /* TODO: Siguiente imagen */ },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                        .width(85.dp)
                        .fillMaxHeight(0.95f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3A3A3A).copy(alpha = 0.9f)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "SIGUIENTE\nIMAGEN",
                            fontSize = 10.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            lineHeight = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Botón Iniciar Recorrido superpuesto en la parte inferior izquierda
                Button(
                    onClick = { navController.navigate(Screen.ActiveTour.createRoute(museum.id)) },
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 8.dp, bottom = 8.dp)
                        .height(36.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2A2A2A).copy(alpha = 0.85f)
                    ),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Icon(
                        Icons.Default.PlayCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "Iniciar Recorrido",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Rating más visible
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(horizontal = 8.dp)
            ) {
                RatingDisplay(value = museum.ratingValue, count = museum.ratingCount)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Tabs rediseñadas
            MuseumInfoTabs(museum = museum)

            Spacer(modifier = Modifier.height(24.dp))

            // Botón Volver al Mapa
            Button(
                onClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF6B35)
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    "Volver al Mapa",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun MuseumInfoTabs(museum: Museum) {
    var selectedTabIndex by remember { mutableStateOf(1) }
    val tabs = listOf("Entrada", "Reseña", "Información")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(0.9f)
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            containerColor = Color(0xFF2A2A2A),
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = Color(0xFFFF6B35),
                    height = 3.dp
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    modifier = Modifier.height(48.dp),
                    text = {
                        Text(
                            title,
                            color = if (selectedTabIndex == index) Color(0xFFFF6B35) else Color.White,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            when (selectedTabIndex) {
                0 -> Text(
                    "Información sobre la entrada no disponible.",
                    textAlign = TextAlign.Justify,
                    color = Color(0xFFE0E0E0),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
                1 -> Text(
                    museum.description,
                    textAlign = TextAlign.Justify,
                    color = Color(0xFFE0E0E0),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
                2 -> Text(
                    "Información general del museo no disponible.",
                    textAlign = TextAlign.Justify,
                    color = Color(0xFFE0E0E0),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}