package org.squeak.android;

import android.content.res.AssetManager;
import java.lang.Exception;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;

import org.squeak.android.SqueakActivity;
import org.squeak.android.SqueakView;

public class SqueakVM {
	SqueakActivity context;
	SqueakView view;

/* Try to load the given image from sdcard. Returns the heap size allocated */

    int loadImageFromSdCard(String imageName) throws Exception {
       	context.toastMsg("Loading image file (full) from sdcard");
	File imgfile = new File("/system/media/sdcard/" + imageName);
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
    		loadImageHeap(imageName, heapsz);
    		interpret();
	    }
    }

    public void loadImageOld(String imageName, int heap) {
	int usedheap = heap;
    	context.toastMsg("Loading image: " + imageName);
		/* Try loading from a file on sdcard */
	try {
        	context.toastMsg("Loading image file (full) from sdcard");
		File imgfile = new File("/system/media/sdcard/" + imageName);
		long fsize = imgfile.length();
		context.toastMsg("image found size: " + fsize);
		usedheap = ((int)fsize + 8 * 1024 * 1024) & (~1);
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
    		System.out.println("Loading image from heap");
    		loadImageHeap(imageName, usedheap);
    		System.out.println("Calling interpret()");
    		interpret();
    		System.out.println("Finished");
		return;
	} catch(Exception e) {
    		context.toastMsg(e.toString());
    		context.toastMsg("Failed to read image file from sdcard");
    	}
    	AssetManager assets = context.getAssets();
    	byte buf[] = new byte[4096];
    	int ofs, len;

		allocate(heap);

		/* Try loading from a single image file */
    	try {
        	context.toastMsg("Loading image file (full) from assets");
    		InputStream image = assets.open(imageName, 2 /*ACCESS_STREAMING*/);
    		ofs = 0;
    		while((len = image.read(buf, 0, 4096)) > 0) {
    			loadMemRegion(buf, ofs, len);
    			ofs += len;
    		}
    	} catch(Exception e) {
    		context.toastMsg(e.toString());
    		context.toastMsg("Failed to read full image file from assets");
    	}

    	/* Try loading from segments squeak.image.1 ... squeak.image.N */
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
    	} catch(Exception e) {
    		System.out.println(e);
    	}
    	System.out.println("Loading image from heap");
    	loadImageHeap(imageName, heap);
    	System.out.println("Calling interpret()");
    	interpret();
    	System.out.println("Finished");
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
    public native int loadImageHeap(String imageName, int heap);
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
