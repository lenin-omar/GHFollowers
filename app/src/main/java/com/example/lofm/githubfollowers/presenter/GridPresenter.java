package com.example.lofm.githubfollowers.presenter;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.lofm.githubfollowers.R;
import com.example.lofm.githubfollowers.model.GHUser;
import com.example.lofm.githubfollowers.rest.GsonRequest;
import com.example.lofm.githubfollowers.rest.VolleySingleton;

import java.util.Arrays;
import java.util.List;

/**
 * Created by LOFM on 22/02/2017.
 */

public class GridPresenter implements Response.Listener<Object>, Response.ErrorListener {

    private Context context;
    private GridListener listener;

    public GridPresenter(Context context) {
        this.context = context;
    }

    public void registerListener(GridListener listener) {
        this.listener = listener;
    }

    public void unRegisterListener() {
        listener = null;
    }

    public void getFollowers(String userId) {
        String url = context.getString(R.string.endpoint) + userId + "/followers";
        GsonRequest request = new GsonRequest(Request.Method.GET, url, GHUser[].class, null, this, this);
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
    }

    @Override
    public void onResponse(Object response) {
        GHUser[] ghUsers = (GHUser[]) response;
        List<GHUser> ghUserList = Arrays.asList(ghUsers);
        listener.onSuccess(ghUserList);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        listener.onError(context.getString(R.string.server_error));
    }

    public interface GridListener {

        void onSuccess(List<GHUser> ghUsers);

        void onError(String errorMessage);
    }

}
