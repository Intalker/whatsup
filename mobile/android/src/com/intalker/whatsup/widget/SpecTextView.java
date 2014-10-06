package com.intalker.whatsup.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

public class SpecTextView extends TextView
{

	public SpecTextView(Context context)
	{
		super(context);
		setStyle();
	}

	public SpecTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setStyle();
	}

	public SpecTextView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		setStyle();
	}

	private void setStyle()
	{
		setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
	}

	@Override
	public void setTextSize(float size)
	{
		super.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
	}
}
