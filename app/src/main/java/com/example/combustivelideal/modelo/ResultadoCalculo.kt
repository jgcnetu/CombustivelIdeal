package com.example.combustivelideal.modelo

enum class TipoCombustivel {
    ETANOL,
    GASOLINA
}

data class ResultadoCalculo(
    val sucesso: Boolean,
    val recomendacao: TipoCombustivel? = null,
    val porcentagem: Double? = null,
    val mensagem: String,
    val erro: String? = null
) {
    companion object {
        fun calcular(gasolina: Double, etanol: Double): ResultadoCalculo {
            // Validação básica
            if (gasolina <= 0 || etanol <= 0) {
                return ResultadoCalculo(
                    sucesso = false,
                    mensagem = "❌ Erro nos valores",
                    erro = "Digite preços maiores que zero"
                )
            }

            // Cálculo da porcentagem
            val porcentagem = (etanol / gasolina) * 100

            // Determinar combustível ideal
            val recomendacao = if (porcentagem <= 70) {
                TipoCombustivel.ETANOL
            } else {
                TipoCombustivel.GASOLINA
            }

            // Gerar mensagem amigável
            val mensagem = when (recomendacao) {
                TipoCombustivel.ETANOL ->
                    "✅ ETANOL é mais vantajoso\n" +
                            "${formatarPorcentagem(porcentagem)} do preço da gasolina"

                TipoCombustivel.GASOLINA ->
                    "✅ GASOLINA é mais vantajosa\n" +
                            "${formatarPorcentagem(porcentagem)} do preço da gasolina"
            }

            return ResultadoCalculo(
                sucesso = true,
                recomendacao = recomendacao,
                porcentagem = porcentagem,
                mensagem = mensagem
            )
        }

        private fun formatarPorcentagem(valor: Double): String {
            return "%.1f%%".format(valor)
        }
    }
}