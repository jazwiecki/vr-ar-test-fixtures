# Using VR Environments as fixtures for AR tests

Automated testing of augmented reality applications using virtual
reality fixtures.

![Full test suite gif](https://cs.colostate.edu/~jzw/images/full-test-suite.gif)

## Testing AR Applications
As widely-deployed software platforms, as with any consumer, industry, or government software projects, effective testing will be critical to ensuring system correctness is effectively maintained through successive rounds of change. The advantages of automated software testing with regard to effectively maintaining software quality have been demonstrated, but it is difficult to write automated functional tests to verify the correctness of AR software.

In order for repeated tests to be valid over multiple runs, and to identify faults over successive rounds of changes, they must be carried out under controlled conditions. For most software systems under test, these controlled conditions are created by running the tests using test doubles, such as automatically or manually created mock or stub objects, in identical environments, with a set of fixtures that create an environment sufficient to initialize and run the system. By using fixtures and test doubles, engineers can ensure the repeatable aspects of their tests, which are critical to maintaining their validity. AR systems, however, will typically be expected to operate under far more widely varied conditions than most software, whether with variations in lighting (hue, brightness), atmospheric conditions (smoke, fog, precipitation), or surface materials (transparency, reflectivity). Effectively testing these conditions consistently with a physical device running the software under test, especially through the expected combinatoric explosion of all variations along those real-world continua, could be prohibitively challenging to the resources of most, if not all, organizations developing AR software.

VR environments offer the ability to run tests of consistent worlds and precisely, consistently vary conditions over successive tests within the expected tolerances of the AR systems under test, potentially acting as effective test fixtures in a cost-effective fashion.

This code demonstrates an effective approach for using the Android SDK to conduct tests of an Android AR application, providing 100% coverage and killing 94.1% of mutants.

## Requirements
* [Android Studio](https://developer.android.com/studio/install)
* [Gradle](https://gradle.org/install/)

## Setup
1. Clone this repository and open the project in Android Studio
2. Create an [AR-enabled emulated Android device](https://developers.google.com/ar/develop/java/emulator#create_a_virtual_device_with_ar_support) and install the app on it
3. Copy test macros from `./emulator-macros` to your home folder (the emulator
is hard-coded to look for files in this location)

## Running tests
First, [find the `emulator` command](https://developer.android.com/studio/run/emulator-commandline) in order to start the emulator from the command line.

Get the name of the emulated device by running `emulator -list-avds`.
The emulator command is in `Android/sdk/emulator/`, so the actual
location will depend on where the SDK is installed. On a computer
running macOS, it installs in the Library folder in the installing
user's home directory, so it might be `~/Library/Android/sdk/emulator/`.

Start the emulator using the name of the AVD (Android Virtual Device)
with `./emulator -avd $NAME_OF_AVD`.

Run the complete instrumented test suite with `./gradlew connectedDebugAndroidTest` while the device is running. A report on test coverage will be generated in `app/builds/reports/coverage/debug`.
