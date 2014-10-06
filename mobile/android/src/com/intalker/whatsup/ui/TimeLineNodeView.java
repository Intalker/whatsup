package com.intalker.whatsup.ui;

import com.intalker.whatsup.util.DensityAdaptor;
import com.intalker.whatsup.widget.SpecTextView;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class TimeLineNodeView extends RelativeLayout
{
	private SpecTextView mLeftTextView = null;
	private SpecTextView mRightTextView = null;
	private ImageView mIconView = null;
	
	private int mIconResId = -1;
	private String mContentText = "";

	public TimeLineNodeView(Context context, int iconResId, String contentText)
	{
		super(context);
		mIconResId = iconResId;
		mContentText = contentText;
		createUI(context);
	}
	
	public void setShowSide(boolean atLeft)
	{
		if (atLeft)
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
		mIconView = new ImageView(context);
		mIconView.setId(100);
		mIconView.setImageResource(mIconResId);
		int size = DensityAdaptor.getDensityIndependentValue(48);
		RelativeLayout.LayoutParams iconLP = new RelativeLayout.LayoutParams(
				size, size);
		iconLP.addRule(RelativeLayout.CENTER_IN_PARENT);
		this.addView(mIconView, iconLP);

		int textColor = Color.BLACK;//ColorUtil.generateRandomColor();
		mLeftTextView = new SpecTextView(context);
		mLeftTextView.setTextColor(textColor);
		mLeftTextView.setTextSize(18f);
		mLeftTextView.setGravity(Gravity.RIGHT);
		mLeftTextView.setText(mContentText);
		RelativeLayout.LayoutParams leftTextLP = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		leftTextLP.addRule(RelativeLayout.LEFT_OF, 100);
		leftTextLP.addRule(RelativeLayout.CENTER_VERTICAL);
		this.addView(mLeftTextView, leftTextLP);

		mRightTextView = new SpecTextView(context);
		mRightTextView.setTextColor(textColor);
		mRightTextView.setTextSize(18f);
		mRightTextView.setText(mContentText);
		RelativeLayout.LayoutParams rightTextLP = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		rightTextLP.addRule(RelativeLayout.RIGHT_OF, 100);
		rightTextLP.addRule(RelativeLayout.CENTER_VERTICAL);
		this.addView(mRightTextView, rightTextLP);

		LinearLayout.LayoutParams nodeLP = new LinearLayout.LayoutParams(
				DensityAdaptor.getDensityIndependentValue(200),
				DensityAdaptor.getDensityIndependentValue(48));
		nodeLP.topMargin = nodeLP.bottomMargin = DensityAdaptor
				.getDensityIndependentValue(32);
		nodeLP.leftMargin = nodeLP.rightMargin = DensityAdaptor.getDensityIndependentValue(32);
		nodeLP.gravity = Gravity.CENTER_HORIZONTAL;
		this.setLayoutParams(nodeLP);
	}
}
