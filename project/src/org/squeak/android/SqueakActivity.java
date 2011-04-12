package org.squeak.android;

import android.app.Activity;
import android.os.Bundle;

import org.squeak.android.SqueakVM;
import org.squeak.android.SqueakView;
import org.squeak.android.SqueakImgList;

import android.widget.Toast;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.widget.TextView;
import android.view.Gravity;

import android.speech.tts.TextToSpeech;
import java.util.Locale;

import android.os.Environment;
import android.content.Intent;

import java.io.File;
import java.io.FileFilter;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;

public class SqueakActivity extends Activity implements TextToSpeech.OnInitListener {
	SqueakVM vm;
	SqueakView view;
	SqueakImgList imgl;
	TextToSpeech mTts;
	boolean canspeak = false;
	
    // Implements TextToSpeech.OnInitListener.
    public void onInit(int status) {
        // status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
	    toastMsg("status: " + status);
    	    if (status == TextToSpeech.SUCCESS) {
	    Locale loc = Locale.getDefault();
            // Set preferred language to US english.
            // Note that a language may not be available, and the result will indicate this.
            int result = mTts.setLanguage(loc);
            // Try this someday for some interesting results.
            // int result mTts.setLanguage(Locale.FRANCE);
            if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
               // Lanuage data is missing or the language is not supported.
                toastMsg(loc.toString() + ": Language is not supported.");
	    } else if (result == TextToSpeech.LANG_MISSING_DATA) {
               // Lanuage data is missing or the language is not supported.
                toastMsg(loc.toString() + ": Missing language data.");
            } else {
                // The TTS engine has been successfully initialized.
 		    canspeak = true;
		    if(vm != null) vm.mTts = mTts;
            }
        } else {
            // Initialization failed.
            toastMsg("Could not initialize TextToSpeech.");
        }
    }

	/** Walk along the image search path (colon-separated) and look
	 * for files with extension .image, recursing into directories when needed
	 * Return a list of files found.
	 */

    File[] findImages(String dir) {
	try {
	    File fdir = new File(dir);
	    if(!fdir.isDirectory()) return new File[0];
	    File[] images = fdir.listFiles(new FileFilter() {
		public boolean accept(File f) {
		    return f.getName().endsWith(".image");
		}
	    });
	    File[] subdirs = fdir.listFiles(new FileFilter() {
		public boolean accept(File f) {
		    return f.isDirectory();
		}
	    });
	    ArrayList<String> sdnames = new ArrayList<String>();
	    for(int i = 0; i < subdirs.length; i++) {
		sdnames.add(subdirs[i].getAbsolutePath());
	    }
	    String[] sdstrn = (String[])sdnames.toArray(new String[sdnames.size()]);
	    File[] subfiles = findImageFiles(sdstrn);
	    ArrayList<File>fimages = new ArrayList<File>(Arrays.asList(images));
	    for(int i = 0; i < subfiles.length; i++) {
		fimages.add(subfiles[i]);
	    }
	    return (File[])fimages.toArray(new File[fimages.size()]);
	} catch (Exception e) {
            return new File[0];
	}
    }

    File[] findImageFiles(String[] dirs) {
	ArrayList<File> res = new ArrayList<File>();
	for(int i = 0; i < dirs.length; i++) {
	    File[] imgs = findImages(dirs[i]);
	    ArrayList<File> aimg = new ArrayList<File>(Arrays.asList(imgs));
	    res.addAll(aimg);
	}
	HashSet<File> hs = new HashSet<File>();
	hs.addAll(res);
	return (File[])hs.toArray(new File[hs.size()]);
    }

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
	final SqueakActivity ctx = this;
    	toastMsg("Select an image to load");
	String extdir = Environment.getExternalStorageDirectory().getAbsolutePath();
	String imgdirs = extdir + File.pathSeparator + getText(R.string.imgdirs).toString();
	File[] imgfiles = findImageFiles(imgdirs.split(File.pathSeparator));
	imgl = new SqueakImgList(this);
	imgl.setAdapter(new ArrayAdapter<File>(this, R.layout.list_item, imgfiles));
	setContentView(imgl);
	imgl.setFocusable(true);
	imgl.requestFocus();
	imgl.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    ctx.startVM(((TextView) view).getText().toString());
            }
	});
//	startVM(savedInstanceState);
    }

    public void startVM(String imgpath) {

    	/* stupid setup dance but I'm not sure who is going to need what here */
    	vm = new SqueakVM();
    	vm.context = this;
	vm.setLogLevel(5);
    	view = new SqueakView(this);
    	view.vm = vm;
    	vm.view = view;
	if(canspeak) vm.mTts = mTts;
    	vm.loadImage(imgpath, 16*1024*1024);
        setContentView(view);
        view.setFocusable(true);
	view.setFocusableInTouchMode(true);
        view.requestFocus();
	Intent checkIntent = new Intent();
	checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
	startActivityForResult(checkIntent, 0);
	mTts = new TextToSpeech(this, this);

    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown!
        if (mTts != null) {
            mTts.stop();
            mTts.shutdown();
        }

        super.onDestroy();
    }


    public void toastMsg(String txt) {
	Toast toast=Toast.makeText(this, txt, 2000);
	    toast.setGravity(Gravity.TOP, -30, 50);
	    toast.show();
    }
}
