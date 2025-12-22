package br.com.combustivelideal.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.combustivelideal.R

@Composable
fun CabecalhoApp() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {

        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource( R.drawable.local_gas_station ),
                contentDescription = "Combustível",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            ) //R.drawable.local_gas_station

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "COMBUSTÍVEL IDEAL",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            ) //COMBUSTÍVEL IDEAL

            Text(
                text = "Descubra qual vale mais a pena!",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            ) //Descubra qual vale mais a pena!
        }
    }
}