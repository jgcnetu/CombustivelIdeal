package com.example.combustivelideal.ui.componente

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.combustivelideal.modelo.ResultadoCalculo

@Composable
fun BotaoCompartilhar(
    resultado: ResultadoCalculo,
    onCompartilharClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onCompartilharClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        )
    ) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = "Compartilhar",
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text("Compartilhar")
    }
}

@Composable
fun MenuFlutuanteCompartilhar(
    resultado: ResultadoCalculo,
    onCompartilharTexto: (String) -> Unit,
    onCompartilharWhatsApp: (String) -> Unit,
    onCopiarTexto: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expandido by remember { mutableStateOf(false) }
    val contexto = LocalContext.current

    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = { expandido = true },
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Compartilhar"
            )
        }

        DropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            DropdownMenuItem(
                text = { Text("Compartilhar Texto") },
                onClick = {
                    onCompartilharTexto(resultado.gerarTextoCompartilhamento())
                    expandido = false
                },
                leadingIcon = {
                    Icon(Icons.Default.Share, contentDescription = null)
                }
            )

            DropdownMenuItem(
                text = { Text("WhatsApp") },
                onClick = {
                    onCompartilharWhatsApp(resultado.gerarTextoWhatsApp())
                    expandido = false
                },
                leadingIcon = {
                    Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null)
                }
            )

            DropdownMenuItem(
                text = { Text("Copiar Texto") },
                onClick = {
                    onCopiarTexto(resultado.gerarTextoCompartilhamento())
                    expandido = false
                },
                leadingIcon = {
                    Icon(Icons.Default.ContentCopy, contentDescription = null)
                }
            )
        }
    }
}