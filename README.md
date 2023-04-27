# ClockAnimation - Tema2 PAM

Tecă Alina-Mihaela, grupa 10LF203

ClockAnimation app, este o aplicație mobilă Android realizată în Jetpack Compose.
Aceasta este formată din două screen-uri "unite" într-un tab.
Pe primul ecran este desenat cu ajutorul composable-ului Canvas ceasul analog, ale cărei arătătoare și marcaje pot fi desenate dintr-o serie de culori ce pot fi alese dintr-un dialog.
Cât timp aplicația este deschisă, culorile sunt stocate în view model.
Al doilea ecran este temporizatorul. Timpul poate fi ales printr-un dialog pus la dispoziție de librăria [Sheets - Compose Dialog](https://maxkeppeler.notion.site/Sheets-Compose-Dialogs-804f0ebcb2c84b98b7afa5f687295aed).
Temporazitorul poate fi oprit, modificat și văzut atâta timp cât nu se merge pe pagina de Analog și aplicația nu este oprită. 
De asemenea, când temporizatorul a ajuns la final trimite notificare.
