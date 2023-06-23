package com.fereshte.event_reminder.util.view.customEditText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

public class IranSansMediumEditText extends AppCompatEditText {

    Context context;
    public IranSansMediumEditText(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public IranSansMediumEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        createView();
    }

    public IranSansMediumEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        createView();
    }

    private void createView(){
        setGravity(Gravity.START);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "iransans_fa_medium.ttf"));
    }

}
