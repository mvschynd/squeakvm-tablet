package org.squeak.android;

import android.app.Activity;
import android.os.Bundle;

import org.squeak.android.SqueakVM;
import org.squeak.android.SqueakView;

import android.widget.Toast;
import android.view.Gravity;

import android.os.Environment;

import java.io.File;

public class SqueakActivity extends Activity {
	SqueakVM vm;
	SqueakView view;

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
        view.requestFocus();

    }

    public void toastMsg(String txt) {
	Toast toast=Toast.makeText(this, txt, 2000);
	    toast.setGravity(Gravity.TOP, -30, 50);
	    toast.show();
    }
}
