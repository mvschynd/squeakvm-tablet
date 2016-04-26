This page contains the output of the Tiny Benchmark run on various Android-powered hardware (tablets, telephones). Send me (or add here) yours.

| **Vendor/Model**| **CPU**| **Android Version**| **ByteCodes/sec**| **Sends/sec**| **VM**| **Image**|
|:----------------|:-------|:-------------------|:-----------------|:-------------|:------|:---------|
|Pandigital Novel eReader 9"|S3C6410 800Mhz|     2.0            |15,686,274        |402,212       |Classic|Squeak 3.9|
|Nexus One        |1Ghz    |N/A                 |30,000,000        |1,000,000 <sup>a</sup>     |Classic|Squeak 3.9|
|HTC Droid Incredible|Qualcomm 1GHz Snapdragon |N/A                 |31,250,000        |857,762 <sup>b</sup> |Classic|Squeak 3.9|
|Samsung Galaxy Tab |Cortex A8 1GHz|     2.2            |33,826,638        |1,185,241     |Classic|Squeak 3.9|
|Samsung Galaxy Tab 7"|N/A     |N/A                 |33,684,210        |1,128,189     |Classic|Squeak 3.9|
|Acer Iconia      |Dual core 1GHz Tegra 2 (VM using one core)|     3.01           |37,058,482        |1,205,854  <sup>c</sup>   |Classic|Squeak 3.9|
|Eken M009        |VIA 8650 @ 800 Mhz 256MB RAM|Uberoid (2.2?)      |14,828,544        |419,778       |Classic|Squeak 3.9|
|Motorola Atrix   |Tegra 2 dualcore, 1Ghz|2.2                 |37,405,026        |1,210,239     |Classic|Squeak 3.9|
|Pandigital Novel eReader 9"|S3C6410 800Mhz|     2.0            |19,631,901        |236,590 <sup>d</sup>   |Stack Cog|Squeak 3.9|
|Pandigital Novel eReader 9"|S3C6410 800Mhz|     2.0            |17,344,173        |405,702 <sup>d</sup>   |Stack Cog|Squeak 3.9|
|Notion Ink Adam  |Dual-core ARM Cortex -A9, Nvidia Tegra 250 1Ghz|     2.1            |36,930,178        |1,201,501     |Classic|Squeak 3.9|
|Notion Ink Adam  |Dual-core ARM Cortex -A9, Nvidia Tegra 250 1Ghz|     2.1            |41,775,456        |2,074,373 <sup>e</sup>  |Stack Cog α|Squeak 3.9|
|Pandigital Novel eReader 9"|S3C6410 800Mhz|     2.0            |20,075,282        |934,960       |Stack Cog α|Squeak 3.9|
|Pandigital Novel eReader 9"|S3C6410 800Mhz|     2.0            |20,012,507        |1,006,318     |Stack Cog α|PharoCore 1.3|
|4G Systems oneTab|Rockchip RK2818 650 Mhz |     2.1            |12,976,480        |351,129       |Classic|Squeak 3.9|
|4G Systems oneTab|Rockchip RK2818 650 Mhz |     2.1            |14,611,872        |725,287       |Stack Cog α|Squeak 3.9|
|Motorola Xoom    |Tegra 2 1 Ghz |     3.0            |40,000,000        |2,200,000     |Stack Cog α|Pharo     |
|Toshiba Thrive   |Tegra 2 1 Ghz |     3.0            |39,555,006        |2,150,588     |Stack Cog α|Pharo 1.3 |
|KVM on AMD64 host|QEMU Virtual CPU version 0.9.1, 1986 MHz, 3973 bogomips |     2.3 (x86)      |85,906,040        |5,183,202 <sup>f</sup>  |Stack Cog α|Squeak 3.9|
|Acer Aconia a500 |Tegra 2 1 Ghz |     3.0            |41,000,000        |2,000,000     |Stack Cog β|Pharo 1.3 |
|Pandigital Novel eReader 9"|S3C6410 800Mhz|     2.0            |20,012,507        |574,236 <sup>g</sup>   |Stack Cog β|Pharo 1.4 |





---

<sup>a</sup> approximately, as reported by the [parent project Wiki](http://code.google.com/p/squeak-android-vm/)

<sup>b</sup> as reported at http://forum.world.st/Squeak-on-Android-td3159980.html

<sup>c</sup> as reported by Phil, see http://lists.squeakfoundation.org/pipermail/vm-dev/2011-June/008556.html

<sup>d</sup> as of 08/12/2011 06:30 AM EST: Stack Cog was first time run successfully on an Android tablet; sources and configuration are in proper order in VMMaker (not in the main repo yet), and platform sources compile with NDKr6 (the newest Android NDK, sources are also in a separate Gitorious branch). There is still a lot of debug print calls in the platform sources which may reduce VM performance. This benchmark was obtained on the older Squeak image (same as the Classic VM uses - run twice - results are so different likely due to the poor timekeeping). Pharo Core image also loads and seems to work, but yellow click did not bring up the context menu, so "print it" could not be run (may need extra work as Pharo treats mouse buttons slightly differently). Network sockets still do not work.

<sup>e</sup> the first public alpha of Stack Cog was released on 09/03/2011.

<sup>f</sup> on 09/28/2011, the Android port of Cog VM was successfully compiled and run on a KVM virtual machine on an AMD64 host. Benchmark shows some proportionality with 1 GHz ARM e. g. NVidia Tegra on Notion Ink Adam: twice as fast CPU yields roughly two times speedup.

<sup>g</sup> PharoDroid build as of 07/29/2012