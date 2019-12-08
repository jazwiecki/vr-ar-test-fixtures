# CSU CS 567

## Using VR Environments as fixtures for AR tests

Automated testing of augmented reality applications using virtual
reality fixtures.

### Check-ins

#### Check-in 4

So far, so good. Since the last check-in, I adapted a skeleton of an AR
app for the visually impaired to give me an actual app to test. It
looks for nearby objects and triggers proximity warnings. That was the
easy part! After I got a trivial app to test, I had to figure out how to
actually test it using the Android emulator's VR environment, a process
which mostly involved exhaustively confirming several approaches were
unworkable.

First, I tried sending the keystrokes that the emulator used for
navigating in the VR environment, before determining that, while they
could be sent to the app, the emulator wasn't receiving them. Then, I
tried using Google's UiAutomator test tooling, but that didn't
receive the keystrokes, either. The emulator came with a couple built-in
macros, but I couldn't reverse-engineer their format or find a way to
trigger them programmatically. Finally, I realized that I could send
commands to the emulator to record emulator movements and play them
back. This gave me a way to record the prebuild macros and my own custom
movements.

Although this solved the problem of creating navigation fixtures for AR
apps in VR environments, I still had to figure out how to trigger the
automations from within the app test methods. This involved searching
through the built-in test tooling, the command-line environment of the
emulated device, and finally the source code of the "Android Bridge",
"adb", which is installed as part of the SDK, in order to
reverse-engineer how the commands I was running were being sent to the
emulator. Poking around the emulator source was pretty useful, too – I
managed to find the 3D models used by the emulator, although I haven't
tried replacing them with custom models, which would be pretty useful to
this line of research. Once I determined that I had to emulate telnet to
talk to the emulator, and figured out how I could talk to the emulator
from within the emulated environment, it was relatively easy to write a
function that would run emulator automations by sending commands to a
socket. As of this evening, I'm able to run a test that triggers an
automation and then makes an assertion about the behavior of the app
when that automation concludes.

For my next check-in, I'll have a lot more source to go with this.
Right now, it's pretty disorganized.

**[Video check-in #4](https://youtu.be/9MWq6M4Ayh8)**

#### Check-in 3

Finally, what feels like the most progress I've had in weeks! It turns
out that Google's Android AR toolchain has more of what I need.
Specifically, Android Studio's emulated devices can load VR worlds for
AR applications. Once I got the basics set up – everything installed,
ARCore SDK for emulators side-loaded to the virtual device, app running
on the device – I was able to "navigate" in a VR environment and place
a virtual 3D Android icon on a surface. Then, for the next step in my
project, I had to start stubbing in the basis of an AR app that I could
test. It's pretty barebones, but I break the screen up into a 3x3 grid
and calculate a rough distance to any object directly in front with hit
tests. Then, the results are passed to a function that updates a text
box with a message about the distance to the nearest object. The good
news is, it works. The bad news is, I haven't figured out how to run an
automated test with it yet, probably because I can't figure out how to
instantiate that VR world in the test, because it runs via the CLI.
Still, I feel I'm definitely headed in the right direction.

The only big question now is what the best experiment would be. There
are two problems: first, I don't know if I can load a custom model for
the VR world yet. Second, I don't know what would be the best
comparison for an experiment. I suppose manually testing the application
in the emulator might be an option, but that seems pretty likely a
computer could perform them faster and more accurately than I can. I
think my ideal test harness would allow me to generate the VR world from
a real environment, so that I could at least eliminate variation between
those environments. I'm going to reach out for suggestions after I
figure out if I can even run a VR automated test.

**[Video check-in #3](https://youtu.be/s12f76gebGM)**


#### Check-in 2

While I was working on the project for this checkpoint, I kept thinking
about a quote I ran into many years ago about unicycles. It went
something like this: some people look around them and think, “No one
else is doing this, I should do it!” Most people having that experience
think, “No one else is doing this, and there must be a reason, so I
shouldn’t do this.” I definitely had the first reaction when I thought
about augmented reality software testing, but the more I work on this,
the more I think about the second reaction. There’s a reason there’s so
little published research on automated software testing of augmented
reality systems, and that’s because it’s actually really, really hard
because most of the existing tools just aren’t there yet. Not even close.

First, here are a couple options for my hypothesis: either automated
functional testing using virtual reality will find more faults than
manual testing, or automated testing will cover more test conditions
per minute than manual testing. Since the last checkpoint, I’ve spent a
lot of time just trying to learn more about what I can do with the
existing software. First, I burnt several hours just trying to get
Unreal Editor to accept the certificates needed to sign an application,
before a deep dive in the build logs gave me a clue: Unreal’s toolchain
complained about being unable to handle the “FriendlyName” key on the
certificates, which turned out to be a problem related to Mono, which is
all the way at the bottom of Unreal’s crazy build toolchain. I tracked
down a specific issue that claimed version 4.23.1 would fix the issue,
joined Epic’s open-source Unreal Engine GitHub group, and patiently
waited for 4.23.1 to be released. As soon as it was out, I was able to
test the demo ARKit app for the first time. This was pretty exciting,
but the more I thought about it, the more I think I would need an actual
working app in order to test more than just the OS-level SLAM systems.
Testing those is useful, but I’m really interested in researching the
kinds of software that would be a consumer of those systems. For that
reason, I did some more research to find a non-trivial AR application
(that is, not a toy or novelty) and reached out to the developers of
Clew, an application meant to help visually impaired users inside a
building retrace their steps. I haven’t heard back yet, but I think
that’s a good candidate app for the use of VR fixtures, so they can test
it in different scenarios that go beyond just determining if it can
identify surfaces.

The other thing I worked on was researching the file format for ARKit’s
“ARWorldMap”, which is the space-mapping state and set of anchors from a
world-tracking AR session. I figured it might help me generate a VR
world for use in AR, so I used source code for a demo app provided by
Apple to build a trivial app, in order to use the debugger to get access
to the data stored by the app. It looks like Unity might have more tools
than Unreal for working with ARWorldMap, so I'm currently exploring that
as an entry point for feeding VR information to an AR app.

**[Video check-in #2](https://youtu.be/n1GnYoovOiU)**

#### Check-in 1

Since the project was approved, most of my time on this project has been
spent getting familiar with Unreal Engine 4, its testing systems, and
the C++ required by both. I put a lot of hours into working through the
recommended Udemy Unreal C++ course, figuring out how to build tests
in Unreal Engine, and reading up on writing C++ tests for UE. A
significant amount of time also went into figuring out how to get
everything running well – clicking "compile" in UE doesn't necessarily
always build using the latest version of the source, and there's kind
of a weird trick involving touching a core config file to get UE builds
from about 30-60 seconds to 10-20 seconds. Plus, for reasons I don't
yet entirely understand, XCode has to do a _lot_ of thinking to analyze
the generated source in order to enable all of its IDE features, which
also slowed down development while my CPU spiked to max utilization for
12+ hours for each new generated UE project. For this check-in, I am
including the source of some of the projects I worked on while
learning/prototyping, and for the next one I hope to have either a test
suite which varies some parameters of the 3D environment, or an ARKit
application with some simple user action that is potentially testable.

**[Video check-in #1](https://youtu.be/vpzpBfPq6DE)**