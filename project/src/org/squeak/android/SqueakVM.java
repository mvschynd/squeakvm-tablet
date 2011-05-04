package org.squeak.android;

import android.content.res.AssetManager;
import java.lang.Exception;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;

import org.squeak.android.SqueakActivity;
import org.squeak.android.SqueakView;

import android.os.Environment;
import android.content.Context;
import android.speech.tts.TextToSpeech;

public class SqueakVM {
	SqueakActivity context;
	SqueakView view;
	File imageDir;
	TextToSpeech mTts = null;
	float pitch = 1.0f;
	float rate = 1.0f;

/* Store the desired speech rate value */

    int setSpeechRate(float r) {
	if(mTts == null) return TextToSpeech.ERROR;
	rate = r;
	return TextToSpeech.SUCCESS;
    }

/* Store the desired pitch value */

    int setPitch(float p) {
	if(mTts == null) return TextToSpeech.ERROR;
	pitch = p;
	return TextToSpeech.SUCCESS;
    }

/* Stop the speech */

    int stop() {
	if(mTts == null) return TextToSpeech.ERROR;
	return mTts.stop();
    }


/* Speak a given string if text */

    int speak(String txt) {
	if(mTts == null) return -1;
	context.toastMsg("speaking: " + txt);
	mTts.setPitch(pitch);
	mTts.setSpeechRate(rate);
 	mTts.speak(txt, TextToSpeech.QUEUE_ADD, null);
	mTts.speak("", TextToSpeech.QUEUE_ADD, null);
	return txt.length();
    }

/* Try to load the given image from sdcard. Returns the heap size allocated */

    int loadImageFromSdCard(String imageName) throws Exception {
       	context.toastMsg("Loading image file (full) from sdcard");
	String imgpath = imageName;
	File imgfile = new File(imgpath);
	long fsize = imgfile.length();
	context.toastMsg("image found size: " + fsize);
	int usedheap = ((int)fsize + 8 * 1024 * 1024) & (~1);
	InputStream fstr = new FileInputStream(imgfile);
    	byte buf[] = new byte[4096];
    	int ofs, len;
    	ofs = 0;
	allocate(usedheap);
	while((len = fstr.read(buf, 0, 4096)) > 0) {
    		loadMemRegion(buf, ofs, len);
    		ofs += len;
    	}
	fstr.close();
	setImagePath(imgpath);
	imageDir = new File(imgpath).getParentFile();
	context.setTitle("Squeak: " + imgpath);
	return usedheap;
    }

/* Try to load the given image from assets as a single file. Returns the heap size allocated */

    int loadImageFullFromAssets(String imageName, int heap) throws Exception {
    	AssetManager assets = context.getAssets();
    	byte buf[] = new byte[4096];
    	int ofs, len;
	allocate(heap);
       	context.toastMsg("Loading image file (full) from assets");
   	InputStream image = assets.open(imageName, 2 /*ACCESS_STREAMING*/);
    	ofs = 0;
    	while((len = image.read(buf, 0, 4096)) > 0) {
    		loadMemRegion(buf, ofs, len);
    		ofs += len;
    	}
	image.close();
	context.setTitle("Squeak: assets://" + imageName);
	return heap;
    }

/* Try to load the given image from assets piece by piece. Returns the heap size allocated. */

    int loadImageSegmentsFromAssets(String imageName, int heap) throws Exception {
    	AssetManager assets = context.getAssets();
    	byte buf[] = new byte[4096];
    	int ofs, len;
	allocate(heap);
	try {
    		int part = 1;
	       	context.toastMsg("Loading image file (segments) from assets");
    		ofs = 0;
	       	while(true) {
       			String partName = imageName + "." + part;
          		System.out.println(partName);
	       		InputStream image = assets.open(partName, 2);
       			while((len = image.read(buf, 0, 4096)) > 0) {
       				loadMemRegion(buf, ofs, len);
       				ofs += len;
	       		}
       			image.close();
       			part++;
       		}
	} catch (Exception e) {
		context.setTitle("Squeak: assets://" + imageName);
		return heap;
	}
     }

    public void loadImage(String imageName, int heap) {
	    int heapsz = 0;
	    try {
		    heapsz = loadImageFromSdCard(imageName);
	    } catch (Exception e1) {
		    try {
			    heapsz = loadImageFullFromAssets(imageName, heap);
		    } catch (Exception e2) {
			    try {
				    heapsz = loadImageSegmentsFromAssets(imageName, heap);
			    } catch (Exception e3) {
			    }
		    }
	    }
	    if (heapsz == 0) {
		    context.toastMsg("Failed to load image " + imageName);
	    } else {
    		int rc = loadImageHeap(imageName, heapsz);
		if (rc == 0) {
    		    interpret();
		} else {
		    context.toastMsg("Failed to load image " + imageName);
		}
	    }
    }

   
    /* VM callbacks */
    public void invalidate(int left, int top, int right, int bottom) {
    	/* System.out.println("Invalidating: (" + left + "," + top + " -- " + right + "," + bottom + ")"); */
    	view.invalidate(left, top, right, bottom);
    }

    /* PRELOAD functions */
    public native int allocate(int heap);
    public native int loadMemRegion(byte[] buf, int ofs, int len);
    public native int setLogLevel(int logLevel);

    /* Main entry points */
    public native int setScreenSize(int w, int h);
    public native int loadImageHeap(String imageName, int heap);
    public native int setImagePath(String imageName);
    public native int sendEvent(int type, int stamp, int arg3, int arg4,
				int arg5, int arg6, int arg7, int arg8);
    public native int updateDisplay(int bits[], int w, int h, int d, int l, int t, int r, int b);
    public native int interpret();

    /* Load the SqueakVM module */
    static {
    	System.out.println("Loading squeakvm shared library");
        System.loadLibrary("squeakvm");
    }
}
