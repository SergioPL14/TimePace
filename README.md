# T&P — Time & Pace Converter

A minimalist Android app for runners to convert between pace, time, and speed.

![Android](https://img.shields.io/badge/Android-26%2B-3DDC84?logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-2.0-7F52FF?logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-2024.09-4285F4?logo=jetpack-compose&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

---

## Features

- **Pace → Time** — Given a pace (min/km) and distance, calculate total race time
- **Time → Pace** — Given a finish time and distance, calculate required pace
- **Speed → Pace** — Convert km/h to min/km pace
- **Pace → Speed** — Convert min/km pace to km/h

Preset distances included: 5K, 10K, Half Marathon, Full Marathon.

Results update instantly as you type — no Calculate button needed.

---

## Screenshots

> Coming soon

---

## Tech Stack

| Layer | Technology |
|---|---|
| UI | Jetpack Compose + Material3 |
| Architecture | Clean Architecture (Domain / Presentation) |
| State | ViewModel + StateFlow |
| Dependency Injection | Hilt |
| Language | Kotlin 2.0 |
| Min SDK | Android 8.0 (API 26) |

---

## Project Structure

```
app/src/main/java/com/tuapp/timepace/
├── domain/
│   ├── model/          # Pace, TimeResult, Distance (pure Kotlin)
│   └── usecase/        # PaceToTime, TimeToPace, SpeedToPace, PaceToSpeed
├── presentation/
│   ├── viewmodel/      # ConverterViewModel + ConverterUiState
│   └── ui/
│       ├── components/ # ModeSelector, PaceInput, TimeInput, DistanceInput...
│       ├── screen/     # ConverterScreen
│       └── theme/      # Black & white minimalist theme
└── di/                 # Hilt AppModule
```

---

## Getting Started

1. Clone the repo
   ```bash
   git clone https://github.com/YOUR_USERNAME/timepace.git
   ```
2. Open in Android Studio (Hedgehog or newer)
3. Make sure you have **JDK 17** configured under `File → Settings → Gradle JDK`
4. Run on an emulator or physical device (API 26+)

---

## Running Tests

```bash
./gradlew test
```

47 unit tests covering domain models, use cases, and ViewModel logic. All tests run on JVM — no emulator required.

---

## License

MIT License — feel free to use, modify and distribute.
