package org.squeak.android;

import android.app.Activity;
import android.os.Bundle;

import org.squeak.android.SqueakVM;
import org.squeak.android.SqueakView;

import android.widget.Toast;
import android.view.Gravity;

import android.speech.tts.TextToSpeech;
import java.util.Locale;

import android.os.Environment;
import android.content.Intent;

import java.io.File;

public class SqueakActivity extends Activity implements TextToSpeech.OnInitListener {
	SqueakVM vm;
	SqueakView view;
	private TextToSpeech mTts;
	
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
                // Check the documentation for other possible result codes.
                // For example, the language may be available for the locale,
                // but not for the specified country and variant.

                // The TTS engine has been successfully initialized.
                // Allow the user to press the button for the app to speak again.
                // Greet the user.
                int res = mTts.speak("Hello", TextToSpeech.QUEUE_ADD, null);
		toastMsg("spoke: " + res);
            }
        } else {
            // Initialization failed.
            toastMsg("Could not initialize TextToSpeech.");
        }
    }


	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	toastMsg("onCreate");

    	/* stupid setup dance but I'm not sure who is going to need what here */
    	vm = new SqueakVM();
    	vm.context = this;
	vm.setLogLevel(5);
    	view = new SqueakView(this);
    	view.vm = vm;
    	vm.view = view;
	String imgpath = "android.image";
    	vm.loadImage(imgpath, 16*1024*1024);
    	super.onCreate(savedInstanceState);
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
