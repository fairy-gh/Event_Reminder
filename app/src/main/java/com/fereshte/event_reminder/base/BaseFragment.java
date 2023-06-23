package com.fereshte.event_reminder.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment<VModel extends ViewModel, VBinding extends ViewBinding>
        extends Fragment {

    protected VBinding viewBinding;
    protected VModel viewModel;

    protected abstract VBinding getViewBinding();

    protected abstract Class<VModel> getViewModel();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (viewBinding == null){
            init();
            setupToolbar();
            setupListeners();
            observe();
        }
        initObjects();
        return viewBinding.getRoot();
    }

    public void init() {
        viewBinding = getViewBinding();
        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()))
                .get(getViewModel());
    }

    public abstract void setupToolbar();

    public abstract void initObjects();

    public abstract void setupListeners();

    public abstract void observe();

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewBinding = null;
    }
}
