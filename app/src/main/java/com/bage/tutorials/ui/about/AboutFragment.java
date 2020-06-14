package com.bage.tutorials.ui.about;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bage.tutorials.BootstrapActivity;
import com.bage.tutorials.MainActivity;
import com.bage.tutorials.R;
import com.bage.tutorials.component.dialog.AlertDialogHelper;
import com.bage.tutorials.domain.AppVersion;
import com.bage.tutorials.domain.UpdateResult;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;
import com.bage.tutorials.utils.ToastUtils;
import com.king.app.updater.AppUpdater;

import java.util.Objects;

public class AboutFragment extends Fragment {

    private AboutViewModel aboutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aboutViewModel =
                ViewModelProviders.of(this).get(AboutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        View btnCheckForUpdate = root.findViewById(R.id.btn_check_for_update);
        aboutViewModel.getUpdatableResult().observe(this, httpResult -> {
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

        Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;
            mainActivity.setSearchViewVisibility(false);
        }

        btnCheckForUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutViewModel.checkForUpdate(BootstrapActivity.appVersion);
            }
        });
        return root;
    }
}