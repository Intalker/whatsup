package com.intalker.whatsup.ui;

import com.intalker.whatsup.R;

import android.content.Context;
import android.widget.FrameLayout;

public class MainContent extends FrameLayout
{
	private PortraitTimeLineView mTimeLineView = null;
	public MainContent(Context context)
	{
		super(context);
		createUI(context);
	}
	
	private void createUI(Context context)
	{
		this.setBackgroundResource(R.drawable.main_bg);
		mTimeLineView = new PortraitTimeLineView(context);
		this.addView(mTimeLineView);
	}
}
