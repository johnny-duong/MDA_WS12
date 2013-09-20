package org.dieschnittstelle.mobile.android.todolist.model;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.Button;

public class ColorHandler {

	public static final int MY_COLOR_BLUE = Color.rgb(0, 0, 255);
	public static final int MY_COLOR_RED = Color.rgb(255, 0, 0);
	public static final int MY_COLOR_GREEN = Color.rgb(0, 255, 0);
	public static final int MY_COLOR_BLACK = Color.rgb(0, 0, 0);
	public static final int MY_COLOR_WHITE = Color.rgb(255, 255, 255);
	public static final int MY_COLOR_ROYALBLUE = Color.rgb(65, 105, 225);
	public static final int MY_COLOR_FIREBRICK = Color.rgb(178, 34, 34);
	public static final int MY_COLOR_SNOW = Color.rgb(205, 201, 201);

	public int getMyColorBlue() {
		return MY_COLOR_BLUE;
	}

	public int getMyColorRed() {
		return MY_COLOR_RED;
	}

	public int getMyColorGreen() {
		return MY_COLOR_GREEN;
	}

	public int getMyColorBlack() {
		return MY_COLOR_BLACK;
	}

	public int getMyColorWhite() {
		return MY_COLOR_WHITE;
	}

	public int getMyColorRoyalblue() {
		return MY_COLOR_ROYALBLUE;
	}

	public int getMyColorFirebrick() {
		return MY_COLOR_FIREBRICK;
	}

	public int getMyColorSnow() {
		return MY_COLOR_SNOW;
	}

	public void setActivityBackground(Activity activity, int color) {
		View activityLayout;
		activityLayout = activity.getWindow().getDecorView();
		activityLayout.setBackgroundColor(color);
	}

	public void setButtonColor(Button btn, int color, int textColor) {
		btn.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
		btn.setTextColor(textColor);
	}

	public void setButtonText(Button btn, String text) {
		btn.setText(text);
	}

}
