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


	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
	final SqueakActivity ctx = this;
    	toastMsg("onCreate");
	imgl = new SqueakImgList(this);
	imgl.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, new String[]{"aa", "bb"}));
	setContentView(imgl);
	imgl.setFocusable(true);
	imgl.requestFocus();
	imgl.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    ctx.toastMsg(((TextView) view).getText().toString());
            }
	});
//	startVM(savedInstanceState);
    }

    public void startVM(Bundle savedInstanceState) {

    	/* stupid setup dance but I'm not sure who is going to need what here */
    	vm = new SqueakVM();
    	vm.context = this;
	vm.setLogLevel(5);
    	view = new SqueakView(this);
    	view.vm = vm;
    	vm.view = view;
	if(canspeak) vm.mTts = mTts;
	String imgpath = "android.image";
    	vm.loadImage(imgpath, 16*1024*1024);
//    	super.onCreate(savedInstanceState);
        setContentView(view);
        /* Let's see if we can display the soft input */
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
