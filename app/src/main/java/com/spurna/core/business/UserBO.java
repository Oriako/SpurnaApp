package com.spurna.core.business;

import com.spurna.core.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Oriako on 05/03/2018.
 */

public class UserBO extends GenericBO {

    public User createUser(String userId)
    {
        User user = new User();

        user.setUserId(userId);

        return user;
    }

    public JSONObject convertToJson(JSONObject result, User user)
    {
        if (result == null)
            result = new JSONObject();
        if (user == null)
            return result;

        try
        {
            result.put("userId", user.getUserId());

        } catch (JSONException e)
        {
            return null;
        }

        return result;
    }
}
