package com.intalker.whatsup.ui;

import com.intalker.whatsup.R;
import com.intalker.whatsup.util.ColorUtil;
import com.intalker.whatsup.util.DensityAdaptor;
import com.intalker.whatsup.widget.SpecTextView;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class TimeLineNodeView extends LinearLayout
{
	public final static boolean mHasTray = false;
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
		} else
		{
			mLeftTextView.setVisibility(View.INVISIBLE);
		}
	}

	private void createUI(Context context)
	{
//		this.setBackgroundColor(ColorUtil.generateRandomColor());
		this.setOrientation(LinearLayout.VERTICAL);

		int horiMargin = 0;
		if (!mHasTray)
		{
			horiMargin = DensityAdaptor.getDensityIndependentValue(16);
		}

		LinearLayout.LayoutParams nodeLP = new LinearLayout.LayoutParams(
				DensityAdaptor.getDensityIndependentValue(300),
				// LinearLayout.LayoutParams.WRAP_CONTENT,
				// DeviceUtility.getScreenWidth(context) * 2 / 5,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		nodeLP.topMargin = nodeLP.bottomMargin = DensityAdaptor
				.getDensityIndependentValue(32);
		// nodeLP.leftMargin = nodeLP.rightMargin =
		// DensityAdaptor.getDensityIndependentValue(32);
		nodeLP.gravity = Gravity.CENTER_HORIZONTAL;
		this.setLayoutParams(nodeLP);

		mMainContentLayout = new RelativeLayout(context);
		LinearLayout.LayoutParams mainContentLP = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		this.addView(mMainContentLayout, mainContentLP);

		if (mHasTray)
		{
			int iconTraySize = DensityAdaptor.getDensityIndependentValue(64);
			FrameLayout iconTrayView = new FrameLayout(context);
			iconTrayView.setBackgroundResource(R.drawable.round_bg);
			RelativeLayout.LayoutParams iconTrayLP = new RelativeLayout.LayoutParams(
					iconTraySize, iconTraySize);
			iconTrayLP.addRule(RelativeLayout.CENTER_IN_PARENT);
			mMainContentLayout.addView(iconTrayView, iconTrayLP);

			mIconView = new ImageView(context);
			mIconView.setId(100);
			mIconView.setImageResource(mIconResId);
			int iconSize = DensityAdaptor.getDensityIndependentValue(32);
			FrameLayout.LayoutParams iconLP = new FrameLayout.LayoutParams(
					iconSize, iconSize);
			iconLP.gravity = Gravity.CENTER;
			iconTrayView.addView(mIconView, iconLP);
		} else
		{
			mIconView = new ImageView(context);
			mIconView.setId(100);
			mIconView.setImageResource(mIconResId);
			int size = DensityAdaptor.getDensityIndependentValue(48);
			RelativeLayout.LayoutParams iconLP = new RelativeLayout.LayoutParams(
					size, size);
			iconLP.addRule(RelativeLayout.CENTER_IN_PARENT);
			mMainContentLayout.addView(mIconView, iconLP);
		}

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
		leftTextLP.rightMargin = horiMargin;
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
		rightTextLP.leftMargin = horiMargin;
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
//		detailTextLP.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
		this.addView(mDetailTextView, detailTextLP);
		mDetailTextView.setScaleX(0);
		mDetailTextView.setScaleY(0);
		mDetailTextView.setVisibility(View.GONE);
	}

	private boolean mDetailInfoDisplayed = false;

	private void addListeners()
	{
		this.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// if (mDetailTextView.getVisibility() == View.GONE)
				if (!mDetailInfoDisplayed)
				{
					// mDetailTextView.setVisibility(View.VISIBLE);
					updateDetailTextViewWithAnimation(1);
//					if (mDisplayOnLeft)
//					{
//						mLeftTextView.setVisibility(View.INVISIBLE);
//					} else
//					{
//						mRightTextView.setVisibility(View.INVISIBLE);
//					}
				} else
				{
					// mDetailTextView.setVisibility(View.GONE);
					updateDetailTextViewWithAnimation(0);
//					if (mDisplayOnLeft)
//					{
//						mLeftTextView.setVisibility(View.VISIBLE);
//					} else
//					{
//						mRightTextView.setVisibility(View.VISIBLE);
//					}
				}
			}
		});
	}

	private void updateDetailTextViewWithAnimation(float detailMsgToScale)
	{
		//ValueAnimator anim = ValueAnimator.ofInt(mDetailTextView, "translationY", 0f, mDetailTextView.getHeight());
		mDetailTextView.animate().setDuration(500).scaleX(detailMsgToScale).scaleY(detailMsgToScale)
				.translationX(0).translationY(0)
				.setInterpolator(new AccelerateDecelerateInterpolator())
				.setListener(new AnimatorListener()
				{

					@Override
					public void onAnimationStart(Animator animation)
					{
						if (!mDetailInfoDisplayed)
						{
							mDetailTextView.setVisibility(View.VISIBLE);
						}
					}

					@Override
					public void onAnimationEnd(Animator animation)
					{
						if (mDetailInfoDisplayed)
						{
							mDetailTextView.setVisibility(View.GONE);
						}
						mDetailInfoDisplayed = !mDetailInfoDisplayed;
					}

					@Override
					public void onAnimationCancel(Animator animation)
					{
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationRepeat(Animator animation)
					{
						// TODO Auto-generated method stub

					}

				});
		float titleToScale = detailMsgToScale == 0 ? 1f : 0;
		if (mDisplayOnLeft)
		{
			mLeftTextView.animate().setDuration(500).alpha(titleToScale)//.scaleX(titleToScale).scaleY(titleToScale)
			.translationX(0).translationY(0)
			.setInterpolator(new AccelerateDecelerateInterpolator());
		} else
		{
			mRightTextView.animate().setDuration(500).alpha(titleToScale)//.scaleX(titleToScale).scaleY(titleToScale)
			.translationX(0).translationY(0)
			.setInterpolator(new AccelerateDecelerateInterpolator());
		}
	}
}
