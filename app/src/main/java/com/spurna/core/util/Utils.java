package com.spurna.core.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Oriako on 05/03/2018.
 */

public class Utils {

    public static String sendGet2(String method, Map<String,String> getParams)
    {

        String content = "";
        try {
            URL url = new URL(EnvironmentHelper.SERVER_URL + File.separator + "products");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            String contentType = connection.getContentType();
            String mesg = connection.getResponseMessage();
            Object cont = connection.getContent();
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                content += line + "\n";
            }
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        return content;
    }

    public static String getURLGet(String method, Map<String,String> getParams)
    {
        String result = "";

        String baseURL = EnvironmentHelper.SERVER_URL;
        result += baseURL;
        if (method != null && !method.isEmpty())
            result += method + File.separator;

        if (getParams != null && !getParams.isEmpty())
        {
            String paramStr = "?";
            for (Map.Entry<String,String> entry : getParams.entrySet())
            {
                paramStr += entry.getKey() + "=" + entry.getValue() + "&";
            }

            paramStr = paramStr.substring(0, paramStr.length()-1);
            result += paramStr;
        }

        return result;
    }

    public static String sendGet(String method, Map<String,String> getParams)
    {
        String content = "";
        try {
            String urlStr = getURLGet(method,getParams);
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            //connection.setDoOutput(true);
            connection.setDoInput(true);
            //connection.setConnectTimeout(5000);
            //connection.setReadTimeout(5000);
            connection.connect();
            int code = connection.getResponseCode();
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                content += line + "\n";
            }
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        return content;
    }

    public static boolean setImageFromAssets(String imageName, ImageView mImage)
    {
        if (mImage == null)
            return false;

        try {
            // get input stream
            InputStream ims = mImage.getResources().getAssets().open(imageName);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            mImage.setImageDrawable(d);
        }
        catch(IOException ex) {
            return false;
        }

        return true;
    }

    public static boolean setButtonBackgroundFromAssets(String imageName, Button button)
    {
        ImageView mImage = new ImageView(button.getContext());
        try {
            // get input stream
            InputStream ims = mImage.getResources().getAssets().open(imageName);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            button.setBackground(d);
        }
        catch(IOException ex) {
            return false;
        }

        return true;
    }

    public static void pushFragment(Activity activity, Integer layoutId, Fragment fragment) {
        if (fragment == null)
            return;

        FragmentManager fragmentManager = activity.getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(layoutId, fragment);
                ft.commit();
            }
        }
    }

    public static void doCreateConfig(Context context, String configStr) throws IOException
    {
        File path = context.getFilesDir();
        File file = new File(path, "config.txt");

        FileOutputStream stream = new FileOutputStream(file);
        try {
            stream.write(configStr.getBytes());
        } finally {
            stream.close();
        }
    }

    public static String doReadConfig(Context context) throws IOException
    {
        File path = context.getFilesDir();
        File file = new File(path, "config.txt");
        int length = (int) file.length();

        byte[] bytes = new byte[length];

        FileInputStream in = new FileInputStream(file);
        try {
            in.read(bytes);
        } finally {
            in.close();
        }

        String contents = new String(bytes);
        return contents;
    }
}
