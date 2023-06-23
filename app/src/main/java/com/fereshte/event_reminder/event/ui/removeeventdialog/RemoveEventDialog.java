package com.fereshte.event_reminder.event.ui.removeeventdialog;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fereshte.event_reminder.R;
import com.fereshte.event_reminder.databinding.DialogRemoveEventBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class RemoveEventDialog extends DialogFragment implements View.OnClickListener {

    public static final String TAG = "RemoveEventDialog";
    private DialogRemoveEventBinding removeEventBinding;

    private final onRemoveDialogClickedListener listener;

    public RemoveEventDialog(onRemoveDialogClickedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        removeEventBinding = DialogRemoveEventBinding.inflate(inflater);
        removeEventBinding.confirmDeleteBtn.setOnClickListener(this);
        removeEventBinding.cancelDeleteBtn.setOnClickListener(this);
        return removeEventBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        setDialogView(requireDialog());
    }

    private void setDialogView(Dialog dialog) {
        Display display = requireActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dialog.getWindow().setLayout((int) ((size.x) * ((double) 4 / 5)), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.confirmDeleteBtn){
            removeEventBinding.confirmDeleteBtn.setTextColor(getContext().getResources().getColor(R.color.turquoise));
            listener.onButtonClicked(true);
        }
        else if(v.getId() == R.id.cancelDeleteBtn){
            listener.onButtonClicked(false);
            removeEventBinding.cancelDeleteBtn.setTextColor(getContext().getResources().getColor(R.color.turquoise));
            dismiss();
        }
    }

    public interface onRemoveDialogClickedListener{
        void onButtonClicked(Boolean clicked);
    }
}
