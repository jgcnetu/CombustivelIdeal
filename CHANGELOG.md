\# Changelog
Todas as mudanças notáveis deste projeto serão documentadas aqui.

O formato segue o padrão \[Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/)
e este projeto adota \[Semantic Versioning](https://semver.org/lang/pt-BR/).


---


## [1.1.1] - 29/12/2025


### ✨ Novidades

- Adicionada opção opcional para informar o **consumo do veículo** (km/l) para Etanol e Gasolina
- Novo cálculo baseado em **custo real por km (R$/km)**
- Resultado mais preciso quando o consumo é informado
- Ícone de destaque no combustível vencedor no modo consumo
- Compartilhamento de resultado agora inclui:
  - Preços
  - Consumo
  - Custo por km
  - Melhor combustível
- InfoCard atualizado explicando o cálculo por consumo

### 🎨 UI / UX

- ResultCard adaptado para exibir cálculo por consumo
- Destaque visual claro para o combustível mais vantajoso
- Mantido layout clean e Material 3
- Nenhuma regressão no modo de cálculo tradicional (70%)

### 🛠️ Técnicas

- Reaproveitamento do utilitário `shareText`
- Lógica de cálculo isolada e segura
- Melhor separação de responsabilidades entre UI e ViewModel


---


\## \[1.1.0] - 22/12/2025


\### ✨ Adicionado

\- Nova HomeScreen totalmente reformulada em Jetpack Compose
\- Ring animado com glow neon e gota central
\- Animação baseada no cálculo (etanol x gasolina)
\- Card de resultado animado com:
  - Opção de recalcular
  - Opção de compartilhar resultado
\- Card informativo “Como funciona?”
\- Botões neon personalizados (Calcular / Limpar)
\- Animação no botão Limpar (shake)
\- Splash Screen Android 12+
\- Ícone adaptativo (foreground/background)
\- Suporte a tema dark e light alinhado ao verde neon
\- Ajuste da Status Bar para cor primary


\### 🎨 Melhorado

\- UI/UX geral com visual moderno inspirado em apps premium
\- Centralização e espaçamento dos elementos
\- Scroll vertical na Home para melhor usabilidade
\- Estados de botão (habilitado/desabilitado)
\- Organização de componentes em packages


\### 🛠 Corrigido

\- Problemas de layout que sobrepunham botões
\- Erros de alinhamento em cards e ícones
\- Correções em componentes Compose (Box, Row, Column)


---


\## \[1.0.0] - 05/12/2025


\### 🎉 Lançamento inicial

\- Cálculo básico de combustível ideal
\- Interface simples