# CSU CS 567: Using VR Environments as fixtures for AR tests

Automated testing of augmented reality applications using virtual
reality fixtures.

## Testing AR Applications
TK Software testing is important

### The advantages of using VR
TK VR can be used for fixtures

### Approach
Using the Android SDK

## Requirements
* [Android Studio](https://developer.android.com/studio/install)
* [Gradle](https://gradle.org/install/)

## Setup
1. Clone this repository and open the project in Android Studio
2. Create an [AR-enabled emulated Android device](https://developers.google.com/ar/develop/java/emulator#create_a_virtual_device_with_ar_support) and install the app on it
3. Copy test macros from `./emulator-macros` to your home folder (the emulator
is hard-coded to look for files in this location)

## Running tests
https://developer.android.com/studio/run/emulator-commandline

Get the name of the emulated device by running `emulator -list-avds`.
The emulator command is in `Android/sdk/emulator/`, so the actual
location will depend on where the SDK is installed. On a computer
running macOS, it installs in the Library folder in the installing
user's home directory, so it might be `~/Library/Android/sdk/emulator/`.

Start the emulator using the name of the AVD (Android Virtual Device)
with `./emulator -avd $NAME_OF_AVD`.

Run the complete instrumented test suite with
`./gradlew connectedDebugAndroidTest` while the device is running.