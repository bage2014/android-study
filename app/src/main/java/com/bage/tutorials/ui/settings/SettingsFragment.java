package com.bage.tutorials.ui.settings;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bage.tutorials.BootstrapActivity;
import com.bage.tutorials.MainActivity;
import com.bage.tutorials.R;
import com.bage.tutorials.component.dialog.AlertDialogHelper;
import com.bage.tutorials.domain.AppVersion;
import com.bage.tutorials.domain.UpdateResult;
import com.bage.tutorials.ui.about.AboutFragment;
import com.bage.tutorials.ui.server.DevelopSettingActivity;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;
import com.bage.tutorials.utils.ToastUtils;
import com.king.app.updater.AppUpdater;

import java.util.Objects;

public class SettingsFragment extends Fragment {

    private UpdateViewModel updateViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;
            mainActivity.setSearchViewVisibility(false);
        }
        updateViewModel = ViewModelProviders.of(this).get(UpdateViewModel.class);
        updateViewModel.getUpdatableResult().observe(this, httpResult -> {
            //简单弹框升级
            LoggerUtils.info(AboutFragment.class, JsonUtils.toJson(httpResult));
            UpdateResult result = JsonUtils.fromJson(httpResult.getData(), UpdateResult.class);
            if (Objects.nonNull(result) && result.isNeedUpdate()) {
                AppVersion appVersion = result.getAppVersion();
                String title = "App Update";
                String message = appVersion.getDesc().replace("\\n", "\n");
                // icon, title, message, 2 actions
                new AlertDialogHelper(getContext()).showConfirmDialog(title, message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new AppUpdater.Builder()
                                .serUrl(appVersion.getApkUrl())
                                .build(getContext())
                                .start();
                    }
                }, null);
            } else {
                ToastUtils.show(getActivity(), "This is the latest version");
            }
        });
        root.findViewById(R.id.settings_card_update).setOnClickListener(v -> updateViewModel.checkForUpdate(BootstrapActivity.appVersion));
        root.findViewById(R.id.settings_card_server).setOnClickListener(v -> startActivity(new Intent(activity, DevelopSettingActivity.class)));
        return root;
    }

}
