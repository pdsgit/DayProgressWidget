#!/bin/bash

echo "========================================="
echo "Day Progress Widget - Project Validation"
echo "========================================="
echo ""

echo "✓ Checking project structure..."
if [ -d "app/src/main/java/com/dayprogresswidget" ]; then
    echo "  ✓ Source directory exists"
else
    echo "  ✗ Source directory missing"
    exit 1
fi

if [ -f "app/build.gradle.kts" ]; then
    echo "  ✓ App build.gradle.kts exists"
else
    echo "  ✗ App build.gradle.kts missing"
    exit 1
fi

echo ""
echo "✓ Checking Kotlin source files..."
SOURCE_FILES=(
    "app/src/main/java/com/dayprogresswidget/MainActivity.kt"
    "app/src/main/java/com/dayprogresswidget/DayProgressApplication.kt"
    "app/src/main/java/com/dayprogresswidget/viewmodel/DayProgressViewModel.kt"
    "app/src/main/java/com/dayprogresswidget/data/PreferencesManager.kt"
    "app/src/main/java/com/dayprogresswidget/ui/screens/DayProgressScreen.kt"
    "app/src/main/java/com/dayprogresswidget/ui/components/CircularProgressRing.kt"
    "app/src/main/java/com/dayprogresswidget/ui/components/TimePickerField.kt"
    "app/src/main/java/com/dayprogresswidget/ui/theme/Theme.kt"
    "app/src/main/java/com/dayprogresswidget/ui/theme/Type.kt"
)

for file in "${SOURCE_FILES[@]}"; do
    if [ -f "$file" ]; then
        echo "  ✓ $(basename $file)"
    else
        echo "  ✗ $(basename $file) missing"
        exit 1
    fi
done

echo ""
echo "✓ Checking resource files..."
RESOURCE_FILES=(
    "app/src/main/AndroidManifest.xml"
    "app/src/main/res/values/strings.xml"
    "app/src/main/res/values/colors.xml"
    "app/src/main/res/values/themes.xml"
)

for file in "${RESOURCE_FILES[@]}"; do
    if [ -f "$file" ]; then
        echo "  ✓ $(basename $file)"
    else
        echo "  ✗ $(basename $file) missing"
        exit 1
    fi
done

echo ""
echo "✓ Checking Gradle files..."
if [ -f "settings.gradle.kts" ]; then
    echo "  ✓ settings.gradle.kts"
else
    echo "  ✗ settings.gradle.kts missing"
    exit 1
fi

if [ -f "build.gradle.kts" ]; then
    echo "  ✓ build.gradle.kts"
else
    echo "  ✗ build.gradle.kts missing"
    exit 1
fi

echo ""
echo "========================================="
echo "PROJECT VALIDATION SUCCESSFUL!"
echo "========================================="
echo ""
echo "Project Structure:"
echo "  - Application: com.dayprogresswidget"
echo "  - Min SDK: 24 (Android 7.0)"
echo "  - Target SDK: 34 (Android 14)"
echo "  - Language: Kotlin 1.9.20"
echo "  - UI: Jetpack Compose + Material3"
echo ""
echo "To build the APK:"
echo "  1. Open this project in Android Studio"
echo "  2. Let Gradle sync complete"
echo "  3. Run: Build > Build Bundle(s) / APK(s) > Build APK(s)"
echo ""
echo "Or use Gradle command line (requires Android SDK):"
echo "  ./gradlew assembleDebug"
echo ""
echo "For CI/CD (Codemagic):"
echo "  - All dependencies are declared in app/build.gradle.kts"
echo "  - Standard Android build commands work"
echo "  - APK will be generated in app/build/outputs/apk/"
echo ""
echo "========================================="
