## Introduction ##

This page summarizes changes made to CogDroid since the first public alpha release on September 3, 2011.

## Baseline ##

The first alpha [release](http://squeakvm-tablet.googlecode.com/files/CogDroid-alpha-20110903.apk) was a mere Android port of Stack (non-JIT) flavor of the [Cog](http://www.mirandabanda.org/cogblog/) virtual machine which is capable of working with recent images of [Squeak](http://squeak.org/) and [Pharo](http://www.pharo-project.org/home) implementations of the [Smalltalk](http://www.smalltalk.org/) programming language. The first alpha release supported only the [ARM](http://www.arm.com/)-based Android devices. The application was able to load and save Smalltalk images and other files from the device's local file system (SD card) and communicate over the network thus allowing to install Smalltalk packages from [SqueakSource](http://squeaksource.com/). No integration with Android-specific services was provided.

## New Functionality Added ##

This section is compiled based on the commit log at the [Gitorious repository](https://gitorious.org/cogvm/dmg-blessed) containing the development branch of CogDroid.

  * Implemented a simple interface with [Android TTS](http://developer.android.com/resources/articles/tts.html) (text-to-speech) voice synthesis system. A text string (English only at the moment) can be pronounced; speech rate and pitch are adjustable. In order to use this functionality, the `Android-Base` package has to be installed from the [EventVM](http://squeaksource.com/EventVM/) repository at SqueakSource.

  * Implemented an intent filter so CogDroid can be started by resolving an intent with data MIME type `application/x-squeak-image` and URI scheme `file://`. With such intent, CogDroid obtains the image file path, the startup script name, and startup script arguments from the intent, just as if it were launched from a command line in a regular Unix or Windows environment. CogDroid can only be launched as a [single instance](http://developer.android.com/guide/topics/fundamentals/tasks-and-back-stack.html) activity due to the platform code being non-reentrant.

  * Implemented functionality to create home screen shortcuts which, when tapped, generate intents resolvable to start the CogDroid application. In order to use this functionality, the `Android-Base` package has to be installed from the [EventVM](http://squeaksource.com/EventVM/) repository at SqueakSource. See the CogDroidIntentsAndShortcuts wiki page for more details.

  * Added [status bar notification](http://developer.android.com/guide/topics/ui/notifiers/notifications.html) icon appearing in the status bar when CogDroid is running, even if backgrounded.

  * Platform for [Android-x86](http://www.android-x86.org/) was added, so CogDroid can be used with the x86 port of Android. Most of the platform code is shared between ARM and x86 platforms, except for few compiler flags that are platform-specific.

  * Better support was added for PC keyboard (usable mainly with x86-Android on PCs and netbooks, also can be useful on ARM tablets with USB keyboard attached, but was not tested such way) to process Ctrl- and Shift- modifiers with alphanumeric and arrow keys, and Home/End keys. Mouse wheel is also supported for scrolling. This provides editing functionality in the class browser and similar applications.

  * Minor tweaks such as making the "Back" hardware button (or right mouse click) to cycle through "Yellow" and "Blue" mouse click modifiers: may be useful on devices without "Page Forward" hardware button.

## Things Not Yet Working ##

  * Sound and multimedia integration. For now, a dummy sound module is compiled. Implementing this requires deeper research into Android internals, e. g. is sound interface compatible with traditional ALSA or OSS, or additional Java wrappers are necessary to use.

  * Multitouch support. Currently screen tap is equivalent to left mouse click.

  * Running multiple instances of CogDroid is not possible because the platform code is not reentrant, and Android shares loaded dynamic libraries among several instances of the same application (with the same Java package name)

## Things Worth Adding Towards Beta ##

  * Enable use of CogDroid as a library. Android applications are identified uniquely by their Java package names. If an activity class is derived from the CogActivity class into a different package, this will be a different application from Android standpoint. Copy of the dynamic library with platform code is loaded once per package. Thus, several applications with different package names will be able to run simultaneously, although each application will still be in the single instance mode.

  * Build a minimal size image with GUI containing some administrative and maintenance code. Such image could be loaded by default if no image is specified via an intent, instead of showing a list of available Smalltalk images found in the local file system.

  * Sound system integration.

More to this section can be added from your comments to this wiki page ;)