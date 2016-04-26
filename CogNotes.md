## Introduction ##

This page is started on Aug 12, 2011 as the next intermediate result was achieved: Event driven Stack Cog VM was compiled from a modified VMMaker configuration and built with recent Android NDK (`r6`). It can be loaded on an Android device and seems to be capable to run with both older Squeak 3.9 image (same as used with the Classic VM in this project), and one of recent PharoCore generic images.

## Location of Sources ##

With future integration with the mainline Cog repos in mind, current sources of the Android Cog are outside of this Google Code project and are as follows:

**Platform sources:**
```
git clone git://gitorious.org/~golubovsky/cogvm/dmg-blessed.git
```

**SLANG sources:**

http://squeaksource.com/EventVM/

## Building the CogDroid ##

<sup>these are very approximate recommendations, will be refined as the progress goes</sup>

Install `CMakeVMMaker-golubovsky.136.mcz` and `VMMaker-oscog-golubovsky.111.mcz` (or later) on top of mainline `ConfigurationOfCog`

Follow [this procedure](http://marianopeck.wordpress.com/2011/04/10/building-the-vm-from-scratch-using-git-and-cmakevmmaker/) to generate VM and plugin sources using the `StackEvtAndroidConfig` as the configuration class name. However the `cmake` step is not needed: change to the `platforms/android/project` directory of the git repo and run

```
ndk-build
```

See the contents of the `ndk-build` file first: make sure your shell environment (path to the Android NDK) is set up correctly. In the end, a file named `CogAndroid-debug.apk` will be created in the `platforms/android/project/bin` subdirectory of the git repo. Transfer this file on the Android device and install the application. Also place the Squeak or Pharo image/changes/sources files somewhere under the Android device's `sdcard` directory.

Once the application starts, it shows a list of image files found under the `/sdcard` path. Choose one by tapping the desired item.

## Sample Tests ##

Similar samples of tests were run on both Android tablet and PC using PharoCore 1.3.
<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/2screens.png'>
<p align='center'>Photo of the two screens: PC and tablet</p>
<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/pharo-tablet.png'>
<p align='center'>Test results on the tablet</p>
<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/pharo-pc.png'>
<p align='center'>Test results on the PC</p>