package com.example.myapplication001.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication001.domain.model.User
import com.example.myapplication001.ui.components.AppBottomNavigation
import com.example.myapplication001.ui.navigation.Screen
import com.example.myapplication001.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val user = User.sample
    Scaffold(
        topBar = { TopAppBar(title = { Text("Cuenta, Información General") }) },
        bottomBar = { AppBottomNavigation(navController = navController) }
    ) {
        Column(
            modifier = Modifier.padding(it).fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileHeader(user)
            Spacer(Modifier.height(24.dp))
            StatsRow(user)
            Spacer(Modifier.height(24.dp))
            SettingsCard()
            Spacer(Modifier.height(16.dp))
            Button(onClick = { navController.navigate(Screen.TripList.route) }) {
                Text("Mis Aventuras")
            }
            Spacer(Modifier.height(32.dp))
            Text(
                text = "✨ Cada lugar que visitas guarda una historia... y tú eres parte de ella. ✨",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ProfileHeader(user: User) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = rememberVectorPainter(Icons.Default.Person), // Placeholder
            contentDescription = "Avatar",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )
        Spacer(Modifier.height(8.dp))
        Text(user.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Arequipa, Perú", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
    }
}

@Composable
fun StatsRow(user: User) {
    Row(horizontalArrangement = Arrangement.spacedBy(40.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(user.placesVisited.toString(), style = MaterialTheme.typography.headlineMedium)
            Text("Lugares", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(user.totalVisits.toString(), style = MaterialTheme.typography.headlineMedium)
            Text("Visitas", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
    }
}

@Composable
fun SettingsCard() {
    Card(modifier = Modifier.fillMaxWidth(0.9f)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Configuración", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            SettingsItem("Notificación", "Activar")
            Divider()
            SettingsItem("Ubicación", "Permitir")
            Divider()
            SettingsItem("Idioma", "Español")
        }
    }
}

@Composable
fun SettingsItem(label: String, buttonText: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(label, modifier = Modifier.weight(1f))
        Button(onClick = { /*TODO*/ }) {
            Text(buttonText)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MyApplicationTheme {
        ProfileScreen(rememberNavController())
    }
}