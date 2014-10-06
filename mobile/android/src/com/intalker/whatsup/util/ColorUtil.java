package com.intalker.whatsup.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

public class ColorUtil {

	public static int HSVToColor(float[] hsv) {
		return Color.HSVToColor(hsv);
	}

	public static void RGBToHSV(int red, int green, int blue, float[] hsv) {
		Color.RGBToHSV(red, green, blue, hsv);
	}

	public static int argb(int alpha, int red, int green, int blue) {
		return Color.argb(alpha, red, green, blue);
	}

	public static int rgb(int red, int green, int blue) {
		return Color.rgb(red, green, blue);
	}

	public static int red(int color) {
		return Color.red(color);
	}

	public static int green(int color) {
		return Color.green(color);
	}

	public static int blue(int color) {
		return Color.blue(color);
	}

 public static int makeColor(int srcColor, int alpha)
 {
	 int clr = Color.argb(alpha, Color.red(srcColor), Color.green(srcColor), Color.blue(srcColor));
	 return clr;
 }

	// r, g, b (0-255)
	// h (0-360), s (0-100), l (0-100)
	public static int HSLToColor(float[] hsl) {

		float h = hsl[0];
		if (h >= 360)
		{
			h %= 360;
		}
		float s = hsl[1];
		float l = hsl[2];

		float r = 0.0f;
		float g = 0.0f;
		float b = 0.0f;

		if (s == 0) {
			l /= 100.0f;
			l *= 255.0;
			r = l;
			g = l;
			b = l;
		} else {
			s /= 100.0f;
			l /= 100.0f;
			// l *= divide255;

			// Fast HSL->RGB from Graphics Gems I -- pp 764
			float v, m, sv, fract, vsf, mid1, mid2;
			int sextant;
			float hue = h * (1.0F / 360.0F);

			if (l <= 0.5F)
				v = l * (1.0F + s);
			else
				v = l + s - l * s;

			if (v <= 0.0F) {
				r = g = b = 0.0F;
			} else {
				m = 2 * l - v;
				sv = (v - m) / v;
				hue *= 6.0F;
				sextant = (int) hue;
				fract = hue - sextant;
				vsf = v * sv * fract;
				mid1 = m + vsf;
				mid2 = v - vsf;

				switch (sextant) {
				case 0:
					r = v;
					g = mid1;
					b = m;
					break;
				case 1:
					r = mid2;
					g = v;
					b = m;
					break;
				case 2:
					r = m;
					g = v;
					b = mid1;
					break;
				case 3:
					r = m;
					g = mid2;
					b = v;
					break;
				case 4:
					r = mid1;
					g = m;
					b = v;
					break;
				case 5:
					r = v;
					g = m;
					b = mid2;
					break;
				}
				r *= 255.0F;
				g *= 255.0F;
				b *= 255.0F;
			}
		}

		return Color.rgb(Math.round(r), Math.round(g), Math.round(b));
	}

	private static float lasth__ = 0.0F;
	
	public static boolean folatEqual(float a, float b)
	{
		return Math.abs(a - b) < 0.0000001f;
	}
	
	public static void RGBToHSL(float r, float g, float b, float[] hsl) {
		// static float lasth__ = 0.0F;

		float h = 0.0F;
		float s = 0.0F;
		float l = 0.0F;

		if (r == 0.0 && g == 0.0 && b == 0.0) {
			s = 50.0F;
			hsl[0] = h;
			hsl[1] = s;
			hsl[2] = l;
			return;
		}

		r *= (1.0F / 255.0F);
		g *= (1.0F / 255.0F);
		b *= (1.0F / 255.0F);

		/* find the cmax and cmin of r g b */
		float cmax = r;
		float cmin = r;
		cmax = (g > cmax ? g : cmax);
		cmin = (g < cmin ? g : cmin);
		cmax = (b > cmax ? b : cmax);
		cmin = (b < cmin ? b : cmin);

		l = (cmax + cmin) / 2.0F;

		if (folatEqual(cmax, cmin)) {
			s = 0.0F; // lasts__;
			h = lasth__; // undefined
			l *= 100.0F;
			
			hsl[0] = h;
			hsl[1] = s;
			hsl[2] = l;
			return;
		}

		// Chromatic Case
		float delta = cmax - cmin;

		if (l <= 0.5F) {
			s = delta / (cmax + cmin);
		} else {
			s = delta / (2.0F - cmax - cmin);
		}

		if (folatEqual(r, cmax))
			h = (g - b) / delta;
		else if (folatEqual(g, cmax))
			h = 2.0F + (b - r) / delta;
		else if (folatEqual(b, cmax))
			h = 4.0F + (r - g) / delta;

		h *= (1.0F / 6.0F);
		if (h < 0.0F)
			h += 1.0F;

		h *= 360.0F;
		lasth__ = h;

		l *= 100.0f;
		s *= 100.0f;
		// l *= 255.0F;

		hsl[0] = h;
		hsl[1] = s;
		hsl[2] = l;
	}
	
	public static int generateRandomColor() {
        int randomColor = Color.argb(0xFF, getRandomNum(), getRandomNum(),
                          getRandomNum());
        return randomColor;
	}
	
	private static int getRandomNum() {
	        return (int) (Math.random() * 0xFF);
	}

    public static Bitmap paintColorToMaskPanel(int color, Bitmap mask)
    {
        int w = mask.getWidth();
        int h = mask.getHeight();
        Bitmap target = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        return paintColorToMaskPanel(color, mask, target);
    }
    
    public static Bitmap paintColorToMaskPanel(int color, Bitmap mask, Bitmap target)
    {
        int w = mask.getWidth();
        int h = mask.getHeight();
        int alpha = Color.alpha(color);
        if (alpha == 0)
        {
            color = 0xffffffff;
        }

        Canvas canvas = new Canvas(target);
        canvas.drawColor(color);
        
        // 2) blend with mask
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(mask, new Rect(0, 0, w, h), new Rect(0, 0, w, h), paint);
        paint.setXfermode(null);
        return target;
    }
    
    public static Bitmap blendToBackground(Bitmap foreground, Bitmap background)
    {
        int w = foreground.getWidth();
        int h = foreground.getHeight();

        Canvas canvas = new Canvas(background);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawBitmap(foreground, new Rect(0, 0, w, h), new Rect(0, 0, w, h), paint);
        paint.setXfermode(null);
        return background;
    }

}