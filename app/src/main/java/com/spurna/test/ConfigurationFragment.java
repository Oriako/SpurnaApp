package com.spurna.test;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.spurna.core.util.EnvironmentHelper;
import com.spurna.core.util.Utils;

import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Oriako on 05/03/2018.
 */

public class ConfigurationFragment extends Fragment implements View.OnClickListener {

    private Button feedButton;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.configuration_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        feedButton = view.findViewById(R.id.buttonLinkProduct);
        feedButton.setOnClickListener(this);

        textView = view.findViewById(R.id.configTextView);
        if (EnvironmentHelper.getInstance().getProductId() != null)
            textView.setText(EnvironmentHelper.getInstance().getProductId());
    }

    @Override
    public void onClick(View view) {
        setProductId();
    }

    private void setProductId()
    {
        String productId = textView.getText().toString();
        if (productId != null || !productId.isEmpty())
        {
            JSONObject object = new JSONObject();
            try {
                object.put("productId", productId);
                Utils.doCreateConfig(this.getActivity().getApplicationContext(), object.toString());
            } catch (Throwable e) {
                e.printStackTrace();
            }
            EnvironmentHelper.getInstance().setProductId(productId);
        }
    }

}
