package com.example.combustivelideal.util

import java.text.NumberFormat
import java.util.Locale

object FormatadorUtil {

    // Locale brasileiro
    private val localidadeBR = Locale("pt", "BR")

    // Formatador de moeda
    private val formatadorMoeda = NumberFormat.getCurrencyInstance(localidadeBR)

    // Formatador de porcentagem
    private val formatadorPorcentagem = NumberFormat.getPercentInstance(localidadeBR).apply {
        maximumFractionDigits = 1
        minimumFractionDigits = 1
    }

    // Converte string para double, aceitando vírgula ou ponto como separador decimal
    fun stringParaDouble(valor: String): Double? {
        return try {
            // Remove R$, espaços e pontos de milhar
            val limpo = valor
                .replace("R$", "")
                .replace(" ", "")
                .trim()

            // Substitui vírgula por ponto se necessário
            val normalizado = if (limpo.contains(",")) {
                limpo.replace(".", "").replace(",", ".")
            } else {
                limpo
            }

            normalizado.toDouble()
        } catch (e: Exception) {
            null
        }
    }

    // Formata double para moeda brasileira (R$ 1.234,56)
    fun formatarMoeda(valor: Double): String {
        return formatadorMoeda.format(valor)
    }

    // Formata double para porcentagem (70,0%)
    fun formatarPorcentagem(valor: Double): String {
        return formatadorPorcentagem.format(valor / 100)
    }

    // Valida se o texto é um preço válido
    fun validarPreco(texto: String): Boolean {
        return stringParaDouble(texto)?.let { it > 0 } ?: false
    }
}