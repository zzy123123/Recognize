package com.rubbish.ui;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

