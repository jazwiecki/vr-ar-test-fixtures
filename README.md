# CSU CS 567

## Using VR Environments as fixtures for AR tests

Automated testing of augmented reality applications using virtual
reality fixtures.

### Check-ins

#### 1

*[Video check-in #1](https://youtu.be/vpzpBfPq6DE)*

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
