package com.bage.tutorials.ui.feedback;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bage.tutorials.MainActivity;
import com.bage.tutorials.R;

public class FeedbackMsgFragment extends Fragment {

    private FeedbackMsgViewModel aboutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aboutViewModel =
                ViewModelProviders.of(this).get(FeedbackMsgViewModel.class);
        View root = inflater.inflate(R.layout.fragment_feedback, container, false);


        Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;
            mainActivity.setSearchViewVisibility(false);
        }

        return root;
    }
}