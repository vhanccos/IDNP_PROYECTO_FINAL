package com.example.myapplication001.ui.screens.events

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication001.domain.model.Event
import com.example.myapplication001.ui.components.AppBottomNavigation
import com.example.myapplication001.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(navController: NavController) {
    var showNotificationCard by remember { mutableStateOf(true) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("EVENTOS Y NOTIFICACIONES") }) },
        bottomBar = { AppBottomNavigation(navController = navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (showNotificationCard) {
                item {
                    NotificationCard { showNotificationCard = false }
                }
            }
            items(Event.sampleData) { event ->
                EventCard(event = event)
            }
        }
    }
}

@Composable
fun EventCard(event: Event) {
    Card(elevation = CardDefaults.cardElevation(4.dp)) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(40.dp))
            Spacer(Modifier.width(16.dp))
            Column {
                Text(event.name, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(event.date, style = MaterialTheme.typography.bodySmall)
                    Text(event.time, style = MaterialTheme.typography.bodySmall)
                }
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(event.location, style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(Modifier.height(4.dp))
                Text("Tipo: ${event.type}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun NotificationCard(onDismiss: () -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.NotificationsActive, contentDescription = null, modifier = Modifier.size(40.dp))
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Recibe Notificaciones", fontWeight = FontWeight.Bold)
                Text("Actívalas para no perderte eventos especiales.", style = MaterialTheme.typography.bodyMedium)
            }
            TextButton(onClick = onDismiss) {
                Text("CERRAR")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventsScreenPreview() {
    MyApplicationTheme {
        EventsScreen(rememberNavController())
    }
}