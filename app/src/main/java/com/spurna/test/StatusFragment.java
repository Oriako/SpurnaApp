package com.spurna.test;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

/**
 * Created by Oriako on 05/03/2018.
 */

public class StatusFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.status_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        Button feedButton = view.findViewById(R.id.buttonFill);
        feedButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ProgressBar progressBar = this.getView().findViewById(R.id.vertical_progressbar);
        if (progressBar != null)
        {
            Integer progress = progressBar.getProgress() + 40;
            if (progress >= 100)
                progress -= 100;
            progressBar.setProgress(progress);
        }
    }
}
