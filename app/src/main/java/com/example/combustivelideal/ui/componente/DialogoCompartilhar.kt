package com.example.combustivelideal.ui.componente

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.combustivelideal.modelo.ResultadoCalculo

data class OpcaoCompartilhamento(
    val id: String,
    val nome: String,
    val icone: ImageVector,
    val descricao: String
)

@Composable
fun DialogoCompartilhamento(
    resultado: ResultadoCalculo?,
    onCompartilharTexto: (String) -> Unit,
    onCompartilharWhatsApp: (String) -> Unit,
    onCopiarTexto: (String) -> Unit,
    onSalvarImagem: (String) -> Unit,
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
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Opções de compartilhamento
                    val opcoes = listOf(
                        OpcaoCompartilhamento(
                            id = "texto",
                            nome = "Compartilhar Texto",
                            icone = Icons.Default.Share,
                            descricao = "Compartilhe em qualquer app"
                        ),
                        OpcaoCompartilhamento(
                            id = "whatsapp",
                            nome = "WhatsApp",
                            icone = Icons.AutoMirrored.Filled.Chat,
                            descricao = "Compartilhe no WhatsApp"
                        ),
                        OpcaoCompartilhamento(
                            id = "copiar",
                            nome = "Copiar Texto",
                            icone = Icons.Default.ContentCopy,
                            descricao = "Copie para a área de transferência"
                        ),
                        OpcaoCompartilhamento(
                            id = "imagem",
                            nome = "Gerar Imagem",
                            icone = Icons.Default.Image,
                            descricao = "Crie uma imagem para compartilhar"
                        )
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        opcoes.forEach { opcao ->
                            OpcaoCompartilhamentoItem(
                                opcao = opcao,
                                onClick = {
                                    when (opcao.id) {
                                        "texto" -> onCompartilharTexto(resultado.gerarTextoCompartilhamento())
                                        "whatsapp" -> onCompartilharWhatsApp(resultado.gerarTextoWhatsApp())
                                        "copiar" -> onCopiarTexto(resultado.gerarTextoCompartilhamento())
                                        "imagem" -> onSalvarImagem(resultado.gerarTextoImagem())
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
                imageVector = opcao.icone,
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
                )

                Text(
                    text = opcao.descricao,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Selecionar",
                tint = MaterialTheme.colorScheme.outline
            )
        }
    }
}