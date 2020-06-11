package com.bage.tutorials.ui.about;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bage.tutorials.BootstrapActivity;
import com.bage.tutorials.MainActivity;
import com.bage.tutorials.R;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;
import com.king.app.dialog.AppDialog;
import com.king.app.dialog.AppDialogConfig;
import com.king.app.updater.AppUpdater;

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
                AppDialogConfig config = new AppDialogConfig();
                config.setTitle("简单弹框升级")
                        .setOk("升级")
                        .setContent("1、修改TV链接、\n2、修改某某问题、\n3、优化某某BUG、")
                        .setOnClickOk(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new AppUpdater.Builder()
                                        .serUrl("http://116.198.163.212:8088/tutorials/ignore/file/download/1591747724222")
                                        .build(getContext())
                                        .start();
                                AppDialog.INSTANCE.dismissDialog();
                            }
                        });
                AppDialog.INSTANCE.showDialog(getContext(),config);
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