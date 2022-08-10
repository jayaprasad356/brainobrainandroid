package com.gm.brainobrain.helper;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ApiConfig extends Application {
    static ApiConfig mInstance;
    public static final String TAG = ApiConfig.class.getSimpleName();
    RequestQueue mRequestQueue;
    static Session session;

    public static String VolleyErrorMessage(VolleyError error) {
        String message = "";
        try {
            message = "";
            if (error instanceof NetworkError) {
                message = "Cannot connect to Internet...Please check your connection!";
            } else if (error instanceof ServerError) {
                message = "The server could not be found. Please try again after some time!";
            } else if (error instanceof AuthFailureError) {
                message = "Cannot connect to Internet...Please check your connection!";
            } else if (error instanceof ParseError) {
                message = "Parsing error! Please try again after some time!";
            } else if (error instanceof TimeoutError) {
                message = "Connection TimeOut! Please check your internet connection.";
            } else
                message = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void RequestToVolley(final VolleyCallback callback, final Activity activity, final String url, final Map<String, String> params, final boolean isProgress, int method) {
        session = new Session(activity);
        if (ProgressDisplay.mProgressBar != null) {
            ProgressDisplay.mProgressBar.setVisibility(View.GONE);
        }
        final ProgressDisplay progressDisplay = new ProgressDisplay(activity);
        progressDisplay.hideProgress();

        if (isProgress)
            progressDisplay.showProgress();
        StringRequest stringRequest = new StringRequest( method, url, new Response.Listener<String>() {
            @Override
            public void onResponse( String s ) {
                if (ApiConfig.isConnected(activity))
                    callback.onSuccess(true, s);
                if (isProgress)
                    progressDisplay.hideProgress();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse( VolleyError volleyError ) {
               try {
                    String responseBody = new String( volleyError.networkResponse.data, "utf-8" );
                    JSONObject jsonObject = new JSONObject( responseBody );
                    if (ApiConfig.isConnected(activity))
                        callback.onSuccess(true, responseBody);
                    if (isProgress)
                        progressDisplay.hideProgress();
                } catch ( JSONException e ) {
                    //Handle a malformed json response
                } catch (UnsupportedEncodingException error){

                }
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params1 = new HashMap<>();
                params1.put(Constant.AUTHORIZATION, "Bearer " +session.getData(Constant.TOKEN));
                return params1;
            }


            @Override
            protected Map<String, String> getParams() {

                return params;
            }
        };

//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
//            if (ApiConfig.isConnected(activity))
//                callback.onSuccess(true, response);
//            if (isProgress)
//                progressDisplay.hideProgress();
//        }, error -> {
//            if (isProgress)
//                progressDisplay.hideProgress();
//            if (ApiConfig.isConnected(activity))
//                callback.onSuccess(false, "");
//            String message = VolleyErrorMessage(error);
//            if (!message.equals(""))
//                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
//        }) {
//
//
//            @Override
//            protected Map<String, String> getParams() {
//
//                return params;
//            }
//        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, 0));
        ApiConfig.getInstance().getRequestQueue().getCache().clear();
        ApiConfig.getInstance().addToRequestQueue(stringRequest);
    }

    public static synchronized ApiConfig getInstance() {
        return mInstance;
    }


    public static Boolean isConnected(final Activity activity) {
        boolean check = false;
        try {
            ConnectivityManager ConnectionManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                check = true;
            } else {
                //Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }



}