# Day Progress Widget - Android App

## Overview
A complete Android application built with Kotlin and Jetpack Compose that visualizes daily progress through a circular ring interface. Users can set their wake-up and sleep times, and the app displays real-time progress through the day with a beautiful Material3 UI.

## Project Details
- **Language**: Kotlin 1.9.20
- **UI Framework**: Jetpack Compose with Material3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Min SDK**: Android 7.0 (API 24)
- **Target SDK**: Android 14 (API 34)
- **Compile SDK**: 34

## Features Implemented
1. ✅ Circular progress ring showing day completion percentage
2. ✅ Time picker inputs for wake-up and sleep times
3. ✅ Real-time progress calculation based on current time
4. ✅ Percentage display in the center of the ring
5. ✅ Time remaining until sleep (hours and minutes)
6. ✅ Material3 theme with light/dark mode support
7. ✅ Persistent storage using DataStore
8. ✅ Modular architecture ready for Glance widget extension
9. ✅ Complete Gradle configuration for CI/CD builds

## Project Structure
```
DayProgressWidget/
├── app/
│   ├── build.gradle.kts              # App-level build configuration
│   ├── src/main/
│   │   ├── AndroidManifest.xml       # App manifest
│   │   ├── java/com/dayprogresswidget/
│   │   │   ├── DayProgressApplication.kt    # Application class
│   │   │   ├── MainActivity.kt              # Main activity with Compose setup
│   │   │   ├── data/
│   │   │   │   └── PreferencesManager.kt    # DataStore preferences management
│   │   │   ├── viewmodel/
│   │   │   │   └── DayProgressViewModel.kt  # State management and logic
│   │   │   └── ui/
│   │   │       ├── components/
│   │   │       │   ├── CircularProgressRing.kt  # Circular progress component
│   │   │       │   └── TimePickerField.kt       # Time picker component
│   │   │       ├── screens/
│   │   │       │   └── DayProgressScreen.kt     # Main screen composable
│   │   │       └── theme/
│   │   │           ├── Theme.kt             # Material3 theme definition
│   │   │           └── Type.kt              # Typography definitions
│   │   └── res/                     # Android resources
│   │       ├── values/
│   │       │   ├── strings.xml      # String resources
│   │       │   ├── colors.xml       # Color definitions
│   │       │   └── themes.xml       # Theme configuration
│   │       └── xml/
│   │           ├── backup_rules.xml
│   │           └── data_extraction_rules.xml
├── build.gradle.kts                 # Root build configuration
├── settings.gradle.kts              # Project settings
├── gradle.properties                # Gradle properties
└── gradle/wrapper/                  # Gradle wrapper files
```

## Key Components

### 1. PreferencesManager
Handles persistent storage of user preferences using DataStore:
- Wake-up time (stored as minutes from midnight)
- Sleep time (stored as minutes from midnight)
- Default values: 7:00 AM wake, 11:00 PM sleep

### 2. DayProgressViewModel
Manages application state and business logic:
- Loads wake/sleep times from DataStore
- Calculates day progress percentage
- Computes time remaining until sleep
- Updates in real-time (every minute)

### 3. CircularProgressRing
Reusable Compose component that:
- Draws a circular progress indicator
- Displays percentage in the center
- Uses Material3 colors
- Customizable size and stroke width

### 4. TimePickerField
Interactive time picker component:
- Shows current time in a text field
- Opens Material3 TimeInput dialog on click
- Formats time as HH:MM
- Supports 24-hour format

### 5. DayProgressScreen
Main screen composable that combines all components:
- Displays the title
- Shows circular progress ring
- Presents time remaining card
- Provides time picker fields for wake/sleep times
- Auto-updates every minute

## Building the Project

### In Android Studio:
1. Open the project in Android Studio
2. Let Gradle sync complete
3. Click "Run" or press Shift+F10
4. Select a device or emulator

### Via Command Line:
```bash
# Make gradlew executable
chmod +x gradlew

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug
```

### For Codemagic CI/CD:
The project is configured for CI/CD builds:
- All dependencies are declared in `app/build.gradle.kts`
- Gradle wrapper is included
- Standard Android build commands work out of the box

## Dependencies
- **Compose BOM 2023.10.01**: Manages all Compose library versions
- **Material3**: Modern Material Design components
- **DataStore 1.0.0**: Preferences storage
- **Coroutines 1.7.3**: Asynchronous programming
- **Lifecycle ViewModel Compose 2.6.2**: ViewModel integration with Compose

## Future Extensions

### Glance Widget Integration
The modular architecture makes it easy to add a Glance-based home screen widget:
1. Add Glance dependencies to `app/build.gradle.kts`
2. Extract progress calculation logic (already modular in ViewModel)
3. Create GlanceAppWidget using the same data layer
4. Share PreferencesManager between app and widget

### Planned Features
- Customizable color themes
- Multiple day profiles (weekday/weekend)
- Progress notifications
- Weekly/monthly analytics
- Widget for home screen

## Recent Changes
- **2025-11-10**: Initial project creation with all core features implemented

## Architecture Notes
- **MVVM Pattern**: Clear separation of UI, business logic, and data layers
- **Unidirectional Data Flow**: ViewModel emits state, UI observes and reacts
- **Compose Best Practices**: Stateless composables, state hoisting
- **Type Safety**: Strong typing throughout, leveraging Kotlin's type system
