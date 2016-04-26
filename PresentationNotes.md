Slides for this presentation: http://wiki.squeakvm-tablet.googlecode.com/hg/Squeak_on_Android.pdf

# Pre-Historical Notes #

Nearly 40 years ago, Alan Kay (one of the Smalltalk creators) proposed an idea of [Dynabook](http://www.mprove.de/diplom/gui/Kay72a.pdf), a lightweight affordable tablet computing device that could be as well used by children for learning and development. It might be part of his vision that such device runs some dialect of [Smalltalk](http://www.smalltalk.org).

These days Dynabook-like devices are available under names of _e-Readers_ and _Internet Tablets_. These inexpensive devices run the [Android](http://www.android.com/) operating system, are equipped with touch screen and loudspeakers.

It is interesting to see whether the Dynabook idea might be practically implemented with these devices.

# Squeak #

[Squeak](http://www.squeak.org/About) is a highly portable, open-source Smalltalk with powerful multimedia facilities. Squeak is the vehicle for a wide range of projects from educational platforms to commercial web application development. It provides a flexible [Morphic Graphics User Interface](http://wiki.squeak.org/squeak/30) (GUI) with possibilities of on-the-fly program logic modification and development.

The two main components of each Squeak [installation](http://www.squeak.org/Documentation/Installation/) are the [Virtual Machine](http://wiki.squeak.org/squeak/676) and the [Image](http://onsmalltalk.com/simple-image-based-persistence-in-squeak) file. The former is an executable program for the host OS where Squeak is running, the latter is basically a snapshot of the virtual machine's heap (object memory). There are also [Changes](http://wiki.squeak.org/squeak/49) and [Sources](http://wiki.squeak.org/squeak/3022) files which reflect the evolution of the Image (not so much important for our presentation). It is important to note that, except for the Virtual Machine, the other parts of Squeak installation are platform-independent.

# Historical Notes #

In 2009, Andreas Raab [proposed](http://lists.squeakfoundation.org/pipermail/vm-dev/2009-November/003385.html) and [implemented](http://lists.squeakfoundation.org/pipermail/vm-dev/2009-November/003437.html) a special event-driven version of Squeak VM which does not contain an event loop, but instead acts as a handler for an externally-provided queue of events, and returns to the caller once all the events have been processed. Such modification of the VM makes it very convenient for embedding with any other (e. g. another language) runtime. Squeak on Android is just an example of such embedding with Java/Dalvik VM.

The event-driven Squeak VM currently available corresponds to the Squeak version 3.11 (with 4.2 being current at the time). It is not clear whether any further progress of the event-driven VM was made.

# Squeak VM embedded with Java #

In Android, [Java](http://www.oracle.com/technetwork/java/index.html) is the primary programming language. Although it uses [Dalvik](http://en.wikipedia.org/wiki/Dalvik_%28software%29) to run transformed Java bytecodes, original [JNI](http://java.sun.com/docs/books/jni/) (Java Native Interface) is supported.

Android [activities](http://developer.android.com/reference/android/app/Activity.html) (interactive applications) are event-driven by their organization that is, an activity implementation overrides certain methods of the base class that are invoked upon certain events happening such as user input action, timer period/expiration, etc.

Such organization would made it hard to integrate with a Squeak VM containing its own event loop. But the event-driven VM integrates much easier: overridden methods invoked on external events just add appropriate messages to the event queue, and enter the Squeak bytecode interpreter (via JNI).

# The Parent and Current Projects #

The [initial port](http://code.google.com/p/squeak-android-vm/) of the Android Squeak VM was released in early 2010, but does not seem to be actively maintained at the moment. The project being presented now was forked off it in early 2011.

The parent project at the moment of fork lacked certain functionality, for example Squeak Image could only be bundled with the application as a fixed asset, on-screen soft keyboard did not work, local filesystem access was not properly implemented, etc. The developers should not be blamed for this, of course: their initial porting work is extremely valuable.

The forked project does not touch the VM; it mainly targets to eliminate these defficiencies of the parent project, improving usability of Squeak on Android.

Porting a modern VM to Android will be a separate task.

# Progress So Far #

  * Soft input method now works. It was necessary to remove the custom subclass of [InputConnection](http://d.android.com/reference/android/view/inputmethod/InputConnection.html) and just provide an instance of [BaseInputConnection](http://d.android.com/reference/android/view/inputmethod/BaseInputConnection.html) for the activity view. BaseInputConnection just passes emulated key events to the active view without any attempts of text pre-editing.

  * Implemented an ad-hoc JNI wrapper for the Android [Text to Speech](http://d.android.com/reference/android/speech/tts/package-summary.html) system. It will likely be necessary in the future to provide a more generic JNI callback wrapper to interact with all the services Android provides.

  * A Squeak image can now be loaded from an external file, not only from assets. The device filesystem is scanned for image files, and a list of available images is provided when Squeak is started. Saving the entire image is not implemented (it was not in the parent project either). This might be seen as a benefit due to the specifics of an Activity lifecycle in Android, when an Activity may be destroyed at any moment if running in background.

  * Fixed access to the local filesystem. While the whole image is locked for writing, image segments loading and saving is supported. Thus it is possible to load and save Squesk projects, thus live application development is enabled.

  * Three-button mouse is emulated using hardware buttons many of Android devices have. Simple tap on the screen corresponds to a _Red_ click (left mouse button). Pressing the "Back" button colors the subsequent tap _Yellow_ (middle mouse button). Pressing the "Page Up" button colors the subsequent tap _Blue_ (right mouse button, brings up halo). Tap coloring is in effect only for one tap after a hardware button is pressed.

  * Of other improvements: screen orientation is locked via the application's manifest xml file; proper screen size is now determined via the `onLayout` method override, current directory is set to the directory where the image file chosen resides (unless loaded from assets).

  * Known defficiencies: tapping scrollbars outside the slider may cause the Squeak application freeze (may be a problem with stepping events), network socket access not working (neither was it in the parent project), NDK rev. 3 is still required to build the Squeak application, Morph dragging works, but is invisible (may be stepping again).

# Conclusions and Future Work #

At this moment, Squeak on Android is considered to be usable for experimentation by all interested users. A pre-built apk file, and zipped image file are provided in the download area of this Google Code project. Everybody is welcome to download them, and experiment on the available Android devices (tablets preferred).

For the future, it is necessary to fix the remaining defficiencies, and also to investigate whether a modern [Cog VM](http://www.mirandabanda.org/cog/) can be turned into an event-driven VM and used for the Android port. A smaller Squeak image with properly themed GUI is also good to have.