package com.fereshte.event_reminder.util.view.customTextView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class IconTextView extends AppCompatTextView {

    private final Context context;

    public IconTextView(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        createView();
    }

    public IconTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        createView();
    }

    private void createView(){
        setGravity(Gravity.CENTER);
        setPadding(1,1,1,1);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fontawesome_webfont.ttf"));
    }
}
