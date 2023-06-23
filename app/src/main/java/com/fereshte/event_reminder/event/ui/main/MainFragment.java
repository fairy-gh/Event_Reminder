package com.fereshte.event_reminder.event.ui.main;

import android.view.LayoutInflater;
import android.view.View;

import com.fereshte.event_reminder.base.BaseFragment;
import com.fereshte.event_reminder.data.model.EventItem;
import com.fereshte.event_reminder.event.ui.eventdetail.EventDetailFragment;
import com.fereshte.event_reminder.R;
import com.fereshte.event_reminder.databinding.FragmentMainBinding;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainFragment extends BaseFragment<MainViewModel, FragmentMainBinding> implements View.OnClickListener
, EventsAdapter.OnItemClickListener{

    private EventsAdapter eventsAdapter;


    public static MainFragment getMainFragmentInstance(){
        return new MainFragment();
    }

    @Override
    public FragmentMainBinding getViewBinding() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        return FragmentMainBinding.inflate(inflater);
    }

    @Override
    protected Class<MainViewModel> getViewModel() {
        return MainViewModel.class;
    }

    @Override
    public void setupToolbar() {
        viewBinding.toolbar.toolbarTitleTxv.setText(R.string.reminding);
        viewBinding.toolbar.backImg.setVisibility(View.GONE);
    }

    @Override
    public void initObjects() {
        viewModel.getDataFromDb();
        setUpRecyclerView();
    }

    @Override
    public void setupListeners() {
        viewBinding.floatingActionButton.setOnClickListener(this);
    }

    @Override
    public void observe() {
        observeEventList();
        if(requireActivity() instanceof OnSwitchFragmentListener)
           observeEventCreatorFragment();
    }


    private void observeEventList(){
        viewModel.getEventItemList().observe(this, eventItems -> eventsAdapter.notifyDataSetChanged());
    }

    private void observeEventCreatorFragment(){
        viewModel.getFragment().observe(this, fragment -> ((OnSwitchFragmentListener)requireActivity()).switchFragments(fragment, true));
    }

    private void setUpRecyclerView(){
        viewBinding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        eventsAdapter = new EventsAdapter(getContext(), viewModel.getEventItemList().getValue(), this);
        viewBinding.homeRecyclerView.setAdapter(eventsAdapter);
    }

    @Override
    public void onClick(View view) {
        viewModel.goToEventCreatorFragment();
    }

    @Override
    public void itemClick(EventItem eventItem) {
        ((OnSwitchFragmentListener)requireActivity()).switchFragments(EventDetailFragment.getEventDetailInstance(eventItem.getId()), true);
    }
}