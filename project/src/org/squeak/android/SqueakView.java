package org.squeak.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.MotionEvent;
import android.view.KeyEvent;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import org.squeak.android.SqueakVM;
import org.squeak.android.SqueakInputConnection;

import java.lang.System;

public class SqueakView extends View {
	SqueakVM vm;
	int bits[];
	int width;
	int height;
	int depth;
	int buttonBits;
	long timestamp = System.nanoTime();
	int lastX = -1, lastY = -1;
	final int redButtonBit = 4;
	final int yellowButtonBit = 2;
	final int blueButtonBit = 1;
	boolean softKbdOn;
	SqueakActivity ctx;
	Paint paint;
	int timerDelay;

	RR rr = new RR(this);

	/* Closure -- ha-ha */

	private class RR extends ResultReceiver {
		SqueakView owner;
		public RR(SqueakView sv) {
			super(getHandler());
			owner = sv;
		}
		protected void  onReceiveResult  (int resultCode, Bundle resultData)
		{
			super.onReceiveResult(resultCode, resultData);
			switch(resultCode) {
				case InputMethodManager.RESULT_HIDDEN:
				case InputMethodManager.RESULT_UNCHANGED_HIDDEN:
					owner.softKbdOn = false;
					break;
				case InputMethodManager.RESULT_SHOWN:
				case InputMethodManager.RESULT_UNCHANGED_SHOWN:
					owner.softKbdOn = true;
					break;
				default:
			}
		}
	}

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
		width = 0;
		height = 0;
		depth = 32;
		softKbdOn = false;
		bits = null;
		buttonBits = redButtonBit;
    	paint = new Paint();
    	timerEvent();
	}

	protected void onLayout(boolean changed, int left, int top, int right, int bottom)
	{
		if(!changed) return;
		this.width = right - left;
		this.height = bottom - top;
		this.bits = new int[this.width * this.height];
	}

	// Key down: show/hide soft keyboard on menu button. Back button turns the mouse
	// yellow for one click. Page up button turns the mouse blue for one click.

	public boolean onKeyDown(int keyCode, KeyEvent event) {
	//	ctx.toastMsg("Key Event: " + event + " " + keyCode);
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
				imm.showSoftInput(this, 0, rr);
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
				int uchar = event.getUnicodeChar();
				if (uchar == 0) return false;
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
		int ex = (int)event.getX();
		int ey = (int)event.getY();
		int dx = ex - lastX;
		int dy = ey - lastY;
		long ts = System.nanoTime();

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
				System.out.println("Unsupported mtn. action: " + event.getAction());
				return false;
		}
		lastX = ex;
		lastY = ey;
		timestamp = ts;
//		ctx.toastMsg(event.toString());
		vm.sendEvent(1 /* EventTypeMouse */, 0 /* timestamp */, 
					ex, ey, 
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
	if (bits == null) return;
    	Rect dirtyRect = new Rect(0,0,0,0);
    	if(canvas.getClipBounds(dirtyRect)) {
    		/* System.out.println("dirtyRect: " + dirtyRect); */
    		vm.updateDisplay(bits, width, height, depth, 
				dirtyRect.left, dirtyRect.top, dirtyRect.right, dirtyRect.bottom);
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
