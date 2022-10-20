package com.zitian.csims.util;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;

public class ToastUtil {
    private static Toast toast;
    private static Toast longToast;

    public static void show(Context context, CharSequence text) {
        if (toast == null) {
            //toast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
            toast = Toast.makeText(context,text,Toast.LENGTH_LONG);
        }
        toast.setText(text);
        toast.show();
    }

    public static void show(Context context, @StringRes int textRes) {
        if (toast == null) {
            //toast = Toast.makeText(context,textRes,Toast.LENGTH_SHORT);
            toast = Toast.makeText(context,textRes,Toast.LENGTH_LONG);
        }
        toast.setText(textRes);
        toast.show();
    }

    public static void showLong(Context context, CharSequence text) {
        if (longToast == null) {
            longToast = Toast.makeText(context,text,Toast.LENGTH_LONG);
        }
        longToast.setText(text);
        longToast.show();
    }

    public static void showLong(Context context, @StringRes int textRes) {
        if (longToast == null) {
            longToast = Toast.makeText(context,textRes,Toast.LENGTH_LONG);
        }
        longToast.setText(textRes);
        longToast.show();
    }
}
