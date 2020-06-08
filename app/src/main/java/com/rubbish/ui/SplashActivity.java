package com.rubbish.ui;
import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.Window;
import android.view.WindowManager;

import com.rubbish.R;
import com.rubbish.ui.base.ManageActivity;
import com.rubbish.utils.ScreenManager;
import com.rubbish.utils.SettingUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

@ContentView(R.layout.splash_activity)
public class SplashActivity extends ManageActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		x.view().inject(this);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this,MainActivity.class);
				startActivity(intent);
				ScreenManager.getScreenManager().popActivity();

			}
		},1500);
		ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.WRITE_EXTERNAL_STORAGE,
											Manifest.permission.INTERNET , Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.CAMERA ,
											Manifest.permission.ACCESS_COARSE_LOCATION} , 1);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}


}

