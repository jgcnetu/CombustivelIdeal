package br.com.combustivelideal.presentation.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp

@Composable
fun ClearButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var shake by remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = {
            shake = true
            onClick()
        },
        modifier = modifier.shake(
            trigger = shake,
            onShakeEnd = { shake = false }
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.error
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = SolidColor(MaterialTheme.colorScheme.error)
        )
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Limpar"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }
}