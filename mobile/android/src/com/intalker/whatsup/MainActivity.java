package com.intalker.whatsup;

import com.intalker.whatsup.ui.MainContent;
import com.intalker.whatsup.util.DensityAdaptor;
import com.intalker.whatsup.util.DeviceUtility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity
{
	private MainContent mMainContent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setWindowStyle();
		createUI();
	}

	private void createUI()
	{
		mMainContent = new MainContent(this);
		this.setContentView(mMainContent);
	}

	@SuppressLint("InlinedApi")
	private void setWindowStyle()
	{
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		if (DeviceUtility.getOsVersion() > 13)
		{
			// Android 3.x will not enable HA.
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
					WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
		}
		DensityAdaptor.init(this);
	}
}
