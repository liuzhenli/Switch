package com.baidou.myswitch;

import com.baidou.myswitch.MySwitch.SwitchStateChangedListener;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	private MySwitch mMySwitch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mMySwitch = (MySwitch) findViewById(R.id.ms_myswitch);
		mMySwitch
				.setOnSwichStateChangedListener(new SwitchStateChangedListener() {

					@Override
					public void onStateChanged(View view, boolean isOn) {
						if (isOn) {
							ToastUtil.showToast(MainActivity.this, "打开");
						} else {
							ToastUtil.showToast(MainActivity.this, "关闭");
						}
					}
				});
	}

}
