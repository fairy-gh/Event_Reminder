package com.fereshte.event_reminder.event.ui.eventdetail;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fereshte.event_reminder.base.BaseFragment;
import com.fereshte.event_reminder.event.ui.eventcreate.AddEventFragment;
import com.fereshte.event_reminder.event.ui.main.MainFragment;
import com.fereshte.event_reminder.event.ui.main.OnSwitchFragmentListener;
import com.fereshte.event_reminder.event.ui.removeeventdialog.RemoveEventDialog;
import com.fereshte.event_reminder.R;
import com.fereshte.event_reminder.databinding.FragmentEventDetailBinding;
import java.util.List;

public class EventDetailFragment extends BaseFragment<EventDetailViewModel, FragmentEventDetailBinding> implements View.OnClickListener
        , RemoveEventDialog.onRemoveDialogClickedListener {

    public static final String EVENT_ID = "eventId";

    public static EventDetailFragment getEventDetailInstance(int eventId) {
        EventDetailFragment eventDetailFragment = new EventDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EVENT_ID, eventId);
        eventDetailFragment.setArguments(bundle);
        return eventDetailFragment;
    }

    @Override
    protected FragmentEventDetailBinding getViewBinding() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        return FragmentEventDetailBinding.inflate(inflater);
    }

    @Override
    protected Class<EventDetailViewModel> getViewModel() {
        return EventDetailViewModel.class;
    }

    @Override
    public void setupToolbar() {
        viewBinding.toolbar.toolbarTitleTxv.setText(R.string.eventDetails);
        viewBinding.toolbar.backImg.setOnClickListener(this);
    }

    @Override
    public void initObjects() {
        getEventDetail();
    }

    @Override
    public void setupListeners() {
        viewBinding.bottomBtns.editLL.setOnClickListener(this);
        viewBinding.bottomBtns.deleteLL.setOnClickListener(this);
        viewBinding.bottomBtns.shareLL.setOnClickListener(this);
    }

    @Override
    public void observe() {
        observeEventDetail();
        observeEventColor();
        observeEventDate();
        observeEventDelete();
        observeEventShare();
    }

    private void observeEventDetail() {
        viewModel.getEventModel().observe(this, eventModel -> {
            viewBinding.eventTitleTxv.setText(eventModel.getEventTitle());
            setViewsValueAndVisibility(eventModel.getEventOccasion(), viewBinding.eventOccasionTxv, viewBinding.detailOccasionGp);
            setViewsValueAndVisibility(eventModel.getEventNote(), viewBinding.eventDescriptionTxv, viewBinding.detailDescriptionGp);
            setViewsValueAndVisibility(eventModel.getEventLocation(), viewBinding.eventLocationTxv, viewBinding.detailLocationGp);
            setViewsValueAndVisibility(eventModel.getEventLink(), viewBinding.eventLinkTxv, viewBinding.detailLinkGp);
        });
    }

    private void observeEventColor() {
        viewModel.getColor().observe(this, color -> viewBinding.eventColorIv.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN));
    }

    private void observeEventDate() {
        viewModel.getEventDate().observe(this, date -> viewBinding.eventDateTxv.setText(date));
        viewModel.getEventTime().observe(this, time -> viewBinding.eventTimeTxv.setText(time));
    }

    private void observeEventDelete() {
        viewModel.getIsEventDeleted().observe(this, isEventDeleted -> {
            if (isEventDeleted)
                ((OnSwitchFragmentListener) requireActivity()).switchFragments(MainFragment.getMainFragmentInstance(), false);
        });
    }

    private void observeEventShare(){
        viewModel.getLinkAndTitle().observe(this, this::shareLink);
    }

    private void shareLink(List<String> linkAndTitle){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, String.format(getContext().getString(R.string.shareEventExtraText), "\n", "\"", linkAndTitle.get(1), "\"", "\n\n", linkAndTitle.get(0)));
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.backImg)
            requireActivity().getSupportFragmentManager().popBackStack();
        else if (view.getId() == R.id.editLL) {
            ((OnSwitchFragmentListener)requireActivity()).switchFragments(AddEventFragment.getAddEventInstance(getArguments().getInt(EVENT_ID), ""), true);
        } else if (view.getId() == R.id.deleteLL)
            new RemoveEventDialog(this).show(getChildFragmentManager(), RemoveEventDialog.TAG);
        else if (view.getId() == R.id.shareLL) {
//            viewModel.shareEvent(getArguments().getInt(EVENT_ID));
        }
    }

    private void getEventDetail() {
        viewModel.getEventDetailFromDb(getArguments().getInt(EVENT_ID));
    }

    private void setViewsValueAndVisibility(String content, TextView contentView, View titleGroup) {
        if (!content.equals("")) {
            contentView.setText(content);
            titleGroup.setVisibility(View.VISIBLE);
        } else titleGroup.setVisibility(View.GONE);
    }

    @Override
    public void onButtonClicked(Boolean clicked) {
        if (clicked)
            viewModel.deleteEventFromDb(getArguments().getInt(EVENT_ID));
    }
}