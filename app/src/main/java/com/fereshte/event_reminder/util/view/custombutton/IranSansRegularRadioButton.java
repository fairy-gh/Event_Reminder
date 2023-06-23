package com.fereshte.event_reminder.util.view.custombutton;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;

import com.google.android.material.radiobutton.MaterialRadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;

public class IranSansRegularRadioButton extends MaterialRadioButton {

    private Context context;
    public IranSansRegularRadioButton(Context context) {
        super(context);
        this.context = context;
    }

    public IranSansRegularRadioButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        createView();
    }

    public IranSansRegularRadioButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        createView();
    }

    private void createView(){
        setGravity(Gravity.START);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "iransans_fa.ttf"));
    }
}
