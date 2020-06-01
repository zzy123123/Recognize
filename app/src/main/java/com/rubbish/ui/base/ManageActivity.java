package com.rubbish.ui.base;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.rubbish.utils.ScreenManager;

public class ManageActivity extends Activity {
	private final String TAG = this.getClass().getSimpleName();

    @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		ScreenManager.getScreenManager().popActivity(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.e("Activity", this.getClass().getSimpleName());
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		ScreenManager.getScreenManager().pushActivity(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	protected void initView(){
		
	}

}
