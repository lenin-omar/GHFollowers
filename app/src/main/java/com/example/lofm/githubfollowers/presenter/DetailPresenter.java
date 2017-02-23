package com.example.lofm.githubfollowers.presenter;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.lofm.githubfollowers.R;
import com.example.lofm.githubfollowers.model.GHUser;
import com.example.lofm.githubfollowers.rest.GsonRequest;
import com.example.lofm.githubfollowers.rest.VolleySingleton;


/**
 * Created by LOFM on 23/02/2017.
 */
public class DetailPresenter implements Response.Listener<Object>, Response.ErrorListener {

    private Context context;
    private DetailListener listener;

    public DetailPresenter(Context context) {
        this.context = context;
    }

    public void registerListener(DetailListener listener) {
        this.listener = listener;
    }

    public void unRegisterListener() {
        listener = null;
    }

    public void getFollowerDetails(String userId) {
        String url = context.getString(R.string.endpoint) + userId;
        GsonRequest request = new GsonRequest(Request.Method.GET, url, GHUser.class, null, this, this);
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
    }

    @Override
    public void onResponse(Object response) {
        if (response != null) {
            GHUser ghUser = (GHUser) response;
            listener.onSuccess(ghUser);
        } else {
            listener.onError(context.getString(R.string.server_error));
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        listener.onError(context.getString(R.string.server_error));
    }

    public interface DetailListener {

        void onSuccess(GHUser ghUser);

        void onError(String errorMessage);
    }
}
