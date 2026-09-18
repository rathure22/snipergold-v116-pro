# SniperGold v116 Pro

**LIVE Gold price + optional Binance keys + TP SMART**

## Features
- LIVE Gold Price from [gold-api.com](https://api.gold-api.com/price/XAU) — **FREE, no API key**
- Optional Binance API keys (EncryptedSharedPreferences)
- Fixed **0.01 lot** risk
- **TP SMART**: 50% BE+ → 80% Trailing → 100% ✓ NIGO
- History Check: ✓ NIGO / X PILDI / ● LIVE (green / red / yellow)
- 3 tabs: **Sniper** | **History** | **Keys** — fully wired

## Build APK

### Option 1: Android Studio
1. Clone / open this repo
2. Wait for Gradle sync
3. **Build → Build Bundle(s) / APK(s) → Build APK(s)**

### Option 2: GitHub Actions (auto)
1. Push to `main`
2. Go to **Actions** tab → **Build SniperGold APK**
3. Download artifact = `app-debug.apk`

### Option 3: Command line
```bash
./gradlew assembleDebug
```
APK path: `app/build/outputs/apk/debug/app-debug.apk`

## Project Flow
```
MainActivity
  └── AdaptiveShell (3 tabs)
        ├── MainScreen          → live gold + BUY/SELL
        ├── HistoryCheckScreen  → ✓ NIGO / X PILDI / ● LIVE
        └── BinanceSidebarSection → paste API keys
              └── MainViewModel (AndroidViewModel)
                    ├── GoldApiManager
                    ├── BinanceManager
                    ├── SecureApiStorage
                    ├── TradeHistoryStorage
                    └── TpSmartManager
```

## Notes
- Real Binance order placement is **stubbed** for safety (returns local `ORDER_xxx`).
- Free mode works completely without any Binance key.
- Min SDK 26 / Target SDK 34 / Compose Material3

## License
Private / personal use.
