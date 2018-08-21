package com.rg.milkwala.controller.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.SuperToast;
/**
 * Created by Mobile on 1/9/2017.
 */

public class Dialogs {
    private static Dialogs mInstance;
    private SuperToast toast;
    private Toast simpletoast;

    public Dialogs(){
        mInstance=this;
    }
    public static Dialogs getInstance(){
        return mInstance;
    }

    public void showtoast(Context context, String message){
        simpletoast=Toast.makeText(context,message,Toast.LENGTH_SHORT);
        simpletoast.show();
    }
   /* public void specialtaost(Activity activity,String msg){
        try {
            if (toast.isShowing()) {
                toast.dismiss();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        toast = SuperActivityToast.create(activity, new Style(), Style.TYPE_STANDARD)
                .setText(msg)
                .setTextColor(activity.getResources().getColor(R.color.colorPrimary))
                .setDuration(Style.DURATION_SHORT)
                .setFrame(Style.FRAME_STANDARD)
                .setColor(Color.WHITE)
                .setAnimations(Style.ANIMATIONS_POP);
        toast.show();
    }*/
}
