[Intents](http://developer.android.com/guide/topics/intents/intents-filters.html#iobjs) are messages that Android applications send around to request certain actions from other applications. Another application may be launched as a result of sending an intent, or a running service may be notified. An intent typically carries a pointer to some data source (in the form of an [URI](http://en.wikipedia.org/wiki/Uniform_Resource_Identifier)), data content [MIME type](http://en.wikipedia.org/wiki/MIME_type), string identifier of the action requested, and extra parameters which may be strings or some other serializable data values.

An application may specify an [Intent Filter](http://developer.android.com/guide/topics/intents/intents-filters.html#ifs) in its [Manifest](http://developer.android.com/guide/topics/manifest/manifest-intro.html) which enables application's participation in the [Intent Resolution](http://developer.android.com/guide/topics/intents/intents-filters.html#ires) process. Once an intent has been sent, and Android finds a suitable application, the application is started, and the intent is passed to it (this is similar to starting an Unix or Windows program with command line passed to its main function). The purpose of intent filter is to specify which intents match this application for the purpose of intent resolution.

The Android Home Screen may display a number of shortcut icons. Each icon is associated with an intent to be sent once it is tapped. The process of shortcut creation itself involves sending an intent encapsulating the shortcut intent, so the Home Screen application listens to those intents and creates shortcut icons visible.

Thus, by creation of a shortcut icon on the Home Screen, containing an intent with data MIME type `application/x-squeak-image` and URI scheme `file://`, it becomes possible to launch CogDroid with specific Smalltalk image file. An intent may also encapsulate a command line. The command line is expected to consist of space (or TAB)-separated tokens, forst of which contains a full path to the Smalltalk file to be loaded and executed on CogDroid startup, and the rest are command line arguments. Double-quotes and backslash-escaping of characters are supported.

Currently, CogDroid only instructs Android to match intents with above mentioned MIME type and URI scheme. It ignores the action string, and only uses intent extra data for startup command line.

It is possible to create Home Screen shortcuts programmatically, by sending special messages to the `Android` class object. In order to access the `Android` class object, first install the `Android-Base` package:

```
   Gofer new
     squeaksource: 'EventVM';
     package: 'Android-Base' ;
     load.
```

Once the `Android-Base` package is installed, make sure you have the following:

  * A Smalltalk image with known path to it. For the currently loaded image use `SmalltalkImage current imageName` to obtain the image path. As usual, the `.changes` and `.sources` files are expected to be in the image file's directory.
  * An icon form. It may be produced out of a PNG or JPEG file residing somewhere on the device's local filesystem: use `Form fromFileNamed: '/path/to/the/icon/file'`.
  * Optionally, a startup script file. Such file is expected to contain a valid Smalltalk code to be executed upon CogDroid startup. This, and passing the arguments to the startup script (accessible via System Attributes) is similar to starting Squeak VM in a regular Unix or Windows environment including the startup script path and its arguments after the image name. See an example in [the Squeak VM manual page](http://squeakvm.org/unix/doc/squeak.html), but it is not needed to include the first line starting with `#!` as provided in the example: launching CogDroid off a Home Screen shortcut does not involve any interpretation by Unix shell.

Then, in a workspace evaluate the following:

```
  Android createShortcutFor: aPath 
          withLabel: aLabel
          andCommand: aCmd 
          andIcon: aForm.
```

where `aPath` is absolute path to the Smalltalk image to launch CogDroid with, `aLabel` is a string to be placed on the Home Screen underneath the shortcut icon, `aCmd` is the startup script command line containing the absolute path to the script file, and its arguments space separated, `aForm` is a Form (bitmap) obtained from the icon file. The icon will be autoscaled to 48x48 pixels, but it is not recommended to supply much larger images for peformance reasons.

This message has a shortened form for launching CogDroid without startup script:

```
  Android createShortcutFor: aPath 
          withLabel: aLabel
          andIcon: aForm.
```

If a shortcut is successfully created, Android pops up a brief message (a Toast) notifying about shortcut creation.

Since CogDroid is a [single instance](http://developer.android.com/guide/topics/fundamentals/tasks-and-back-stack.html) application, once it is running, tapping any shortcut icon with an intent resolvable to CogDroid causes the currently running instance to be brought foreground. A notification icon can be seen in the status bar when there is an instance of CogDroid running, so result of tapping any such shortcut icon can be predicted.