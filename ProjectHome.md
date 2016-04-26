This is a fork of the [squeak-android-vm](http://code.google.com/p/squeak-android-vm/) project targeting tablet devices, that is, taking advantage of larger screen, memory, and storage than a mobile phone may provide.

As of April 2011, the project has reached its first milestone: to provide public packages for easy experimentation. Download the [application](http://squeakvm-tablet.googlecode.com/files/sqandr_pres.apk) and the zipped Squeak [image](http://squeakvm-tablet.googlecode.com/files/sqandr_pres.zip), and take the TestDrive.

As of August 12, 2011, Android port of Stack Cog VM (CogDroid) was compiled and tested with a recent PharoCore image. See the CogNotes wiki page for further information.

As of September 3, 2011, Android port of Stack Cog VM (CogDroid) has reached public alpha stage. The installable application is [available](http://squeakvm-tablet.googlecode.com/files/CogDroid-alpha-20110918.apk). It is recommended to use it with a recent [Pharo](http://www.pharo-project.org/pharo-download) image.

On September 28, 2011, the [Android-x86](http://www.android-x86.org) platform was added to the CogDroid source tree. Most of the source code is shared between ARM and x86 architectures. The following [application](http://squeakvm-tablet.googlecode.com/files/CogDroid86-alpha-20110928.apk) can be tried on Android-x86 (it is recommended to use live ISO images for the 2.3 version of Android-x86).

On October 9, 2011, new features are:
  * better support for keyboard (arrows, home, end keys), mouse wheel
  * it is now possible to create home screen shortcuts to launch CogDroid with image file specified, and also with optional startup script and arguments, so going through the startup list of images can be avoided, and certain startup actions can be programmed.

As of July 2012, continuous integration job to build CogDroid exists at [Jenkins (INRIA)](https://ci.inria.fr/pharo-contribution/). While not very regularly running, CogDroid Beta unbundled VM builds are available at [this page](https://ci.inria.fr/pharo-contribution/job/CogDroid/). VM builds bundled with Pharo-1.4 (PharoDroid) image are available at [this page](https://ci.lille.inria.fr/pharo/job/PharoDroid/).

On Aug 7, 2012, CogDroid and PharoDroid Jankins builds are officially [announced](http://lists.squeakfoundation.org/pipermail/vm-dev/2012-August/011090.html). Read the JenkinsBuilds wiki page for documentation.

All downloads from this site are deprecated now (search among all downloads to see them). Please consider downloading public Betas from [CogDroid](https://ci.inria.fr/pharo-contribution/job/CogDroid/) and [PharoDroid](https://ci.lille.inria.fr/pharo/job/PharoDroid/) builds.

Please provide your feedback on the [Issues](http://code.google.com/p/squeakvm-tablet/issues/list) page, and by entering comments to the Wiki pages.