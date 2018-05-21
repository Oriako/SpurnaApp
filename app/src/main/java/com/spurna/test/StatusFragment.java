package com.spurna.test;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.spurna.core.util.EnvironmentHelper;
import com.spurna.core.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oriako on 05/03/2018.
 */

public class StatusFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.status_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button feedButton = view.findViewById(R.id.buttonFill);
        feedButton.setOnClickListener(this);

        ProgressBar progressBar = this.getView().findViewById(R.id.vertical_progressbar);
        if (progressBar != null) {
            Integer progress = getProgress();
            progressBar.setProgress(progress);
        }
    }

    @Override
    public void onClick(View view) {
        setNow();
    }

    private Integer getProgress() {
        Map<String, String> params = new HashMap<>();
        params.put("productId", EnvironmentHelper.getInstance().getProductId());

        Utils.sendGet("amountget", params);

        return 0;
    }

    private void setNow() {
        Map<String, String> params = new HashMap<>();
        params.put("productId", EnvironmentHelper.getInstance().getProductId());

        Utils.sendGet("newset", params);
    }
}
