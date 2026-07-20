package com.example.myapplication001.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication001.viewmodel.ThemeViewModel

@Composable
fun CommonHeader(
    subtitle: String? = null,
    titleColor: Color = Color.Unspecified,
    subtitleColor: Color = Color.Unspecified,
    themeViewModel: ThemeViewModel? = null
) {
    // ← Solución: leer el estado solo si el ViewModel existe
    val isDark = themeViewModel?.isDarkTheme?.collectAsState()?.value ?: true

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "PERUSTEAR",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                color = titleColor
            )
            if (!subtitle.isNullOrEmpty()) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    color = subtitleColor
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        if (themeViewModel != null) {
            IconButton(
                onClick = { themeViewModel.toggleTheme() },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = if (isDark) Icons.Default.LightMode
                    else Icons.Default.DarkMode,
                    contentDescription = if (isDark) "Cambiar a claro" else "Cambiar a oscuro",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}