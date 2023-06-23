package com.fereshte.event_reminder.event.ui.eventcreate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.fereshte.event_reminder.base.BaseFragment;
import com.fereshte.event_reminder.data.model.DateModel;
import com.fereshte.event_reminder.data.model.EventModel;
import com.fereshte.event_reminder.data.model.TimeModel;
import com.fereshte.event_reminder.event.ui.eventdetail.EventDetailFragment;
import com.fereshte.event_reminder.event.ui.occasiondialog.OccasionDialog;
import com.fereshte.event_reminder.R;
import com.fereshte.event_reminder.databinding.FragmentAddEventBinding;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;

public class AddEventFragment extends BaseFragment<AddEventViewModel, FragmentAddEventBinding> implements View.OnClickListener,
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener,
        OccasionDialog.OnDialogCheckListener, RadioGroup.OnCheckedChangeListener {

    public static final int MONTH_DIFFER = 1;
    private static final String TOKEN = "token";

    public static AddEventFragment getAddEventInstance(Integer eventId, String token) {
        AddEventFragment addEventFragment = new AddEventFragment();
        if (eventId != null || !token.equals("")) {
            Bundle bundle = new Bundle();
            if (eventId != null)
                bundle.putInt(EventDetailFragment.EVENT_ID, eventId);
            if (!token.equals(null))
                bundle.putString(TOKEN, token);
            addEventFragment.setArguments(bundle);
        }
        return addEventFragment;
    }

    @Override
    protected FragmentAddEventBinding getViewBinding() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        return FragmentAddEventBinding.inflate(inflater);
    }

    @Override
    protected Class<AddEventViewModel> getViewModel() {
        return AddEventViewModel.class;
    }

    @Override
    public void setupToolbar() {
        viewBinding.toolbar.toolbarTitleTxv.setText(R.string.event_creator_toolbar_title);
    }

    @Override
    public void initObjects() {
        if (getArguments() != null && getArguments().containsKey(EventDetailFragment.EVENT_ID))
            viewModel.getEventDetailFromDb(getArguments().getInt(EventDetailFragment.EVENT_ID));
        setDrawable();
    }

    private void setDrawable() {
        viewBinding.colorPaletteBlueRb.setButtonDrawable(R.drawable.event_color_blue_radiobutton_selector);
        viewBinding.colorPaletteYellowRb.setButtonDrawable(R.drawable.event_color_yellow_radiobutton_selector);
        viewBinding.colorPaletteGreenRb.setButtonDrawable(R.drawable.event_color_green_radiobutton_selector);
        viewBinding.colorPaletteRedRb.setButtonDrawable(R.drawable.event_color_red_radiobutton_selector);
    }

    @Override
    public void observe() {
        observeEventDetail();
        observeInitialTime();
        observeInitialDate();
        observeTitleValidation();
        observeDateDialog();
        observeTimeDialog();
        observeOccasion();
        observeColors();
        observeDescriptionTxvVisibility();
        observeLocationTxvVisibility();
        observeLinkTxvVisibility();
        observePlusIconVisibility();
    }


    private void observeEventDetail() {
        viewModel.getEventModel().observe(this, eventModel -> {
            viewBinding.eventTitleEdt.setText(eventModel.getEventTitle());
            viewBinding.eventOccasionTxv.setText(eventModel.getEventOccasion());
            checkSelectedRadioButton(eventModel.getEventColor());
            viewBinding.eventDescriptionEdt.setText(eventModel.getEventNote());
            viewBinding.eventLocationEdt.setText(eventModel.getEventLocation());
            viewBinding.eventLinkEdt.setText(eventModel.getEventLink());
        });
    }

    private void observeInitialTime() {
        viewModel.getEventTime().observe(this, time -> viewBinding.eventTimeTxv.setText(time));
    }

    private void observeInitialDate() {
        viewModel.getEventDate().observe(this, date -> viewBinding.eventDateTxv.setText(date));
    }

    private void observeTitleValidation() {
        viewModel.getTitleValidation().observe(this, isValid -> {
            if (isValid) {
                requireActivity().getSupportFragmentManager().popBackStack();
            } else
                viewBinding.eventTitleEdt.setError(getString(R.string.EditTextError));
        });
    }

    private void observeDateDialog() {
        viewModel.getDateModel().observe(this, this::showDateDialog);
    }

    private void observeTimeDialog() {
        viewModel.getTimeModel().observe(this, this::showTimeDialog);
    }

    private void observeOccasion() {
        viewModel.getOccasion().observe(this, this::showOccasionDialog);
    }

    private void observeColors() {
        viewModel.getColor().observe(this, this::checkSelectedRadioButton);
    }

    private void observeDescriptionTxvVisibility() {
        viewModel.getVisibleDescriptionTxv().observe(this, isVisible -> setViewsVisibility(isVisible, viewBinding.descriptionAndIcon, viewBinding.eventDescriptionBtn));
    }

    private void observeLocationTxvVisibility() {
        viewModel.getVisibleLocationTxv().observe(this, isVisible -> setViewsVisibility(isVisible, viewBinding.locationAndIcon, viewBinding.eventLocationBtn));
    }

    private void observeLinkTxvVisibility() {
        viewModel.getVisibleLinkTxv().observe(this, isVisible -> setViewsVisibility(isVisible, viewBinding.linkAndIcon, viewBinding.eventLinkBtn));
    }

    private void observePlusIconVisibility() {
        viewModel.getVisiblePlusIcon().observe(this, isVisible -> {
            if (isVisible)
                viewBinding.plusIconTxv.setVisibility(View.VISIBLE);
            else viewBinding.plusIconTxv.setVisibility(View.GONE);
        });
    }


    @Override
    public void setupListeners() {
        viewBinding.eventTimeDateLay.setOnClickListener(this);
        viewBinding.eventOccasionTxv.setOnClickListener(this);
        viewBinding.eventDescriptionBtn.setOnClickListener(this);
        viewBinding.eventLocationBtn.setOnClickListener(this);
        viewBinding.eventLinkBtn.setOnClickListener(this);
        viewBinding.storeEventInDbBtn.setOnClickListener(this);
        viewBinding.cancelBtn.setOnClickListener(this);
        viewBinding.toolbar.backImg.setOnClickListener(this);

        viewBinding.colorPaletteRg.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.eventTimeDateLay)
            viewModel.onClickDate();
        else if (view.getId() == R.id.eventOccasionTxv) {
            if (getArguments() != null && getArguments().containsKey(EventDetailFragment.EVENT_ID))
                showOccasionDialog(viewBinding.eventOccasionTxv.getText().toString());
            else
                viewModel.onClickOccasion();
        } else if (view.getId() == R.id.eventDescriptionBtn) {
            viewModel.OnClickDescription();
            viewModel.getPlusIconVisibility();
        } else if (view.getId() == R.id.eventLocationBtn) {
            viewModel.OnClickLocation();
            viewModel.getPlusIconVisibility();
        } else if (view.getId() == R.id.eventLinkBtn) {
            viewModel.OnClickLink();
            viewModel.getPlusIconVisibility();
        } else if (view.getId() == R.id.storeEventInDbBtn)
            storeInDataBase();
        else if (view.getId() == R.id.cancelBtn || view.getId() == R.id.backImg)
            requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void showOccasionDialog(String occasion) {
        OccasionDialog occasionDialog = OccasionDialog.getOccasionDialogInstance(occasion, this);
        occasionDialog.show(getChildFragmentManager(), OccasionDialog.TAG);
    }

    private void storeInDataBase() {
        String title = viewBinding.eventTitleEdt.getEditableText().toString();
        String description = viewBinding.eventDescriptionEdt.getEditableText().toString();
        String location = viewBinding.eventLocationEdt.getEditableText().toString();
        String link = viewBinding.eventLinkEdt.getEditableText().toString();

        viewModel.storeEventInDataBase(new EventModel(
                title, null, null, description, location, link, 0));
        //viewModel.storeEventInDataBase(title, description, location, link);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        int blue = 0;
        int yellow = 1;
        int green = 2;
        int red = 3;
        if (id == R.id.colorPaletteRedRb)
            viewModel.setEventColor(red);
        else if (id == R.id.colorPaletteGreenRb)
            viewModel.setEventColor(green);
        else if (id == R.id.colorPaletteYellowRb)
            viewModel.setEventColor(yellow);
        else if (id == R.id.colorPaletteBlueRb)
            viewModel.setEventColor(blue);
    }

    private void checkSelectedRadioButton(int checkedRadioButtonId) {
        for (int j = 0; j < viewBinding.colorPaletteRg.getChildCount(); j++) {
            RadioButton idCollatorRb = (RadioButton) viewBinding.colorPaletteRg.getChildAt(j);
            if (j == checkedRadioButtonId) {
                idCollatorRb.setChecked(true);
                break;
            }
        }
    }


    private void showDateDialog(DateModel dateModel) {
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                dateModel.getYear(),
                dateModel.getMonth() - MONTH_DIFFER,
                dateModel.getDay());
        datePickerDialog.show(requireActivity().getFragmentManager(), getString(R.string.DatePickerDialog));
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        viewModel.setDateDialog(year, monthOfYear, dayOfMonth);
    }

    private void showTimeDialog(TimeModel timeModel) {
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this,
                timeModel.getHour(),
                timeModel.getMinute(),
                true);
        timePickerDialog.show(requireActivity().getFragmentManager(), getString(R.string.TimePickerDialog));
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hour, int minute) {
        viewModel.setTimeDialog(hour, minute);
    }

    @Override
    public void dialogCheck(String item) {
        viewModel.getOccasionItem(item);
        viewBinding.eventOccasionTxv.setText(item);
    }

    private void setViewsVisibility(Boolean visible, View group, View button) {
        if (visible) {
            group.setVisibility(View.VISIBLE);
            button.setVisibility(View.GONE);
        } else {
            group.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
        }
    }
}
