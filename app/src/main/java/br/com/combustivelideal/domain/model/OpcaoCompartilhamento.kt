package br.com.combustivelideal.domain.model

import androidx.compose.ui.graphics.painter.Painter

data class OpcaoCompartilhamento(
    val id: String,
    val nome: String,
    val icone: Painter,
    val descricao: String
)