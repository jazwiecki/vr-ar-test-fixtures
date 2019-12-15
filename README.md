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
Android Studio
Gradle

## Setup
Clone this repository and open the project in Android Studio
Create an AR-enabled emulated Android device and install the app on it
Copy test macros from `./emulator-macros` to your home folder

## Running tests
Get the name of the emulated device by running `emulator -list-avds`.
The emulator command is in `Android/sdk/emulator/`, so the actual
location will depend on where the SDK is installed. On a computer
running macOS, it installs in the Library folder in the installing
user's home directory, so it might be `~/Library/Android/sdk/emulator/`.

Start the emulator using the name of the AVD (Android Virtual Device)
with `./emulator -avd $NAME_OF_AVD`.

Run the complete instrumented test suite with
`./gradlew connectedDebugAndroidTest` while the device is running.