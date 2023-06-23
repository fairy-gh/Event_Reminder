package com.fereshte.event_reminder.util.view.customradiobutton;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class IranSansRegularButton extends AppCompatButton {

    private Context context;

    public IranSansRegularButton(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public IranSansRegularButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        createView();
    }

    public IranSansRegularButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        createView();
    }

    private void createView(){
        setGravity(Gravity.CENTER);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "iransans_fa.ttf"));
    }
}
