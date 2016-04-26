

# Introduction #

There are two CI (continuous integration) jobs running periodically at the [INRIA Jenkins](https://ci.lille.inria.fr/pharo/) server: [CogDroid](https://ci.lille.inria.fr/pharo/job/CogDroid/) and [PharoDroid](https://ci.lille.inria.fr/pharo/job/PharoDroid/). The former is an unbundled NonJIT Cog VM for Android which can run any Smalltalk image original [Cog VM](http://www.mirandabanda.org/cogblog/) can run. The latter is the same Cog VM bundled with a generic [Pharo 1.4](http://www.pharo-project.org/home) Smalltalk image. These two builds complement each other, and together they comprise an [Android](http://www.android.com/) analog of the [one-click](http://gforge.inria.fr/frs/download.php/31258/Pharo-1.4-14557-OneClick.zip) Pharo distribution: a practically usable piece of software ready for development and exploration of possible uses of Smalltalk on mobile devices.


# Highlights #

Both CogDroid and PharoDroid are Android applications (apks) built from the same [C/Java](https://gitorious.org/~golubovsky/cogvm/dmg-blessed) and [Smalltalk](http://source.squeakfoundation.org/VMMaker/) sources. Both apks are [signed with debug key](http://developer.android.com/tools/publishing/app-signing.html#debugmode), and are not available from Google Play. Both apks are built for two hardware architectures: Intel x86 and ARM. They can be used on any device running Android 2.0 and higher, with 256M or more RAM installed. In order to install them, installation from [unknown sources](http://developer.android.com/tools/publishing/publishing_overview.html#unknown-sources) should be enabled on the device.

## CogDroid ##

The CogDroid CI job builds the unbundled (that is, no Smalltalk image is included with the application) version of Android Cog VM. The job provides the following artifacts:

  * [Installable Cog VM for the ARM architecture](https://ci.lille.inria.fr/pharo/job/CogDroid/lastSuccessfulBuild/artifact/platforms/android/derived/generic/bin/CogDroid-debug.apk)
  * [Installable Cog VM for the Intel x86 architecture](https://ci.lille.inria.fr/pharo/job/CogDroid/lastSuccessfulBuild/artifact/platforms/x86android/derived/generic/bin/CogDroid86-debug.apk)
  * [Pre-built native Cog VM for the ARM architecture](https://ci.lille.inria.fr/pharo/job/CogDroid/lastSuccessfulBuild/artifact/platforms/android/nativeVM-arm.zip)
  * [Pre-built native Cog VM for the Intel x86 architecture](https://ci.lille.inria.fr/pharo/job/CogDroid/lastSuccessfulBuild/artifact/platforms/x86android/nativeVM-x86.zip)
  * [A template for building bundled and branded versions of Cog VM](https://ci.lille.inria.fr/pharo/job/CogDroid/lastSuccessfulBuild/artifact/platforms/android/makevm.zip)

The installable Cog VMs are Android applications which can be installed by following the links above on an Android device. The pre-built native VMs together with the template for building bundled and branded VMs are useful for packaging the Cog VM with any Smalltalk image containing installed applications, thus allowing to distribute Smalltalk applications for easy installation by Android users. An example of such bundled and branded application available from Google Play is [DrGeo](https://play.google.com/store/apps/details?id=eu.drgeocv.android&feature=search_result#?t=W251bGwsMSwyLDEsImV1LmRyZ2VvY3YuYW5kcm9pZCJd).

## PharoDroid ##

The PharoDroid CI job uses the artifacts from the CogDroid build and the Smalltalk Pharo-1.4 [image](https://ci.lille.inria.fr/pharo/job/Pharo%201.4/lastSuccessfulBuild/artifact/Pharo-1.4/Pharo-1.4.image) from the [Pharo-1.4](https://ci.lille.inria.fr/pharo/job/Pharo%201.4/) CI job, and provides the following artifacts:

  * [The installable application for the ARM architecture](https://ci.lille.inria.fr/pharo/job/PharoDroid/lastSuccessfulBuild/artifact/arm/makevm/bin/PharoDroid-arm-debug.apk)
  * [The installable application for the Intel x86 architecture](https://ci.lille.inria.fr/pharo/job/PharoDroid/lastSuccessfulBuild/artifact/x86/makevm/bin/PharoDroid-x86-debug.apk)

The VM and the image are bundled together in this application, and thus install together. The bundled image is immutable, that is, even if the **Save** item of the World menu is used, subsequent starts of the application will erase all changes, and the image will be loaded in its initial state. Sources and Changes files are not included with the application to reduce its size.

With immutable Smalltalk image there is no risk to corrupt it by accident running experimental code. The ScriptLoader class included with the image helps define the behavior during startup (for example, to install and run a particular application). If the startup code acts incorrectly, it can be easily edited/erased with Android provided tools, and then recreated from the VM GUI again.

One incovenience of the immutable image is inability to quickly and easily save the current changes made to the image. It is however possible, by having both CogDroid and PharoDroid installed, to save the changes in a separate mutable image file, and use that image with CogDroid which acts as a regular PC installation of Cog VM in regards to saving and loading of Smalltalk images.

It is necessary to note that CogDroid cannot use the Smalltalk image bundled with PharoDroid as the latter is located in the application-private files area. Images that CogDroid can use should be placed under the root directory of the SD card.

It is also possible to create a Home Screen shortcut which appears as an icon which, when touched, launches CogDroid with a particular Smalltalk image and startup script with arguments, much like desktop icons in Linux graphics desktop environments and Windows.

# Installation and Removal #

To install CogDroid and/or PharoDroid, touch the link to the installable application for the proper architecture (ARM or Intel x86) in the web browser on the Android device, and follow the further instructions (may differ between Android versions). Both applications request certain permissions in order to be used, such as reading/writing external storage (SD card) and using the network. If installation fails, check whether installation from [unknown sources](http://developer.android.com/tools/publishing/publishing_overview.html#unknown-sources) is enabled on the device. Also, check for the previously installed versions of CogDroid and PharoDroid: they may need to be removed prior to installations of newer versions.

To uninstall either (or both) applications use the Android settings menu (the Applications section) and follow the usual process for particular Android version. The image bundled with PharoDroid will be removed along with the applications. Mutable images that CogDroid might use will not be removed automatically.

# Touch Screen Considerations #

Touchscreen operation of Smalltalk GUI differs from the standard desktop one. It is recommended to use stylus unless application's interface is configured to have really large elements.

Both PharoDroid and CogDroid are locked in the landscape mode (this may be changed in a bundled VM by editing the proper [element](http://developer.android.com/guide/topics/manifest/activity-element.html#screen) of the AndroidManifest.xml file).

Simple screen touch corresponds to left (red) mouse click. The "Menu" hardware button (or mouse's middle button/wheel in Android-x86) causes the on-screen keyboard to show. The "Back" hardware button cycles between two other mouse click colors: yellow (middle) and blue (right). The click color currently set is shown in a briefly appearing popup message (a Toast). The click color set by the "Back" hardware button applies only to the subsequent touch on the screen. So, in order to perform a do-it in a workspace, press the "Back" button twice (the "Mouse blue" Toast will appear) and then touch the workspace area: the context menu will appear. Touch the "Do it (d)" item, and the action will be performed.

Cog VM does not differentiate between long and short touches unless a particular Smalltalk application does its own timing.

When navigating a hierarchical menu (such as World menu -> **Tools** -> **Finder**) is is recommended to keep the stylus on the screen until the final menu item is reached, and lift it off the screen only afterwards. Windowed elements may be moved by touching and dragging them by title bar, but they disappear from the screen while being moved. Resizing of windowed elements is possible by dragging their corner/border but due to small border thickness may be not so easy.

# The Android-Base Package #

The Android-Base package provides methods specific to the Android platform. It is automatically included into the image bundled with PharoDroid. In order to install this package into an existing image, perform the following:

```smalltalk

Gofer new
squeakfoundation: 'VMMaker';
package: 'Android-Base' constraint: [ :v | v author = 'golubovsky' ];
load.
```

The package exposes a number of primitives as methods of the `Android` class; some of them will be reviewed below in greater details; the rest can be studied by browsing the source code of the package under the [VMMaker](http://source.squeak.org/VMMaker.html) Smalltalk repository.

## Home Screen Shortcuts ##

Android allows programmatic creation of Home Screen shortcut icons. Each Home Screen shortcut icon (launcher icon) is associated with a certain [Intent](http://developer.android.com/reference/android/content/Intent.html) (a message that Android applications send around to communicate with other applications) which is broadcast once an icon is touched (or clicked). Android determines the recipient of an Intent based on the content type and resource identifier (URI) the Intent contains. CogDroid when installed registers itself as a recipient of intents with content type `application/x-squeak-image` and URI scheme `file` (as in `file:///path/to/file` URI).

The Android-Base package provides the following primitive:

```smalltalk

createShortcutFor: aPath withLabel: aString andCommand: aCmd iconWH: xy iconFlags: flg iconBits: b
"create a home screen shortcut for a given image"
<primitive: 'primShortCut' module: 'AndroidPlugin'>
self primitiveFailed.
```

and a convenience method:

```smalltalk

createShortcutFor: aPath withLabel: aString andCommand: aCmd andIcon: aForm
"create a home screen shortcut for a given image"
Android createShortcutFor: aPath
withLabel: aString
andCommand: aCmd
iconWH: ((aForm width << 16) bitOr: (aForm height))
iconFlags: 0
iconBits: (aForm bits asByteArray).
```

which accepts a `Form` which can be read from a regular JPEG or PNG file containing the icon to be displayed on the Home Screen for the shortcut. Original icon size may be arbitrary: it will be automatically scaled to 48x48 pixels. The `aPath` argument contains path to the image (it is not checked for existence). The `aString` argument contains the desired icon label to be displayed on the home screen underneath the icon. The `aCmd` argument is the command line passed to the Cog VM along with the image: the first element of the command line contains startup script name, and the rest are startup script arguments (space separated).

## Idle Timer Setting ##

The Android-Base package contains declarations of [Pharo System Settings](http://book.pharo-project.org/book/Tidbits/CustomizingPharo/DeclaringSetting/) to adjust idle VM timer interval.

<img src='http://squeakvm-tablet.googlecode.com/files/andro-settings.png'>

Since the Android version of Cog VM is <a href='http://lists.squeakfoundation.org/pipermail/vm-dev/2009-November/003385.html'>event-driven</a>, it only gets control from Android when some user action (such as touch screen action or keyboard input, if present) occurs. It is however necessary to periodically give control to the VM in order for its internal timers to work properly. This is achieved by using a special idle VM timer: each time when the timer expires, and there is no user input, VM gets control from Android. If the VM idle timer interval is too short, internal VM timers provide better accuracy, but battery power consumption is higher (if the battery is concerned, like in a mobile device). If the VM idle timer interval is too long, the battery power will be consumed only if there is intensive user input; otherwise VM will not get control from Android. A good visual illustration of idle VM timer effect is the blinking cursor in a workspace: if the VM idle timer interval is considerably long, the cursor stops blinking once there is no user input.<br>
<br>
When running a generic Pharo image, it is not recommended to alter this setting. If however a mobile-specific application is running, which can be accomodated to the VM not getting control from Android if there is no user input at all, the interval can be set to tens or hundreds of seconds, thus reducing battery power consumption.<br>
<br>
<h2>Other Features</h2>

<h3>Brief Messages (Toasts)</h3>

The Android-Base package provides the following method of the <code>Android</code> class:<br>
<br>
<pre><code><br>
briefMessage: aString<br>
"display a brief message (a toast) by means of Android runtime"<br>
&lt;primitive: 'primBriefMessage' module: 'AndroidPlugin'&gt;<br>
self primitiveFailed.<br>
</code></pre>

Calling this method with a string argument causes a brief message (<a href='http://developer.android.com/guide/topics/ui/notifiers/toasts.html'>Toast</a>) to appear on the device screen. Such messages may be useful to provide notifications about some events which do not require any immediate actions form user.<br>
<br>
<h3>Interface with TTS</h3>

The Android-Base package provides the following primitive methods to interact with the <a href='http://developer.android.com/reference/android/speech/tts/TextToSpeech.html'>Android Text To Speech</a> (TTS) service:<br>
<br>
<pre><code><br>
speak: aString<br>
"speak the given string via TTS"<br>
&lt;primitive: 'primSpeak' module: 'AndroidPlugin'&gt;<br>
self primitiveFailed.<br>
</code></pre>

<pre><code><br>
setPitch: aFloat<br>
"set TTS synthesized speech pitch"<br>
&lt;primitive: 'primSetPitch' module: 'AndroidPlugin'&gt;<br>
self primitiveFailed.<br>
</code></pre>

<pre><code><br>
setSpeechRate: aFloat<br>
"set TTS synthesized speech rate"<br>
&lt;primitive: 'primSetSpeechRate' module: 'AndroidPlugin'&gt;<br>
self primitiveFailed.<br>
</code></pre>

Availability of the TTS service depends upon the device language settings and the configuration of the TTS installed: certain TTS configurations may not support certain device language and locale settings combinations.<br>
<br>
<h3>Reading from Android Clipboard</h3>

The Android-Base package provides the following method (without arguments):<br>
<br>
<pre><code><br>
getClipboardString<br>
"get a string from Android clipboard"<br>
&lt;primitive: 'primGetClipboardString' module: 'AndroidPlugin'&gt;<br>
self primitiveFailed.<br>
</code></pre>

The method returns a string value (possibly of zero length if Android clipboard is empty) which represents the textual contents of Android clipboard (placed there by another Android application).<br>
<br>
<h1>TrueType Fonts Support</h1>

The Android Cog VM is built with the FreeType plugin (FT2Plugin) enabled by default, and the Pharo image bundled with PharoDroid is adjusted in order to use TrueType fonts by default.<br>
<br>
<img src='http://squeakvm-tablet.googlecode.com/files/andro-ttf.png'>

The PharoDroid installable application includes the basic set of <a href='https://fedorahosted.org/liberation-fonts/'>Liberation</a> fonts (v2.0), so they are installed with the application. The screenshot above is related to PharoDroid.<br>
<br>
In the process of PharoDroid bundled image preparaion, the following change is applied:<br>
<br>
<pre><code><br>
! !FreeTypeFontProvider methodsFor: 'file paths'!<br>
getUnixFontFolderPaths<br>
^ #('/usr/share/fonts' '/usr/local/share/fonts' '/sdcard/fonts' )! !<br>
</code></pre>

which adds an Android-specific directory to the list of places where the FreeType font manager looks for the <code>*.ttf</code> files.<br>
<br>
Thus, if a directory <code>/sdcard/fonts</code> is created, and some TrueType font files are placed there, PharoDroid will see them automatically. The same change needs to be applied to images to be loaded with CogDroid in order for these fonts to become usable. Otherwise, another "universal" (yet per-image) location where TTF files are looked for is the <code>Fonts</code> subdirectory in the loaded Smalltalk image directory.<br>
<br>
<h1>Starting PharoDroid</h1>

The only way to start PharoDroid is via launching the application (most typically by touching/clicking the installed application icon). Since PharoDroid is built based on the bundled VM template, it does not register an intent filter to respond to any intents associated with Smalltalk images.<br>
<br>
PharoDroid, while running, shows a <a href='http://developer.android.com/guide/topics/ui/notifiers/notifications.html'>status notification icon</a> looking like a miniature Pharo logo. There may be only one instance of PharoDroid running at the time. If the PharoDroid notification icon is visible on the left side of the Android status bar, touching the PharoDroid Home Screen icon brings the currently running instance on the top of the <a href='http://developer.android.com/guide/components/tasks-and-back-stack.html'>tasks stack</a>.<br>
<br>
<h2>Using StartupLoader Scripts</h2>

StartupLoader is a class which allows to define Pharo's startup actions without changing the image and thus perfectly suitable for use with immutable images. When a StartupAction instance is added to the list of desired startup actions, its code is stored in an external file at certain location, and next time when PharoDroid starts up, those files are read in and interpreted. This also provides an easy way to correct startup actions by direct editing their corresponding files by any text editor available in Android (or from Pharo's own file browser), or simply deleting them when no longer needed.<br>
<br>
Note though that location of startup files recognized by PharoDroid differs from the standard Pharo location (since each bundled VM should be able to define its own location). In particular, the following change is applied to the image bundled with PharoDroid:<br>
<br>
<pre><code><br>
!<br>
<br>
!UnixFileDirectory class methodsFor: '*StartupPreferences'!<br>
preferencesFolder<br>
| t1 t2 t3 t4 t5 |<br>
t1 := '$CONFIG/' , SystemVersion current dottedMajorMinor.<br>
t2 := {Android getSDCardRoot}.<br>
t3 := t2 joinUsing: self slash.<br>
t3 := self slash , t3 , self slash.<br>
t4 := t3 , t1 , self slash.<br>
t5 := self forFileName: t4.<br>
^ t5! !<br>
<br>
!UnixFileDirectory class methodsFor: '*StartupPreferences'!<br>
preferencesGeneralFolder<br>
| t1 t2 t3 t4 t5 |<br>
t1 := '$CONFIG/general'.<br>
t2 := {Android getSDCardRoot}.<br>
t3 := t2 joinUsing: self slash.<br>
t3 := self slash , t3 , self slash.<br>
t4 := t3 , t1 , self slash.<br>
t5 := self forFileName: t4.<br>
^ t5! !<br>
</code></pre>

where <code>$CONFIG</code> is an externally defined in the build script shell variable (<code>pharodroid</code> in our case), <code>getSDCardRoot</code> is a method in the <code>Android</code> class which in turn calls the <a href='http://developer.android.com/reference/android/os/Environment.html#getExternalStorageDirectory()'>getExternalStorageDirectory</a> Android runtime method to determine the SD card mountpoint (usually <code>/sdcard</code> or <code>/mnt/sdcard</code>). Thus, the directories where StartupLoader looks for scripts are <code>/mnt/sdcard/pharodroid/general</code> and <code>/mnt/sdcard/pharodroid/1.4</code>.<br>
<br>
The following Smalltalk code (based on the StartupLoader example distributed with Pharo) adds a startup action to open a workspace with given text.<br>
<br>
<pre><code><br>
| t1 |<br>
t1 := StartupAction<br>
name: 'Open Help'<br>
code: 'Workspace openContents: ''Here is just an example of how to use the StartupLoader'''<br>
runOnce: true.<br>
StartupLoader default addAtStartupForGeneral: {t1}.<br>
</code></pre>

This code has to be executed (do-it'd) in a workspace in order to add the startup action to the startup actions sequence.<br>
<br>
<h1>Starting CogDroid</h1>

CogDroid is not bundled with any Smalltalk image: it is a standalone VM. It can be started in two ways: one via touching the CogDroid Home Screen icon and selecting the Smalltalk image to load from the list of images, or via intent carrying the path to the desired Smalltalk image and optionally startup script name and arguments. Such intent most likely originates from touching a Home Screen shortcut icon associated with the given Smalltalk image and startup arguments, however it may be broadcast by any other application. The Android-Base package contains a facility to create a Home Screen shortcut for the given Smalltalk image, but it does not provide a facility to broadcast an intent associated with any Smalltalk image.<br>
<br>
CogDroid, while running, shows a <a href='http://developer.android.com/guide/topics/ui/notifiers/notifications.html'>status notification icon</a> looking like a miniature CogDroid logo: <img src='http://gitorious.org/~golubovsky/cogvm/dmg-blessed/blobs/raw/19f84368ac68695a617ec3a1a1b27175ac07323e/platforms/android/derived/generic/res/drawable/ntficon.png' height='20' width='20'>. There may be only one instance of CogDroid running at the time. If the CogDroid notification icon is visible on the left side of the Android status bar, touching the CogDroid Home Screen icon brings the currently running instance on the top of the <a href='http://developer.android.com/guide/components/tasks-and-back-stack.html'>tasks stack</a>.<br>
<br>
CogDroid and PharoDroid can run simultaneously as well as any number of other Cog VM based applications as long as applications' <a href='http://developer.android.com/guide/topics/manifest/manifest-element.html#package'>package names</a> are different. Note though that Cog VM consumes considerable memory and CPU resources especially with low values of the idle VM timer interval. Simultaneous running of several Cog VM based applications may considerably degrade performance of an Android device.<br>
<br>
<h2>Smalltalk Image Selection from Menu</h2>

If CogDroid is started by touching the Home Screen icon associated with the application itself rather than with any Smalltalk image, a list of Smalltalk images (files matching the <code>*.image</code> pattern) found under the external storage (SD card) mount point is displayed as shown below (the list may be very dense if there are many image files found).<br>
<br>
<img src='http://squeakvm-tablet.googlecode.com/files/image-list.png'>

Touching any of items of this list causes CogDroid to load the selected image file as if there were no other command line arguments, just the image name.<br>
<br>
If the selected image file is not write-protected, CogDroid may save its object memory state in the image, that is, behaves in the same way as desktop Cog VM.<br>
<br>
<h2>Starting via Intent</h2>

The CogDroid application is configured via its AndroidManifest.xml file to act as an intent filter, that is, to respond in the process of dispatching of Intents matching certain criteria.<br>
<br>
An Intent that CogDroid would respond to may be created by sample Java code below:<br>
<br>
<pre><code><br>
Intent sci = new Intent();<br>
sci.setAction(Intent.ACTION_VIEW);<br>
Uri uri = new Uri.Builder().scheme("file").path(imagePath).build();<br>
sci.setDataAndType(uri, "application/x-squeak-image");<br>
sci.putExtra("command", cmd);<br>
</code></pre>

which means that an Intent is associated witn an URI of scheme <code>file</code> pointing at the Smalltalk image to be loaded by CogDroid, with MIME type <code>application/x-squeak-image</code> which appears to be an agreed-upon content type for Smalltalk images, and an extra named parameter <code>command</code> containing the command line as if it were typed on a Linux or Windows console in order to start Cog: startup script (Smalltalk code) name, and additional arguments, space-separated.<br>
<br>
URIs with schemes other than <code>file</code> are not supported at the moment. CogDroid has limited recognition of double-quoted command line arguments that may contain spaces as well as escaped (with backslash) characters.<br>
<br>
Note also that even though CogDroid is started by touching some icon associated with a Smalltalk image, the notification icon of CogDroid is always the same, the miniature CogDroid logo.<br>
<br>
<h1>Cog VM Bundling and Branding</h1>

This section discusses in details how to bundle and brand a Cog VM with some application using artifacts from the CogDroid CI job build.<br>
<br>
<h2>Prerequisites</h2>

It is recommended to perform the build on a 64bit Linux machine. 2G RAM may be sufficient. Plan to use at least 30G of disk storage (mainly for instrumental software installation).<br>
<br>
Make sure that the following software is installed:<br>
<br>
<ul><li><a href='http://www.oracle.com/technetwork/java/javase/downloads/index.html'>Oracle JDK</a> is recommended (<a href='http://openjdk.java.net/'>OpenJDK</a> may or may not work)<br>
</li><li><a href='http://ant.apache.org/'>Apache ANT</a> v1.8.x<br>
</li><li><a href='http://developer.android.com/sdk/index.html'>Android SDK</a></li></ul>

Make sure all components of Android SDK are installed. Run<br>
<br>
<pre><code>android update sdk -u<br>
</code></pre>

to download them.<br>
<br>
<h2>Artifacts</h2>

The following artifacts are to be downloaded from the CogDroid artifacts area:<br>
<br>
<ul><li>The bundled VM template: <a href='https://ci.lille.inria.fr/pharo/job/CogDroid/lastSuccessfulBuild/artifact/platforms/android/makevm.zip'>makevm.zip</a>
</li><li>The prebuilt native VM for the desired CPU architecture: <a href='https://ci.lille.inria.fr/pharo/job/CogDroid/lastSuccessfulBuild/artifact/platforms/android/nativeVM-arm.zip'>nativeVM-arm.zip</a> or <a href='https://ci.lille.inria.fr/pharo/job/CogDroid/lastSuccessfulBuild/artifact/platforms/x86android/nativeVM-x86.zip'>nativeVM-x86.zip</a>.</li></ul>

Using a pre-built native VM makes it unnecessary to deal with Android NDK.<br>
<br>
<h2>Bundling and Branding Items</h2>

Prepare a Smalltalk image to be bundled. Make sure the image does not require Changes and Sources files by executing the following during the image build:<br>
<br>
<pre><code><br>
SmalltalkImage checkSourcesFileAvailability: false.<br>
SmalltalkImage checkChangesFileAvailability: false.<br>
</code></pre>

Split the Smalltalk image into 1M chunks. Create an empty directory somewhere, and change there. Run the following command:<br>
<br>
<pre><code>split -d -b 1m /path/to/myapplication.image myapplication.<br>
</code></pre>

After this command finishes, the directory where it was run will contain files named like <code>myapplication.00</code>, <code>myapplication.01</code>, etc, each 1M or less (the last one) in size.<br>
<br>
Create two PNG files to be used as the application icon (128x128 pixels) and the notification icon (48x48 pixels).<br>
<br>
Prepare any files to be placed into the image directory and below when the bundled VM is running, and zip them into a .zip file of arbitrary name - to be referred to as FFF.zip in this section. To clarify: files zipped at the root of the FFF.zip archive will be unpacked into the Smalltalk image directory (which can be addressed as <code>FileDirectory default</code> from Smalltalk code). Files zipped at subdirectories under the FFF.zip root will be unpacked into subdirectories located under the Smalltalk image directory.<br>
<br>
<h2>Identification</h2>

Define the following names:<br>
<br>
<ul><li>Domain name for the application (will also be used as the <a href='http://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html'>package name</a>) in format xx.yy.zz (dot-separated).<br>
</li><li>Main activity name: any identifier allowed for <a href='http://docs.oracle.com/javase/tutorial/java/javaOO/classdecl.html'>Java class name</a> - it will be referred to as MMM in this section.<br>
</li><li>APK build name: any identifier suitable for a file name; recommended to be the same as the main activity name with CPU architecture appended after hyphen - it will be referred to as AAA-aaa in this section.</li></ul>

<h2>Unzipping the Sources</h2>

Make an empty directory. Unzip the <code>makevm.zip</code> file there. This creates the <code>makevm</code> subdirectory. Change to the <code>makevm</code> subdirectory and keep it the current directory while building the application.<br>
<br>
Unzip the <code>nativeVM-xxx.zip</code> (xxx being the CPU architecture). This creates the <code>project</code> subdirectory.<br>
<br>
<h2>Preparing the Build</h2>

Run the following command:<br>
<br>
<pre><code>  android update project -p . -t android-4 -l ./project<br>
</code></pre>

in order to properly initialize Android build system for this project.<br>
<br>
Create the hierarchy of directories for the main activity Java source file. If the package name chosen is xx.yy.zz then the directories are created with the following command line:<br>
<br>
<pre><code>  mkdir -p src/xx/yy/zz<br>
</code></pre>

<h2>Adding Java Sources</h2>

Create the main activity Java source file: <code>src/xx/yy/zz/MMM.java</code> where MMM is the identifier chosen for the Main activity name. The file at its simplest can be as follows:<br>
<br>
<pre><code><br>
package xx.yy.zz;<br>
import org.golubovsky.cogstack.CogActivity;<br>
public class MMM extends CogActivity<br>
{<br>
}<br>
</code></pre>

Naming the main activity Java source such way creates a unique package name for the application which is an unique identifier of the application for Android.<br>
<br>
<h2>Configuring Assets and Resources</h2>

Make sure subdirectory <code>assets/zipped</code> exists under the <code>makevm</code> directory.<br>
<br>
Symlink the directory where split parts of the application image reside as <code>assets/image</code>. The command<br>
<br>
<pre><code>  ls -l assets/image/<br>
</code></pre>

should print the full list of image chunks with their proper sizes.<br>
<br>
Copy the previously created FFF.zip file into the <code>assets/zipped</code> subdirectory under <code>makevm</code>.<br>
<br>
Replace certain substitute tokens in several files as equivalent of the following <code>sed</code> commands:<br>
<br>
<pre><code>  sed -i "s/@@PACKAGE@@/\"xx.yy.zz\"/g" AndroidManifest.xml<br>
  sed -i "s/@@ACTIVITY@@/\"MMM\"/g" AndroidManifest.xml<br>
  sed -i "s/@@BUILD@@/\"AAA-aaa\"/g" build.xml<br>
  sed -i "s/@@BUILD@@/AAA/g" res/values/strings.xml<br>
</code></pre>

If the commands above are used, they update files in place, and only need to be issued before the first build.<br>
<br>
Copy the 128x128 pixels icon file as <code>res/drawable/icon.png</code>.<br>
Copy the 48x48 pixels icon file as <code>res/drawable/ntficon.png</code>.<br>
<br>
<h2>Building</h2>

Run the following commands:<br>
<br>
<pre><code>  ant clean &amp;&amp; ant debug<br>
</code></pre>

to build the application. The installable apk file will be under <code>bin</code>: <code>AAA-aaa-debug.apk</code>.<br>
<br>
The new bundled and branded Cog VM has been built. When installed, it will show the 128x128 pixels icon on the Applications panel, or the Home Screen. When running, the 48x48 pixels icon will show up in the status notifications bar.<br>
<br>
When launched, it should start directly into the application it was bundled with.<br>
<br>
<h2>Final Notes</h2>

This section covers only steps absolutely necessary to build a working bundled Cog VM. It omits other aspects such as modifications of the generic Pharo image to create the image to be bundled, and possible modifications of the AndroidManifest.xml file. Developers are advised to consult proper pieces of Android and Pharo documentation in order to learn about these possibilities.<br>
<br>
<h1>Conclusions and Acknowledgements</h1>

This Wiki article discussed briefly the details of use and extension of the two Android applications based on Cog VM. Due to the open source nature of this project, much deeper transformations are possible. Please share your ideas in the related online forums and discussions list.<br>
<br>
Special thanks to the maintainers of the INRIA Jenkins CI server who made it possible to set up these jobs.<br>
<br>
Special thanks to Hilaire Fernandes who explored the possibilities of Cog VM bundling and branding in the process of porting the DrGeo application to Android.<br>
<br>
Special thanks to the Smalltalk and Pharo community members who helped develop the Android port of Cog VM.<br>
<br>
The developer of CogDroid and PharoDroid hopes that these new Android applications will help the adoption of Smalltalk for writing applications for mobile devices.