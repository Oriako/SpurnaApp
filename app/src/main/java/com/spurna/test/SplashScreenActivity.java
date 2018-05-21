package com.spurna.test;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.spurna.core.util.EnvironmentHelper;
import com.spurna.core.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.webkit.CookieManager;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * Created by Oriako on 13/05/2018.
 */

public class SplashScreenActivity extends Activity {

    private static final long SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.splash_screen);

        ImageView splashImageView = (ImageView) findViewById(R.id.splashImageView);
        if (splashImageView != null)
            Utils.setImageFromAssets("splash_screen.png", splashImageView);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Start the next activity
                Intent mainIntent = new Intent().setClass(
                        SplashScreenActivity.this, MainActivity.class);
                startActivity(mainIntent);

                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);

        String productId = null;
        try
        {
            String jsonToString = Utils.doReadConfig(getApplicationContext());
            JSONObject object = new JSONObject(jsonToString);
            productId = object.getString("productId");
        }
        catch (Throwable e)
        {

        }

        if (productId == null)
        {
            productId = UUID.randomUUID().toString();
            JSONObject object = new JSONObject();
            try {
                object.put("productId", productId);
                Utils.doCreateConfig(getApplicationContext(), object.toString());
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        EnvironmentHelper.getInstance().setProductId(productId);
    }



}
