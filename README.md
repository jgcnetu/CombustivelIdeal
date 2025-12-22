📱 Combustível Ideal
<p align="center"> <img src="https://img.shields.io/badge/Kotlin-1.9+-7F52FF?logo=kotlin&logoColor=white" /> <img src="https://img.shields.io/badge/Jetpack%20Compose-UI-4285F4?logo=android&logoColor=white" /> <img src="https://img.shields.io/badge/Android-13%2B-3DDC84?logo=android&logoColor=white" /> <img src="https://img.shields.io/badge/Material%203-Design-6750A4?logo=materialdesign&logoColor=white" /> </p> <p align="center"> <img src="https://img.shields.io/badge/version-v1.1.0-brightgreen" /> <img src="https://img.shields.io/badge/license-MIT-blue" /> </p>

🚗 Sobre o App

Combustível Ideal é um aplicativo Android que ajuda o usuário a decidir qual combustível compensa mais: Etanol ou Gasolina, com base na regra dos 70%.
O app foi desenvolvido com Jetpack Compose, seguindo boas práticas modernas de arquitetura, UI declarativa e Material Design 3.


🧮 Como funciona

O usuário informa:
Preço do Etanol
Preço da Gasolina

O app calcula:
(Preço do Etanol / Preço da Gasolina) × 100

Resultado:
Até 70% → Etanol é a melhor opção
Acima de 70% → Gasolina compensa mais


✨ Funcionalidades

✅ Cálculo automático da melhor opção de combustível
🎨 Interface moderna com Material 3
🌈 Elementos visuais com efeito neon
📊 Indicador visual animado (ring progress)
🔄 Recalcular resultado sem limpar campos
🧹 Limpar campos rapidamente
📤 Compartilhar resultado
ℹ️ Card informativo explicando o cálculo
📱 Suporte a rolagem vertical
🌙 Pronto para Dark Mode


🧱 Arquitetura

O projeto segue uma separação clara de responsabilidades:
presentation
HomeScreen
ViewModel (StateFlow)
Components (Cards, Buttons, Rings)
domain
Modelos e regras de negócio
uiState
Estado imutável da tela

Padrões utilizados:
MVVM
StateFlow
UI declarativa com Compose


🛠️ Tecnologias Utilizadas

Kotlin
Jetpack Compose
Material Design 3
StateFlow
ViewModel
Android SDK 33+


📦 Requisitos

Android 13 (API 33) ou superior
Android Studio Hedgehog ou mais recente
Gradle com suporte a Compose


🚀 Como executar

Clone o repositório:
git clone https://github.com/seu-usuario/combustivel-ideal.git
Abra no Android Studio
Execute em um emulador ou dispositivo físico


🏷️ Versão
v1.1.0

UI refinada
Card de informações
Resultado centralizado
Compartilhamento de resultado
Scroll vertical
Melhor organização de componentes

Veja o arquivo CHANGELOG.md
 para mais detalhes.


📄 Licença

Este projeto está sob a licença MIT.
Sinta-se livre para usar, modificar e distribuir.


🙌 Autor: jgcnetu

Desenvolvido com ❤️ usando Kotlin + Jetpack Compose
