package com.example.kryptonworx.taskapplication.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.kryptonworx.taskapplication.adapters.SearchList_Adapter;
import com.example.kryptonworx.taskapplication.helpers.Constants;
import com.example.kryptonworx.taskapplication.helpers.Utilities;
import com.example.kryptonworx.taskapplication.models.PhotoItem;
import com.example.kryptonworx.taskapplication.R;
import com.example.kryptonworx.taskapplication.adapters.Details_Adapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhotoDetails extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Details_Adapter adapter;
    private ProgressBar progressBar;
    private List<PhotoItem> photoItemsList;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photodetails);
        initViews();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            String ownerID = (String) bundle.get("owner_id");
            String secret = Constants.Secret_key;
            url = Constants.Details_Service_URL;
            String app_sign = Utilities.getMd5(secret + "api_keyf215a67afdb72fe1c0b48384925b047dformatjsonmethodflickr.people.getPhotosnojsoncallback1user_id" + ownerID);
            url = String.format(url, ownerID, app_sign);
        }


        getOwner_photos();
    }

    private void initViews() {
        progressBar = (ProgressBar) findViewById(R.id.loadingpanel);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_details);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getOwner_photos() {
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String stat = response.getString("stat");
                            if (stat.equals("ok")) {
                                photoItemsList = new ArrayList<>();
                                progressBar.setVisibility(View.GONE);
                                mRecyclerView.setVisibility(View.VISIBLE);

                                JSONArray jsonArray = response.getJSONObject("photos").getJSONArray("photo");
                                Gson gson = new GsonBuilder().serializeNulls().create();
                                photoItemsList = Arrays.asList(gson.fromJson(jsonArray.toString(), PhotoItem[].class));

                                updateUI();

                            } else {
                                progressBar.setVisibility(View.GONE);
                                mRecyclerView.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Try again ", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Try again ", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                }
        ) {

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(postRequest);

    }

    private void updateUI() {
        // adapter
        adapter = new Details_Adapter(PhotoDetails.this, photoItemsList);
        mRecyclerView.setAdapter(adapter);
    }

}
