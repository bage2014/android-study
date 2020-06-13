package com.bage.tutorials.ui.about;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bage.tutorials.BootstrapActivity;
import com.bage.tutorials.MainActivity;
import com.bage.tutorials.R;
import com.bage.tutorials.domain.UpdateResult;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.king.app.updater.AppUpdater;

import java.util.Objects;

public class AboutFragment extends Fragment {

    private AboutViewModel aboutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aboutViewModel =
                ViewModelProviders.of(this).get(AboutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        aboutViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        aboutViewModel.getUpdatableResult().observe(this, new Observer<HttpResult>() {
            @Override
            public void onChanged(@Nullable HttpResult httpResult) {
                //简单弹框升级
                LoggerUtils.info(AboutFragment.class, JsonUtils.toJson(httpResult));
                UpdateResult result = JsonUtils.fromJson(httpResult.getData(), UpdateResult.class);
                if (Objects.nonNull(result) && result.isNeedUpdate()) {
                    String positiveText = "Update";
                    String negativeText = "Cancel";
                    String title = "App Update";
                    String message = "1、修改TV链接、\n2、修改已知Bug";
                    // icon, title, message, 2 actions
                    new MaterialAlertDialogBuilder(getContext())
                            .setTitle(title)
                            .setMessage(message)
                            .setPositiveButton(positiveText, (dialog, which) -> new AppUpdater.Builder()
                                    .serUrl(result.getApkUrl())
                                    .build(getContext())
                                    .start())
                            .setNegativeButton(negativeText, null)
                            .setIcon(R.drawable.ic_about_outline_black_24dp).show();
                } else {
                    Toast.makeText(getActivity(), "do not need update!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;
            mainActivity.setSearchViewVisibility(false);
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutViewModel.checkForUpdate(BootstrapActivity.appVersion);
            }
        });
        return root;
    }
}