
# SniperGold v116 Pro - TINUOD NA APP

Repo: https://github.com/rathure22/snipergold-v116-pro

## Flow
MainActivity > AdaptiveShell > MainScreen + BinanceSidebar > MainViewModel > BinanceManager / SecureApiStorage / TradeHistoryStorage

## Features
- LIVE Gold Price from gold-api.com FREE NO KEY
- Optional Binance Keys - wired ready to paste
- 0.01 lot fixed low risk
- TP SMART: 50% BE+, 80% Trailing, 100% ✓ NIGO
- History Check: ✓ NIGO / X PILDI / ● LIVE with colors

## Paano himoong APK tinuod?

### Option 1: Android Studio (Tinuod na APK)
1. Clone repo: `git clone https://github.com/rathure22/snipergold-v116-pro.git`
2. Open sa Android Studio
3. Build > Build APK > Debug

### Option 2: GitHub Actions (Auto Build APK)
1. Push code sa GitHub
2. Adto sa Actions tab > Build SniperGold APK > Download artifact = app-debug.apk
3. Install sa phone

### Option 3: HTML to APK WebView
- Naa na sa MainActivityWebView.kt - i-load ang HTML file sa WebView para mahimong tinuod na app

## Live Test PWA
https:// - ang HTML version pwede ma Add to Home Screen

## Structure
- app/build.gradle.kts - dependencies
- MainActivity.kt - entry
- ui/AdaptiveShell.kt - shell
- binance/ - BinanceManager, SecureApiStorage, TpSmartManager, etc
- data/GoldApiManager.kt - free gold price
