package com.fereshte.event_reminder.event;

import android.content.Intent;
import android.net.Uri;
import  android.os.Bundle;
import android.widget.Toast;
import com.fereshte.event_reminder.R;
import com.fereshte.event_reminder.databinding.ActivityMainBinding;
import com.fereshte.event_reminder.event.ui.eventcreate.AddEventFragment;
import com.fereshte.event_reminder.event.ui.main.MainFragment;
import com.fereshte.event_reminder.event.ui.main.OnSwitchFragmentListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements OnSwitchFragmentListener {

    private FragmentManager fragmentManager;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        handleIntent(getIntent());
    }

    private void init() {
        fragmentManager = getSupportFragmentManager();
        switchFragments(MainFragment.getMainFragmentInstance(), false);
    }

    @Override
    public void switchFragments(Fragment fragment, Boolean backStack) {
        if (fragmentManager != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            if (backStack)
                transaction.addToBackStack(fragment.getClass().getName());
            transaction.commit();
        }
    }

    private void handleIntent(Intent intent){
        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();
        if(Intent.ACTION_VIEW.equals(appLinkAction) & appLinkData != null){
            String token = appLinkData.getLastPathSegment().trim();
            Toast.makeText(this, token, Toast.LENGTH_LONG).show();
            switchFragments(AddEventFragment.getAddEventInstance(null, token), true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}