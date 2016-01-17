package com.baidou.myswitch;

import android.app.Activity;
import android.widget.Toast;


public class ToastUtil {
	private static Toast toast;

	public static void showToast(Activity activity,String text) {
		if (toast == null) {
			toast=Toast.makeText(activity, text,
					Toast.LENGTH_SHORT);
		} else {
			toast.setText(text);
		}
		toast.show();
	}
}
