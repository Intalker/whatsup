package com.intalker.whatsup.ui;

import com.intalker.whatsup.R;
import com.intalker.whatsup.util.DensityAdaptor;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class PortraitTimeLineView extends FrameLayout
{
	private ImageView mVerticalBar = null;
	private ScrollView mScrollView = null;
	private LinearLayout mNodeLinearLayout = null;

	public PortraitTimeLineView(Context context)
	{
		super(context);
		createUI(context);
	}

	private void createUI(Context context)
	{
		mVerticalBar = new ImageView(context);
		mVerticalBar.setBackgroundColor(Color.GRAY);
		FrameLayout.LayoutParams verticalBarLP = new FrameLayout.LayoutParams(
				DensityAdaptor.getDensityIndependentValue(1),
				FrameLayout.LayoutParams.MATCH_PARENT);
		verticalBarLP.gravity = Gravity.CENTER_HORIZONTAL;
		this.addView(mVerticalBar, verticalBarLP);
		mScrollView = new ScrollView(context);
		mScrollView.setFillViewport(true);
		this.addView(mScrollView);

		mNodeLinearLayout = new LinearLayout(context);
		mNodeLinearLayout.setOrientation(LinearLayout.VERTICAL);
		mScrollView.addView(mNodeLinearLayout);

		addTestNodes(context);
	}

	private void addTestNodes(Context context)
	{
		for (int i = 0; i < 100; ++i)
		{
			int iconResId = -1;
			switch (i % 6)
			{
				case 0:
					iconResId = R.drawable.cherry;
					break;
				case 1:
					iconResId = R.drawable.orange;
					break;
				case 2:
					iconResId = R.drawable.watermelon;
					break;
				case 3:
					iconResId = R.drawable.tomato;
					break;
				case 4:
					iconResId = R.drawable.kiwi;
					break;
				case 5:
					iconResId = R.drawable.grape;
					break;
				default:
					break;
			}
			TimeLineNodeView nodeView = new TimeLineNodeView(
					context,
					iconResId,
					"Event No. "
							+ (i + 1)
							+ "\nTest text Test text Test text Test text Test text Test text Test text ");
			nodeView.setDisplaySide(i % 2 == 0);
			mNodeLinearLayout.addView(nodeView, 0);
		}
	}
}
