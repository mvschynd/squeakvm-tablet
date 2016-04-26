# Introduction #

This Wiki article contains an illustrated demonstration of Squeak on an Android tablet device. Everyone is encouraged to install the Squeak Android application and associated Image on their device, and try to follow the steps described further. You are expected to see screens like the screenshots below. If something goes differently, feel free to report an issue at this project's [Issues](http://code.google.com/p/squeakvm-tablet/issues/list) page.

# Presentation Software Installation #

Proceed to the [Downloads](http://code.google.com/p/squeakvm-tablet/downloads/list) page of this project. Download the [Squeak application](http://squeakvm-tablet.googlecode.com/files/sqandr_pres.apk) and [the Squeak Image](http://squeakvm-tablet.googlecode.com/files/sqandr_pres.zip) on your Android device. Once downloaded, proceed with the apk file installation on the device. For the zipped Image, create a directory named Presentation on the device's SD card (most likely the path to this directory will be `/sdcard/Presentation`). On some devices, there may be path elements preceding this path, but for the rest of this article we assume that the last two path elements are as shown above. Unzip the `squandr_pres.zip` file into that directory. After unzipping, the directory will contain the following files:

  * android.image
  * android.changes
  * SqueakV39.sources
  * DemoProject.001.pr

Locate the installed Squeak icon: it looks like this:

<img src='http://squeakvm-tablet.googlecode.com/hg/project/res/drawable/icon.png'>

Start the Squeak application by tapping the Squeak icon. The list of Squeak images found on the device will appear (most likely it will consist of one item: the Squeak image you just recently unzipped). Tap on the corresponding list item.<br>
<br>
If Squeak starts normally, you will finally see the screen like shown on Fig. 1 below.<br>
<br>
<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/sqandr.png'>
<p align='center'>Fig. 1 Squeak initial screen</p>

<h1>Notes about Touch Screen</h1>

<h2>Squeak and Colored Mouse Buttons</h2>

Squeak's <a href='http://wiki.squeak.org/squeak/30'>Morphic</a> GUI depends on using mouse a lot. Tablet devices regularly have only touch screen which remotely corresponds to a one-button mouse which only reports its location and movement only when the button is pressed. <a href='http://forum.world.st/Mouseover-on-a-touch-screen-td978575.html'>No mouseover on a touch screen</a>. Therefore an attempt was made to emulate this using the few hardware buttons some Android devices have (some Android devices however have only one or no hardware buttons at all, but there are soft buttons displayed on the side of the screen).<br>
<br>
<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/colormouse.png'>
<p align='center'>Fig. 2 Colored mouse buttons (source: <a href='http://squeakbyexample.org/'>Squeak by Example</a>)<br>
</p>

The figure above shows how colors are assigned (in one of <a href='http://wiki.squeak.org/squeak/897'>possible ways</a>) to mouse buttons. Another example of a mouse with colored buttons can be found <a href='http://www.oldmouse.com/mouse/hawley/X063Xjellybeans.shtml'>here</a>.<br>
<br>
By general convention, "Red" button selects objects and brings up primary menus (the <a href='http://wiki.squeak.org/squeak/2350'>World</a> menu), the "Yellow" button brings up secondary (context) menus, and the "Blue" button serves for meta-operations (brings up halos).<br>
<br>
<h2>Emulation of Colored Mouse Buttons</h2>

The Android device used for this project is known as <a href='http://www.pandigital.net/search.asp?Mode=Product&TypeID=43&ProductID=394'>9" Pandigital Novel</a>, marketed in the US as an e-Reader. It can be used as a regular Android tablet device without rooting (No Market access, and USB shell access is not possible though).<br>
<br>
<p align='center'><img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/pdn-land.jpg'>
<p align='center'>Fig. 3 9" Pandigital Novel and Click Color Emulation</p>

Simple tap on the touch screen corresponds to a "Red" click. A tap lasts as long as pressure is applied to the screen. Squeak does not make distinction between long and short taps.<br>
<br>
The Pandigital device has four hardware buttons; three of them can be used by applications. The "page forward" button in the upper right corner (in landscape mode: this is the only mode Android Squeak operates as of now) changes color of the subsequent tap to "Blue". The "page back" button in the lower right corner changes color of the subsequent tap to "Yellow". The second from the top right side button "Menu" brings the soft keyboard on; more on this later. The remaining button returns to the Home Screen, and it cannot be intercepted by applications.<br>
<br>
<h2>Selecting Menu Items on Touch Screen</h2>

Some Squeak menus appear positioned so that the location of mouse click/touch screen tap is inside the menu. It is recommended in such case to move the stylus/finger towards the desired menu item, maintaining pressure on the touch screen, and only release once the desired menu item is highlighted. If however the menu appears so that the tap location is outside the menu, release without moving the stylus/finger on the screen and tap the desired menu item.<br>
<br>
<h1>The Test Drive</h1>

Now that we have reviewed the specifics of interaction with Squeak using the touch screen, we can start our excercise.<br>
<br>
<h2>Selecting a Project to Load</h2>

Squeak has a special project facility based on <a href='http://wiki.squeak.org/squeak/2316'>Image Segments</a>. Project load and save mechanism is independent from loading and saving the Image by Squeak itself. While Squeak on Android does not support image saving, the project facility is functional.<br>
<br>
<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/prmenu.png'>
<p align='center'>Fig. 4 The Project Menu</p>

Tap on the <b>Projects</b> item of the horizontal menu bar. The pulldown menu appears as shown on Fig. 4. Move the stylus/finger towards the <b>Load Project</b> menu item, and release once it highlights.<br>
<br>
<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/prchoose.png'>
<p align='center'>Fig. 5 The Project Selection Menu</p>

Next, the Project Selection menu shows on the screen. In general, it shows all project files found in the current directory (the same directory where the image was loaded from). In our case there is only one project file. Tap the menu item <b>DemoProject.001.pr</b>.<br>
<br>
<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/prloading.png'>
<p align='center'>Fig. 6 The Project Load Progress Indicator</p>

A blinking square appears on the screen while the project is loading.<br>
<br>
<h2>Inside the Project</h2>

<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/prloaded.png'>
<p align='center'>Fig. 7 Inside the Project</p>

At this moment, the project has been loaded. The project file keeps the whole project status, including the screen (World) contents. Note the different screen background pattern.<br>
<br>
The project we have just loaded does not contain much: just a <a href='http://wiki.squeak.org/squeak/1934'>Workspace</a> and few simplest <a href='http://wiki.squeak.org/squeak/1820'>Morphs</a>.<br>
<br>
<h2>Execute Some Code</h2>

<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/bmsel.png'>
<p align='center'>Fig. 8 Select a Line in Workspace</p>

Let's execute (<i>print-it</i>) a <a href='http://fbanados.wordpress.com/2011/02/10/a-tinybenchmark/'>benchmark</a>, displaying the result. To execute a code in Workspace, tap as shown on Fig. 8: at the right of the text; the line will highlight.<br>
<br>
<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/yellowmenu.png'>
<p align='center'>Fig. 9 Yellow Button (Context) Menu</p>

Press the appropriate hardware button to make the subsequent tap Yellow. Then tap somewhere inside the Workspace. The Context Menu appears as shown on Fig.9. Move the stylus/finger to the <b>print it</b> menu item, and release once it highlights.<br>
<br>
<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/bench.png'>
<p align='center'>Fig. 10 Benchmark Results Printed</p>

After few seconds during which Squeak may remain non-responsive, the benchmark result shows up next to the code just executed. You may write down the output along with other information (see the TinyBenchmark page) and send it to the maintainer of this project, or edit the TinyBenchmark page yourself.<br>
<br>
In order to remove the printed output, bring up the Yellow menu again, and select the <b>undo</b> item (not shown, left as the reader's excercise).<br>
<br>
<h2>Live Morphic Modifications</h2>

In Squeak Morphic, it is possible to make modifications to existing GUI elements, and create new elements "on the fly". In this excercise, let's change color of one of the morphs.<br>
<br>
<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/halo.png'>
<p align='center'>Fig. 11 Morphic Halo</p>

Press the appropriate hardware button to make the subsequent tap Blue. Next tap inside the element you want to perform an action upon. The halo appears around the Morph as shown on Fig. 11.<br>
<br>
<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/dbgmenu.png'>
<p align='center'>Fig. 12 The Debug Menu</p>

Tap the <img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/dbgbutton.png'> icon of the halo. The Debug Menu appears.<br>
<br>
<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/inspect.png'>
<p align='center'>Fig. 13 The Object Inspection Window</p>

Tap the <b>inspect morph</b> menu item, then tap somewhere on the window backround pattern. The Object Inspection Window appears as shown on Fig. 13.<br>
<br>
<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/selfcolor.png'>
<p align='center'>Fig. 14 Perform Code on an Object</p>

Tap inside the lower panel of the inspection window, so the blinking cursor appears. Press the appropriate hardware button to bring on the soft keyboard. Type the text as shown on Fig. 14:<br>
<br>
<pre><code>self color: Color black<br>
</code></pre>

This code sends a message to the object being inspected (<code>self</code> when used within an Inspection Window refers to <b>the</b> object, like <code>this</code> in Java) to change its color to black. Once the typing is done, remove the soft keyboard from the screen (by either pressing a hardware button which is used for Yellow click coloring, or by a special soft key on the keyboard, if provided).<br>
<br>
<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/ymcolor.png'>
<p align='center'>Fig. 15 Yellow Menu to Execute Code</p>

Just like it was done with the benchmark, bring on the Yellow menu, and select the <b>do it</b> item.<br>
<br>
<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/nowblack.png'>
<p align='center'>Fig. 16 Morph Changed Color</p>

The screen now looks as shown on Fig 16: the Morph which was initially blue has become black.<br>
<br>
<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/toclose.png'>
<p align='center'>Fig. 17 Closing Inspection Window</p>

Tap on the window close symbol on the title bar of the inspection window as shown on Fig. 17. The window disappears from the screen.<br>
<br>
<h2>Saving the Project</h2>

<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/saveproj.png'>
<p align='center'>Fig. 18 Project Save Dialog</p>

Tap on the <b>Projects</b> item on the Squeak title bar, then move the stylus/finger towards the <b>Save Project</b> item of the projects menu which appears; release when the item highlights. In the project save dialog, tap on the <i>Presentations</i> directory name first (a hair dotted line appears around it) to select where to save, and next tap the <b>Save</b> button.<br>
<br>
<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/saving.png'>
<p align='center'>Fig. 19 Saving Project Progress Bar</p>

After some delay (varies with device, but 10 seconds are not unusual), a progress bar as shown on Fig. 19 appears. Project saving operation may be very fast on a PC, but since mobile devices' CPUs are usually slower, expect it to take some time. Indeed, project saving consists of writing all objects belonging to the project to an external image segment file, preparing a project screenshot (that small iconic picture above the directory selection box), and zipping everything together (technically <code>.pr</code> files are zip archives).<br>
<br>
<h2>Loading a Saved Project</h2>

Now, as the last part of our test drive, let's try to load the project we just saved. First, stop the Squeak application. Stop it just to have a clean experiment. In regular use, it is of course not needed to be stopped just to load a project. You may need to open the applications manager on your device, and force stop of the running Squeak. Then start it again, go through selection of the image to load, and proceed to the project load menu.<br>
<br>
<p align='center'>
<img src='http://wiki.squeakvm-tablet.googlecode.com/hg/pics/loadtwo.png'>
<p align='center'>Fig. 20 Saved Project Versions</p>

Now you see the two versions of the DemoProject: 001 which was initially zipped with the Image, and 002 which we just saved after changing color of one of its morphs. Try to load the 002 version.<br>
<br>
<b>Morphs of what colors do you see now?</b>

<h1>Spoiler</h1>

As it was briefly mentioned in the beginning of this article, author's tablet device does not allow USB access. So no screenshots could be obtained from the device itself. The screenshots you saw in this article, were obtained by loading the same presentation image in a Linux PC Squeak VM with window resized to the device screen dimensions. Identical actions were performed on both PC and the device. Screens looked almost identical (except for some file names shown on menus, which are intentionally blurred on this article's figures). But here is the beauty of Squeak: develop and debug your application project on a PC, then transfer the project file on a tablet device. You get exact same GUI layout, and if some final fixes are needed, the development environment is still with you.<br>
<br>
Try to run Eclipse on a tablet ;)