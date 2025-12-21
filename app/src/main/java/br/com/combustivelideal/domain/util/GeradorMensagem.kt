package br.com.combustivelideal.domain.util

import br.com.combustivelideal.domain.model.ResultadoCalculo
import br.com.combustivelideal.domain.model.TipoCombustivel
import br.com.combustivelideal.presentation.util.FormatadorUtil
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object GeradorMensagem {

    fun gerarMensagemCompartilhamento (
        resultado: ResultadoCalculo,
        precoGasolina: Double,
        precoEtanol: Double
    ): String {
        val dataAtual = SimpleDateFormat(
            "dd/MM/yyyy",
            Locale("pt", "BR")).format(Date())

        return """
        ⛽ *Combustível Ideal - Resultado do Cálculo* ⛽
        
        📅 Data: $dataAtual
        
        💰 *Preços:*
        Gasolina: ${FormatadorUtil.formatarMoeda(precoGasolina)}
        Etanol: ${FormatadorUtil.formatarMoeda(precoEtanol)}
     
        📊 *Resultado:*
        ${resultado.mensagem}
        Relação: ${FormatadorUtil.formatarPorcentagem(resultado.porcentagem ?: 0.0)}
        
        ✅ *Recomendação:*
        ${gerarRecomendacaoTexto(resultado.recomendacao)}
        
        🔧 *Como funciona:*
        Divida o preço do etanol pelo da gasolina;
        Se o resultado for ≤ 70%, escolha etanol;
        Se for > 70%, escolha gasolina.
                            
        📱 *App: Combustível Ideal*
        #CombustivelIdeal #Economia #CalculadoraCombustivel
        """.trimIndent()
    }

    fun gerarMensagemSimples(resultado: ResultadoCalculo): String {
        return "✅ ${resultado.mensagem} " +
                "(Relação: ${FormatadorUtil.formatarPorcentagem(resultado.porcentagem ?: 0.0)}) " +
                "📱 Combustível Ideal App"
    }

    fun gerarMensagemWhatsApp(resultado: ResultadoCalculo): String {
        val emoji = if (resultado.recomendacao?.name == "ETANOL") "🟢" else "🟡"
        return "$emoji *${resultado.mensagem}* " +
                "(${FormatadorUtil.formatarPorcentagem(resultado.porcentagem ?: 0.0)}) " +
                "📲 Calculado pelo app *Combustível Ideal*"
    }

    private fun gerarRecomendacaoTexto(
        tipo: TipoCombustivel?): String {
        return when (tipo?.name) {
            "ETANOL" -> "• Use ETANOL para economizar\n" +
                    "• Vantajoso até 70% do preço da gasolina\n" +
                    "• Economize no abastecimento!"
            "GASOLINA" -> "• Use GASOLINA neste caso\n" +
                    "• Acima de 70% do preço da gasolina\n" +
                    "• O etanol não vale a pena agora"
            else -> "• Faça o cálculo no app para ver a recomendação !"
        }
    }

    /*fun gerarImagemTexto(resultado: ResultadoCalculo): String {
        return """
            ┏━━━━━━━━━━━━━━━━━━━━━━━┓
            ┃     ⛽ COMBUSTÍVEL    ┃
            ┃        IDEAL          ┃
            ┗━━━━━━━━━━━━━━━━━━━━━━━┛
            
            ${resultado.mensagem}
            
            Relação: ${FormatadorUtil.formatarPorcentagem(resultado.porcentagem ?: 0.0)}
            
            ${if (resultado.recomendacao?.name == "ETANOL") "🟢 Use ETANOL" else "🟡 Use GASOLINA"}
            
            📱 App Combustível Ideal
            """.trimIndent()
    }*/
}