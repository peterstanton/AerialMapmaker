package com.c_sharpphoto.aerialmapmaker;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import com.dji.sdk.sample.utils.DJIDialog;

import dji.sdk.base.DJIError;

/**
 * Created by dji on 15/12/18.
 */



public class Utils {
    public static final double ONE_METER_OFFSET = 0.00000899322;
    public static final double ONE_FOOT_OFFSET = 0.00000274113;


    public static double Radian(double x){
        return  x * Math.PI / 180.0;
    }

    public static double Degree(double x){
        return  x * 180 / Math.PI ;
    }

    public static double cosForDegree(double degree) {
        return Math.cos(degree * Math.PI / 180.0f);
    }

    public static double calcLongitudeOffset(double latitude) {
        return ONE_METER_OFFSET / cosForDegree(latitude);
    }

    public static double calcLongitudeFeetOffset(double latitude) {
        return ONE_FOOT_OFFSET / cosForDegree(latitude);
    }


}
