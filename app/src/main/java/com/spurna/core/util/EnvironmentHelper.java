package com.spurna.core.util;

import android.webkit.CookieManager;

/**
 * Created by Oriako on 05/03/2018.
 */

public class EnvironmentHelper {

    public static String SERVER_URL = "https://spurna.raindrinker.com/";
    public static String PATH_FILE = "";

    private String productId;

    private static volatile EnvironmentHelper instance = new EnvironmentHelper();

    public static EnvironmentHelper getInstance() {
        return instance;
    }

    public String getProductId()
    {
        return productId;
    }
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
}
