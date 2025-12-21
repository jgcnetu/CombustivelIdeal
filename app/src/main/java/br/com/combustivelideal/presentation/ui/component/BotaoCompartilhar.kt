package br.com.combustivelideal.presentation.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.combustivelideal.R

@Composable
fun BotaoCompartilhar(
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
        painter = painterResource(R.drawable.share),
        contentDescription = "Compartilhar",
        modifier = Modifier.size(20.dp)
    )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Compartilhar")
    }
}