package com.intalker.whatsup.ui;

import com.intalker.whatsup.R;
import com.intalker.whatsup.util.DensityAdaptor;
import com.intalker.whatsup.widget.SpecTextView;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class TimeLineNodeView extends LinearLayout
{
	private RelativeLayout mMainContentLayout = null;
	private SpecTextView mLeftTextView = null;
	private SpecTextView mRightTextView = null;
	private ImageView mIconView = null;
	
	private SpecTextView mDetailTextView = null;

	private int mIconResId = -1;
	private String mContentText = "";
	
	private boolean mDisplayOnLeft = false;

	public TimeLineNodeView(Context context, int iconResId, String contentText)
	{
		super(context);
		mIconResId = iconResId;
		mContentText = contentText;
		createUI(context);
		addListeners();
	}
	
	public void setDisplaySide(boolean onLeft)
	{
		mDisplayOnLeft = onLeft;
		if (mDisplayOnLeft)
		{
			mRightTextView.setVisibility(View.INVISIBLE);
		}
		else
		{
			mLeftTextView.setVisibility(View.INVISIBLE);
		}
	}

	private void createUI(Context context)
	{
		this.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout.LayoutParams nodeLP = new LinearLayout.LayoutParams(
				DensityAdaptor.getDensityIndependentValue(300),
				//LinearLayout.LayoutParams.WRAP_CONTENT,
				//DeviceUtility.getScreenWidth(context) * 2 / 5,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		nodeLP.topMargin = nodeLP.bottomMargin = DensityAdaptor
				.getDensityIndependentValue(32);
		//nodeLP.leftMargin = nodeLP.rightMargin = DensityAdaptor.getDensityIndependentValue(32);
		nodeLP.gravity = Gravity.CENTER_HORIZONTAL;
		this.setLayoutParams(nodeLP);
		
		mMainContentLayout = new RelativeLayout(context);
		LinearLayout.LayoutParams mainContentLP = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		this.addView(mMainContentLayout, mainContentLP);
		
		mIconView = new ImageView(context);
		mIconView.setId(100);
		mIconView.setImageResource(mIconResId);
		int size = DensityAdaptor.getDensityIndependentValue(32);
		RelativeLayout.LayoutParams iconLP = new RelativeLayout.LayoutParams(
				size, size);
		iconLP.addRule(RelativeLayout.CENTER_IN_PARENT);
		mMainContentLayout.addView(mIconView, iconLP);

		int textColor = Color.BLACK;
		mLeftTextView = new SpecTextView(context);
		mLeftTextView.setMaxLines(1);
		mLeftTextView.setEllipsize(TruncateAt.END);
		mLeftTextView.setTextColor(textColor);
		mLeftTextView.setTextSize(18f);
		mLeftTextView.setGravity(Gravity.RIGHT);
		mLeftTextView.setText(mContentText);
		RelativeLayout.LayoutParams leftTextLP = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		leftTextLP.rightMargin = DensityAdaptor.getDensityIndependentValue(16);
		leftTextLP.addRule(RelativeLayout.LEFT_OF, 100);
		leftTextLP.addRule(RelativeLayout.CENTER_VERTICAL);
		mMainContentLayout.addView(mLeftTextView, leftTextLP);

		mRightTextView = new SpecTextView(context);
		mRightTextView.setMaxLines(1);
		mRightTextView.setEllipsize(TruncateAt.END);
		mRightTextView.setTextColor(textColor);
		mRightTextView.setTextSize(18f);
		mRightTextView.setText(mContentText);
		RelativeLayout.LayoutParams rightTextLP = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		rightTextLP.leftMargin = DensityAdaptor.getDensityIndependentValue(16);
		rightTextLP.addRule(RelativeLayout.RIGHT_OF, 100);
		rightTextLP.addRule(RelativeLayout.CENTER_VERTICAL);
		mMainContentLayout.addView(mRightTextView, rightTextLP);

		mDetailTextView = new SpecTextView(context);
		mDetailTextView.setTextColor(textColor);
		mDetailTextView.setBackgroundResource(R.drawable.msg_bg);
		mDetailTextView.setTextSize(18f);
		mDetailTextView.setText(mContentText);
		LinearLayout.LayoutParams detailTextLP = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		this.addView(mDetailTextView, detailTextLP);
		mDetailTextView.setVisibility(View.GONE);
	}
	
	private void addListeners()
	{
		this.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (mDetailTextView.getVisibility() == View.GONE)
				{
					mDetailTextView.setVisibility(View.VISIBLE);
					if (mDisplayOnLeft)
					{
						mLeftTextView.setVisibility(View.INVISIBLE);
					}
					else
					{
						mRightTextView.setVisibility(View.INVISIBLE);
					}
				}
				else
				{
					mDetailTextView.setVisibility(View.GONE);
					if (mDisplayOnLeft)
					{
						mLeftTextView.setVisibility(View.VISIBLE);
					}
					else
					{
						mRightTextView.setVisibility(View.VISIBLE);
					}
				}
			}
		});
	}
}
