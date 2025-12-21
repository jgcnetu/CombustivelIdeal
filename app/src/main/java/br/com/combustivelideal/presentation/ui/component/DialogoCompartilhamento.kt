package br.com.combustivelideal.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import br.com.combustivelideal.R
import br.com.combustivelideal.domain.model.OpcaoCompartilhamento
import br.com.combustivelideal.domain.model.ResultadoCalculo

@Composable
fun DialogoCompartilhamento(
    resultado: ResultadoCalculo?,
    onCompartilharTexto: (String) -> Unit,
    onCompartilharWhatsApp: (String) -> Unit,
    onCopiarTexto: (String) -> Unit,
    onFechar: () -> Unit
) {
    if (resultado != null) {
        Dialog(onDismissRequest = onFechar) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Título
                    Text(
                        text = "Compartilhar Resultado",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ) //Compartilhar Resultado

                    Spacer(modifier = Modifier.height(16.dp))

                    // Opções de compartilhamento
                    val opcoes = listOf(
                        OpcaoCompartilhamento(
                            id = "texto",
                            nome = "Compartilhar Texto",
                            icone = painterResource( R.drawable.share ),
                            descricao = "Compartilhe em qualquer app"
                        ), //texto
                        OpcaoCompartilhamento(
                            id = "whatsapp",
                            nome = "WhatsApp",
                            icone = painterResource( R.drawable.chat_bubble ),
                            descricao = "Compartilhe no WhatsApp"
                        ), //whatsapp
                        OpcaoCompartilhamento(
                            id = "copiar",
                            nome = "Copiar Texto",
                            icone = painterResource( R.drawable.content_copy ),
                            descricao = "Copie para a área de transferência"
                        ), //copiar
                        OpcaoCompartilhamento(
                            id = "imagem",
                            nome = "Gerar Imagem",
                            icone = painterResource( R.drawable.image ),
                            descricao = "Crie uma imagem para compartilhar"
                        ) //imagem
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        opcoes.forEach { opcao ->
                            OpcaoCompartilhamentoItem(
                                opcao = opcao,
                                onClick = {
                                    when (opcao.id) {
                                        "texto" -> onCompartilharTexto( resultado.gerarTextoCompartilhamento() )
                                        "whatsapp" -> onCompartilharWhatsApp( resultado.gerarTextoWhatsApp() )
                                        "copiar" -> onCopiarTexto( resultado.gerarTextoCompartilhamento() )
                                    }
                                    onFechar()
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botão de cancelar
                    TextButton(
                        onClick = onFechar,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cancelar")
                    }
                }
            }
        }
    }
}

@Composable
private fun OpcaoCompartilhamentoItem(
    opcao: OpcaoCompartilhamento,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                painter = opcao.icone,
                contentDescription = opcao.nome,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = opcao.nome,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                ) //nome

                Text(
                    text = opcao.descricao,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ) //descricao
            }

            Icon(
                painter = painterResource( R.drawable.double_arrow ),
                contentDescription = "Selecionar",
                tint = MaterialTheme.colorScheme.outline
            ) //Selecionar
        }
    }
}