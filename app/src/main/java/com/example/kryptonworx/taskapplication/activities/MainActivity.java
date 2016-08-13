package com.example.kryptonworx.taskapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.kryptonworx.taskapplication.helpers.Constants;
import com.example.kryptonworx.taskapplication.helpers.MyApplication;
import com.example.kryptonworx.taskapplication.interfaces.OnPhotoClickListener;
import com.example.kryptonworx.taskapplication.models.PhotoItem;
import com.example.kryptonworx.taskapplication.R;
import com.example.kryptonworx.taskapplication.helpers.Utilities;
import com.example.kryptonworx.taskapplication.adapters.SearchList_Adapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnPhotoClickListener {


    private List<PhotoItem> photoItemList;
    private RecyclerView mRecyclerView;
    private SearchList_Adapter adapter;
    private ProgressBar progressBar;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setListeners();
    }

    private void initViews() {

        progressBar = (ProgressBar) findViewById(R.id.loadingpanel);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        etSearch = (EditText) findViewById(R.id.search_et);
    }

    private void setListeners() {
        etSearch.setOnKeyListener(etSearchOnKeyListener);
    }

    private void search(final String query) {

        String url = Constants.Search_Service_URL;

        String app_sign = Utilities.getMd5(Constants.Secret_key + "api_key" + Constants.Api_key + "formatjsonmethodflickr.photos.searchnojsoncallback1text" + query);
        url = String.format(url, query, app_sign);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String stat = response.getString("stat");
                            if (stat.equals("ok")) {
                                photoItemList = new ArrayList<>();
                                progressBar.setVisibility(View.GONE);
                                mRecyclerView.setVisibility(View.VISIBLE);

                                JSONArray jsonArray = response.getJSONObject("photos").getJSONArray("photo");
                                Gson gson = new GsonBuilder().serializeNulls().create();
                                photoItemList = Arrays.asList(gson.fromJson(jsonArray.toString(), PhotoItem[].class));
                                MyApplication.myList.put(query, photoItemList);

                                updateUI();


                            } else {
                                progressBar.setVisibility(View.GONE);
                                mRecyclerView.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Try again please", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Try again please", Toast.LENGTH_LONG).show();
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
        adapter = new SearchList_Adapter(MainActivity.this, photoItemList);
        mRecyclerView.setAdapter(adapter);

    }

    View.OnKeyListener etSearchOnKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            // If the event is a key-down event on the "enter" button
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Perform action on key press
                String search_query = etSearch.getText().toString();
                if (MyApplication.myList.containsKey(search_query)) {
                    adapter = new SearchList_Adapter(MainActivity.this, MyApplication.myList.get(search_query));
                    mRecyclerView.setAdapter(adapter);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    search(search_query);
                }
                return true;
            }
            return false;
        }
    };

    @Override
    public void onPhotoClick(PhotoItem photoItem) {
        String ownerId = photoItem.getOwner();
        Intent intent = new Intent(this, PhotoDetails.class);
        intent.putExtra("owner_id", ownerId);
        startActivity(intent);
    }
}
