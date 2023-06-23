package com.fereshte.event_reminder.event.ui.occasiondialog;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fereshte.event_reminder.databinding.DialogOccasionsBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class OccasionDialog extends DialogFragment implements RadioGroup.OnCheckedChangeListener {

    public static final String TAG = "OccasionDialog";
    public static final String SELECTED_OCCASION = "selectedOccasion";

    private DialogOccasionsBinding occasionsBinding;

    private final OnDialogCheckListener dialogCheckListener;

    private OccasionDialog(OnDialogCheckListener dialogCheckListener) {
        this.dialogCheckListener = dialogCheckListener;
    }

    public static OccasionDialog getOccasionDialogInstance(String occasion, OnDialogCheckListener listener){
        OccasionDialog occasionDialog = new OccasionDialog(listener);
        Bundle bundle = new Bundle();
        bundle.putString(SELECTED_OCCASION, occasion);
        occasionDialog.setArguments(bundle);
        return occasionDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        occasionsBinding = DialogOccasionsBinding.inflate(inflater, container, false);
        init();
        return occasionsBinding.getRoot();
    }

    private void init() {
        setData();
        occasionsBinding.occasionsRg.setOnCheckedChangeListener(this);
    }

    private void setData() {
        if (getArguments() != null && getArguments().containsKey(SELECTED_OCCASION))
            checkSelectedRadioButton(getArguments().getString(SELECTED_OCCASION));
    }

    private void checkSelectedRadioButton(String occasion) {
        for (int k = 0; k < occasionsBinding.occasionsRg.getChildCount(); k++) {
            RadioButton textCollatorRb = (RadioButton) occasionsBinding.occasionsRg.getChildAt(k);
            if (textCollatorRb.getText().toString().equals(occasion)) {
                textCollatorRb.setChecked(true);
                break;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        getCheckedRadioButtonText(id);
        dismiss();
    }

    private void getCheckedRadioButtonText(int checkedRadioButtonId) {
        for (int j = 0; j < occasionsBinding.occasionsRg.getChildCount(); j++) {
            RadioButton idCollatorRb = (RadioButton) occasionsBinding.occasionsRg.getChildAt(j);
            if (idCollatorRb.getId() == checkedRadioButtonId) {
                dialogCheckListener.dialogCheck(idCollatorRb.getText().toString());
            }
        }
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

    public interface OnDialogCheckListener {
        void dialogCheck(String item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        occasionsBinding = null;
    }
}
