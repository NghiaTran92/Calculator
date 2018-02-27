package com.nghiatt.calculator.utils;

/**
 * Created by ngh on 9/6/2015.
 */
public class RoundUtils {
        public static String round(double value){
            float t=(float)Math.abs(value)-Math.abs((int)value);
            if(t==0){
                return (int)value+"";
            }else{
                return value+"";
            }

        }
}
