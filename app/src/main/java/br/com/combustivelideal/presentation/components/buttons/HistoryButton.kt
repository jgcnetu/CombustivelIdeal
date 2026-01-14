package br.com.combustivelideal.presentation.components.buttons

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HistoryButton(
    text: String = "Histórico",
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled)
                MaterialTheme.colorScheme.secondary
            else
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (enabled) 8.dp else 0.dp
        )
    ) {
        Icon(
            imageVector = Icons.Default.History,
            contentDescription = "Ícone de histórico"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }
}