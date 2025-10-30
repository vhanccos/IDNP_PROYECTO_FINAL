package com.example.myapplication001.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication001.ui.components.AppBottomNavigation
import com.example.myapplication001.ui.navigation.Screen
import com.example.myapplication001.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var userInsideMuseum by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("PERUSTEAR") }) },
        bottomBar = { AppBottomNavigation(navController = navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Mapa de Museos: Arequipa", style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(16.dp))
                MapViewPlaceholder()
                Spacer(modifier = Modifier.height(12.dp))
                Text("Escoger su museo favorito haciendo click en este icono", style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController.navigate(Screen.MuseumList.route) }, modifier = Modifier.fillMaxWidth(0.6f)) {
                    Icon(imageVector = Icons.Default.List, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Lista de museos")
                }
            }
            if (userInsideMuseum) {
                PopupCard(
                    museumName = "Museo de Arte Virreinal",
                    onDismiss = { userInsideMuseum = false },
                    onEnter = { userInsideMuseum = false; navController.navigate(Screen.ActiveTour.route) }
                )
            }
        }
    }
}

@Composable
fun MapViewPlaceholder() {
    Box(
        modifier = Modifier.fillMaxWidth(0.85f).height(250.dp).clip(RoundedCornerShape(12.dp)).background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text("Mapa Interactivo (Placeholder)", color = Color.Black)
    }
}

@Composable
fun PopupCard(museumName: String, onDismiss: () -> Unit, onEnter: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(shape = RoundedCornerShape(12.dp)) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Se encuentra adentro de:", style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(4.dp))
                Text(museumName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(16.dp))
                Button(onClick = onEnter, modifier = Modifier.fillMaxWidth(0.8f)) {
                    Text("Ingresar")
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