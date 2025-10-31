package com.example.myapplication001.ui.screens.museumdetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.myapplication001.domain.model.Museum
import com.example.myapplication001.ui.components.CommonHeader
import com.example.myapplication001.ui.navigation.Screen
import com.example.myapplication001.ui.screens.museumlist.RatingDisplay
import com.example.myapplication001.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MuseumDetailScreen(navController: NavController, museumId: String?) {
    val museum = Museum.sampleData.find { it.id == museumId } ?: Museum.sampleData.first()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { CommonHeader(subtitle = museum.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(museum.imageUrl).crossfade(true).build(),
                    contentDescription = "Imagen de ${museum.name}",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(16 / 9f)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { /*TODO*/ }) {
                    Text("SIGUIENTE IMAGEN")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = { navController.navigate(Screen.ActiveTour.createRoute(museum.id)) }) {
                    Text("Iniciar Recorrido")
                }
                RatingDisplay(value = museum.ratingValue, count = museum.ratingCount)
            }

            Spacer(modifier = Modifier.height(16.dp))

            MuseumInfoTabs(museum = museum)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate(Screen.Home.route) { popUpTo(Screen.Home.route) { inclusive = true } } },
                modifier = Modifier.fillMaxWidth(0.65f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Map, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Volver al Mapa")
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun MuseumInfoTabs(museum: Museum) {
    var selectedTabIndex by remember { mutableStateOf(1) }
    val tabs = listOf("Entrada", "Reseña", "Información")

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.padding(horizontal = 24.dp)) {
            when (selectedTabIndex) {
                0 -> Text("Información sobre la entrada no disponible.", textAlign = TextAlign.Justify)
                1 -> Text(museum.description, textAlign = TextAlign.Justify, style = MaterialTheme.typography.bodyLarge)
                2 -> Text("Información general del museo no disponible.", textAlign = TextAlign.Justify)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MuseumDetailScreenPreview() {
    MyApplicationTheme {
        MuseumDetailScreen(navController = rememberNavController(), museumId = "1")
    }
}