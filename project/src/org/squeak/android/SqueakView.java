package org.squeak.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.MotionEvent;
import android.view.KeyEvent;

import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import org.squeak.android.SqueakVM;
import org.squeak.android.SqueakInputConnection;

public class SqueakView extends View {
	SqueakVM vm;
	int bits[];
	int width;
	int height;
	int depth;
	int buttonBits;
	final int redButtonBit = 4;
	final int yellowButtonBit = 2;
	final int blueButtonBit = 1;
	boolean softKbdOn;
	SqueakActivity ctx;
	Paint paint;
	int timerDelay;


	public void timerEvent() {
		final class SqueakTimer implements Runnable {
			public void run() {
				timerEvent();
			}
		}
		if(vm != null) vm.interpret();
		postDelayed(new SqueakTimer(), timerDelay);
	}

	public SqueakView(Context context) {
		super(context);
		ctx = (SqueakActivity)context;
		timerDelay = 100;
		width = 800;
		height = 600;
		depth = 32;
		softKbdOn = false;
		bits = new int[800*600];
		buttonBits = redButtonBit;
    	paint = new Paint();
    	timerEvent();
	}

	// Key down: show/hide soft keyboard on menu button. Back button turns the mouse
	// yellow for one click. Page up button turns the mouse blue for one click.

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		ctx.toastMsg("Key Event: " + event + " " + keyCode);
		switch(keyCode) {
			case KeyEvent.KEYCODE_BACK:
				buttonBits = yellowButtonBit;
				ctx.toastMsg("mouse yellow");
				return true;
			case 92: //KeyEvent.KEYCODE_PAGE_UP:
				buttonBits = blueButtonBit;
				ctx.toastMsg("mouse blue");
				return true;
			case KeyEvent.KEYCODE_MENU:
				InputMethodManager imm = (InputMethodManager)
					ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (softKbdOn) { // true: hide
					imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
				} else {	 // false: show
					imm.showSoftInput(this, 0);
				}
				softKbdOn = !softKbdOn;
				return true;
			case KeyEvent.KEYCODE_DEL: // special handling for DEL
				vm.sendEvent(	2 /* EventTypeKeyboard */,
						0 /* timeStamp */,
						8 /* charCode */,
						0 /* EventKeyChar */,
						0 /* modifiers */,
						8 /* utf32Code */,
						0 /* reserved1 */,
						0 /* windowIndex */);
				vm.interpret();
				break;
			default:		 // send key event
				ctx.setTitle("Key Event: " + event + " " + keyCode);
				int uchar = event.getUnicodeChar();
				vm.sendEvent(	2 /* EventTypeKeyboard */,
						0 /* timeStamp */,
						uchar /* charCode */,
						0 /* EventKeyChar */,
						0 /* modifiers */,
						uchar /* utf32Code */,
						0 /* reserved1 */,
						0 /* windowIndex */);
				vm.interpret();
		}
		return true;
	}

	// Touch the screen and possibly move while pressing. Current button bits
	// will be used until the pressure removed, i. e. forward/back button modifiers
	// last exactly for one touch (or click).

	public boolean onTouchEvent(MotionEvent event) {
		int buttons = 0;
		int modifiers = 0;

		switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN: // 0
				buttons = buttonBits;
				break;
			case MotionEvent.ACTION_MOVE: // 2
				buttons = buttonBits;
				break;
			case MotionEvent.ACTION_UP: // 1
				buttons = 0;
				buttonBits = redButtonBit;
				break;
			default:
				System.out.println("Unsupported motion action: " + event.getAction());
				return false;
		}
		vm.sendEvent(1 /* EventTypeMouse */, 0 /* timestamp */, 
					(int)event.getX(), (int)event.getY(), 
					buttons, modifiers, 0, 0);
		vm.interpret();
		return true;
	}

	/**
     * Render me
     * 
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
    	Rect dirtyRect = new Rect(0,0,0,0);
    	if(canvas.getClipBounds(dirtyRect)) {
    		/* System.out.println("dirtyRect: " + dirtyRect); */
    		vm.updateDisplay(bits, width, height, depth, dirtyRect.left, dirtyRect.top, dirtyRect.right, dirtyRect.bottom);
    	}
        super.onDraw(canvas);
        canvas.drawColor(-1);
    	canvas.drawBitmap(bits, 0, width, 0, 0, width, height, false, paint);
    }
    
    /**
     * Text Input handling
     */
    @Override
    public boolean onCheckIsTextEditor() {
    	return true;
    }
    
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
    	if(!onCheckIsTextEditor()) return null;
    	return new SqueakInputConnection(this, false);
    }
    
    public void sendText(CharSequence text) {
		System.out.println("sendText: " + text);
    	for(int index=0; index<text.length(); index++) {
    		vm.sendEvent(2 /* EventTypeKeyboard */, 0 /* timestamp */, 
    			(int)text.charAt(index),
    			0 /* EventKeyChar */,
    			0 /* Modifiers */, 
    			0, 0, 0);
    	}
		vm.interpret();
    }
}
