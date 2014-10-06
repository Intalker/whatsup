package com.intalker.whatsup.util;

import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class DeviceUtility
{

	private static int sScreenLayout = Integer.MIN_VALUE;
	public final static int SMALL_MEMORY_CLASS = 0;
	public final static int MEDIUM_MEMORY_CLASS = 1;
	public final static int LARGE_MEMORY_CLASS = 2;

	private final static int[] sAllMemoryClasses = { 16, 24, 32, 48 };
	private static int[] sMemoryClasses;
	private static final float sRatio = 0.25f;
	private static final int sNormalHeapVersion = 7;
	private static final int sLargeHeapVersion = 9;
	private static final int sExtraLargeHeapVersion = 11;
	private static int sScreenWidth = -1;
	private static int sScreenHeight = -1;

	public static int getCurrentScreenWidth(Context ctx)
	{
		Display display = ((WindowManager) ctx
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();

		display.getMetrics(dm);

		return dm.widthPixels;
	}

	public static int getCurrentScreenHeight(Context ctx)
	{
		Display display = ((WindowManager) ctx
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();

		display.getMetrics(dm);

		return dm.heightPixels;
	}

	public static boolean isLargeDevice(Context context)
	{
		if (sScreenLayout == Integer.MIN_VALUE)
		{
			sScreenLayout = context.getResources().getConfiguration().screenLayout
					& Configuration.SCREENLAYOUT_SIZE_MASK;
		}
		if (sScreenLayout == Configuration.SCREENLAYOUT_SIZE_LARGE
				|| sScreenLayout == Configuration.SCREENLAYOUT_SIZE_XLARGE)
		{
			return true;
		}
		return false;
	}

	public static float getMemoryClass(Context context, int memoryClass)
	{
		if (sMemoryClasses == null)
		{
			calculateMemoryClasses(context);
		}

		return sMemoryClasses[memoryClass];
	}

	public static int getOsVersion()
	{
		return android.os.Build.VERSION.SDK_INT;
	}

	public static int getScreenWidth(Context ctx)
	{
		if (sScreenWidth == -1)
			getDeviceScreenInfo(ctx);

		return sScreenWidth;
	}

	public static int getScreenHeight(Context ctx)
	{
		if (sScreenHeight == -1)
			getDeviceScreenInfo(ctx);

		return sScreenHeight;
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private static void getDeviceScreenInfo(Context ctx)
	{
		if (sScreenWidth != -1 && sScreenHeight != -1)
			return;
		WindowManager wm = (WindowManager) ctx
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		if (DeviceUtility.getOsVersion() >= 17)
		{
			Point outSize = new Point();
			display.getRealSize(outSize);
			sScreenWidth = outSize.x > outSize.y ? outSize.x : outSize.y;
			sScreenHeight = outSize.x < outSize.y ? outSize.x : outSize.y;
		} else if (DeviceUtility.getOsVersion() >= 16)
		{

			Point outSmallestSize = new Point();
			Point outLargestSize = new Point();
			display.getCurrentSizeRange(outSmallestSize, outLargestSize);

			sScreenWidth = outLargestSize.x > outLargestSize.y ? outLargestSize.x
					: outLargestSize.y;
			sScreenHeight = outSmallestSize.x > outSmallestSize.y ? outSmallestSize.x
					: outSmallestSize.y;
		} else
		{
			// We need the actual size of device that include system decoration.
			// while the API is not ready until 4.1(API16), we use reflect for
			// an @hide api
			// for older device, this should be safe, for newer device, we use
			// correct API.
			try
			{
				Method mGetRawW = Display.class.getMethod("getRawWidth");
				Method mGetRawH = Display.class.getMethod("getRawHeight");
				sScreenWidth = (Integer) mGetRawW.invoke(display);
				sScreenHeight = (Integer) mGetRawH.invoke(display);
			} catch (Exception e)
			{
				sScreenHeight = display.getHeight();
				sScreenWidth = display.getWidth();
			}

			if (sScreenWidth < sScreenHeight)
			{
				int temp = sScreenWidth;
				sScreenWidth = sScreenHeight;
				sScreenHeight = temp;
			}
		}

	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static int queryCurrentWindowWidth(Context ctx)
	{
		{

			WindowManager wm = (WindowManager) ctx
					.getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();

			if (DeviceUtility.getOsVersion() >= 17)
			{
				Point outSize = new Point();
				display.getRealSize(outSize);

				return outSize.x;
			} else
			{
				// We need the actual size of device that include system
				// decoration.
				// while the API is not ready until 4.1(API16), we use reflect
				// for an @hide api
				// for older device, this should be safe, for newer device, we
				// use correct API.
				try
				{
					Method mGetRawW = Display.class.getMethod("getRawWidth");
					return (Integer) mGetRawW.invoke(display);
				} catch (Exception e)
				{
					return display.getWidth();
				}

			}

		}
	}

	private static void calculateMemoryClasses(Context context)
	{
		sMemoryClasses = new int[LARGE_MEMORY_CLASS + 1];

		int maxMemory = getMaxMemory(context);
		final int standardMemoryIndex = getStandardMemoryClassIndex();
		if (maxMemory <= 0)
		{
			maxMemory = sAllMemoryClasses[standardMemoryIndex];
		}
		sMemoryClasses[LARGE_MEMORY_CLASS] = maxMemory;

		int j = standardMemoryIndex;
		if (maxMemory <= sAllMemoryClasses[standardMemoryIndex])
		{
			for (j = standardMemoryIndex - 1; j >= 0; j--)
			{
				if (maxMemory > sAllMemoryClasses[j])
				{
					break;
				}
			}
		}

		int remaining = MEDIUM_MEMORY_CLASS;
		for (; remaining >= SMALL_MEMORY_CLASS; remaining--)
		{
			if (j >= 0)
			{
				sMemoryClasses[remaining] = sAllMemoryClasses[j];
			} else
			{
				sMemoryClasses[remaining] = (int) Math
						.round(sMemoryClasses[remaining + 1] * sRatio);
			}
			j--;
		}
	}

	@SuppressLint("NewApi")
	private static int getMaxMemory(Context context)
	{
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Activity.ACTIVITY_SERVICE);

		int maxSizeInMb = 0;

		if (activityManager != null)
		{
			maxSizeInMb = activityManager.getMemoryClass();
			if (android.os.Build.VERSION.SDK_INT > 10)
			{
				try
				{
					maxSizeInMb = activityManager.getLargeMemoryClass();
				} catch (NoSuchMethodError e)
				{
					e.printStackTrace();
				}
			}
			float maxRuntimeMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;
			if (maxSizeInMb > maxRuntimeMemory)
			{
				maxSizeInMb = (int) maxRuntimeMemory;
			}
		}

		return maxSizeInMb;
	}

	private static int getStandardMemoryClassIndex()
	{
		final int osVersion = android.os.Build.VERSION.SDK_INT;

		if (osVersion >= sNormalHeapVersion && osVersion < sLargeHeapVersion)
		{
			return 1;
		} else if (osVersion >= sLargeHeapVersion
				&& osVersion < sExtraLargeHeapVersion)
		{
			return 2;
		} else if (osVersion >= sExtraLargeHeapVersion)
		{
			return 3;
		} else
		{
			return 0;
		}
	}
}