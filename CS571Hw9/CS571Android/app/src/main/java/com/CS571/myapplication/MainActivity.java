package com.CS571.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.CS571.myapplication.databinding.ActivityMainBinding;
import com.CS571.myapplication.model.autoComplete.autoCompleteResponse;
import com.CS571.myapplication.model.autoDetect.autoDetectResponse;
import com.CS571.myapplication.model.googleGeoLocationApi.locationResponse;
import com.CS571.myapplication.model.reviewYelp.reviewResponse;
import com.CS571.myapplication.model.yelpApi.Business;
import com.CS571.myapplication.model.yelpApi.BusinessLocation;
import com.CS571.myapplication.model.yelpApi.yelpResponse;
import com.CS571.myapplication.network.BackendYelpApi;
import com.CS571.myapplication.network.BackendYelpRetrofitClient;
import com.CS571.myapplication.network.GoogleApi;
import com.CS571.myapplication.network.GoogleRetrofitClient;
import com.CS571.myapplication.network.autoCompleteApi;
import com.CS571.myapplication.network.autoCompleteRetrofitClient;
import com.CS571.myapplication.network.autoDetectIPApi;
import com.CS571.myapplication.network.autoDetectIpRetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemClickListener {
    public static final String EXTRA_ID = "businessId";
    public static final String EXTRA_NAME = "businessName";
    public static final String EXTRA_LAT = "latitude";
    public static final String EXTRA_LNG = "longitude";
    public static final String EXTRA_LINK = "businessLink";
    ActivityMainBinding binding;

    CheckBox checkBox;
    Button submitButton, clearButton;
    EditText distance, location;
    AutoCompleteTextView keyWord;
    Spinner category;
    boolean isAllFieldsChecked = false; // initially all the filed are empty.
    String keyWordStr, locationStr, distanceStr, categoryStr;
    String backendBaseUrl = "https://hw8-backend-368020.wl.r.appspot.com/search?";
    final String myToken = "77d13dbec3db4a";
    Boolean isAutoDetect = false;


    RecyclerView recyclerView;
    List<Business> businessesList;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set up a toolbar
        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        ImageView reservationPage = findViewById(R.id.imageView2);
        reservationPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StorageActivity.class));
            }
        });

        createRedStar();
        // recyclerView
        recyclerView = findViewById(R.id.yelpRecyclerView);
        businessesList = new ArrayList<Business>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new Adapter(getApplicationContext(), businessesList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));
        // autocomplete
        keyWord = findViewById(R.id.keyWord);
        keyWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String enterdText = s.toString();
                autoCompleteRequest(enterdText);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // get all the content from the editText input
        distance = findViewById(R.id.distance);
        category = findViewById(R.id.category_spinner);
        location = findViewById(R.id.location);
        // define the checkBox
        checkBox = findViewById(R.id.simpleCheckBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoDetect = !isAutoDetect;
                if (isAutoDetect) {
                    location.setEnabled(false);
                    location.setVisibility(View.INVISIBLE);
                } else {
                    location.setEnabled(true);
                    location.setVisibility(View.VISIBLE);
                }
            }
        });
        // define the onSubmit and clear button
        submitButton = findViewById(R.id.submit);
        clearButton = findViewById(R.id.clear);
//        yelpRequest("chicken", "food", "10", "34.0920", "-118.3152");
        // create onClickListener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get keyWord from input form;
                isAllFieldsChecked = checkAllFields();
                if (isAllFieldsChecked) {
                    recyclerView.setVisibility(View.VISIBLE);
                    if (isAutoDetect) {
                        keyWordStr = keyWord.getText().toString();
                        distanceStr = distance.getText().toString();
                        categoryStr = category.getSelectedItem().toString();
                        autoDetectIpInfoRequest();
                    } else {
                        keyWordStr = keyWord.getText().toString();
                        distanceStr = distance.getText().toString();
                        locationStr = location.getText().toString();
                        categoryStr = category.getSelectedItem().toString();
                        googleLocationRequest(locationStr);
                    }
                }
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyWord.setText("");
                distance.setText("");
                category.setSelection(0);
                location.setText("");
                businessesList = new ArrayList<>();
                businessesList = new ArrayList<Business>();
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new Adapter(getApplicationContext(), businessesList);
                recyclerView.setAdapter(adapter);
                findViewById(R.id.noResult).setVisibility(View.INVISIBLE);
            }
        });
    }

    private void createRedStar() {
        String keyWord = "KeyWord";
        String distance = "Distance";
        String category = "Category";
        String location = "Location";
        String redStar = "*";

        SpannableStringBuilder keyWordLine = new SpannableStringBuilder();
        SpannableStringBuilder distanceLine = new SpannableStringBuilder();
        SpannableStringBuilder categoryLine = new SpannableStringBuilder();
        SpannableStringBuilder locationLine = new SpannableStringBuilder();
        // keyWord
        SpannableString keyWordColoredString = new SpannableString(keyWord);
        keyWordColoredString.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0,keyWord.length(), 0);
        keyWordLine.append(keyWordColoredString);
        // distance
        SpannableString distanceColoredString = new SpannableString(distance);
        distanceColoredString.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0,distance.length(), 0);
        distanceLine.append(distanceColoredString);
        // category
        SpannableString categoryColoredString = new SpannableString(category);
        categoryColoredString.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0,category.length(), 0);
        categoryLine.append(categoryColoredString);
        //location
        SpannableString locationColoredString = new SpannableString(location);
        locationColoredString.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0,location.length(), 0);
        locationLine.append(locationColoredString);
        // redStar
        SpannableString redColoredString= new SpannableString(redStar);
        redColoredString.setSpan(new ForegroundColorSpan(Color.RED), 0, redStar.length(), 0);
        keyWordLine.append(redColoredString);
        categoryLine.append(redColoredString);
        locationLine.append(redColoredString);
        // findViewById
        EditText keyWordEditText = findViewById(R.id.keyWord);
        EditText distanceEditText = findViewById(R.id.distance);
        TextView categoryEditText = findViewById(R.id.category);
        EditText locationEditText = findViewById(R.id.location);
        keyWordEditText.setHint(keyWordLine);
        distanceEditText.setHint(distanceLine);
        categoryEditText.setHint(categoryLine);
        locationEditText.setHint(locationLine);
    }

    private boolean checkAllFields() {
        if (keyWord.length() == 0) {
            keyWord.setError("This field is required");
            return false;
        }
        if (distance.length() == 0) {
            distance.setError("This field is required");
            return false;
        }
        if (location.length() == 0 && !isAutoDetect) {
            location.setError("This field is required");
            return false;
        }
        return true;
    }

    private void autoCompleteRequest(String text) {
        autoCompleteApi api = autoCompleteRetrofitClient.newInstance().create(autoCompleteApi.class);
        api.sendAutoCompleteYelpRequest(text).enqueue(new Callback<autoCompleteResponse>() {
            @Override
            public void onResponse(Call<autoCompleteResponse> call, Response<autoCompleteResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("autoComplete", response.body().toString());
                    List<String> autoList = new ArrayList<>();
                    for (int i = 0; i < response.body().categories.size(); i++) {
                        String cur = response.body().categories.get(i).title;
                        autoList.add(cur);
                    }
                    for (int i = 0; i < response.body().terms.size(); i++) {
                        String cur = response.body().terms.get(i).text;
                        autoList.add(cur);
                    }
                    // convert the autoList to autoArray
                    String[] autoArray = new String[autoList.size()];
                    int index = 0;
                    for (String cur : autoList) {
                        autoArray[index++] = cur;
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_list_item_1, autoArray);
                    keyWord.setAdapter(arrayAdapter);
                } else {
                    Log.d("autoComplete", "netWork error");
                }
            }
            @Override
            public void onFailure(Call<autoCompleteResponse> call, Throwable t) {
                Log.d("autoComplete", t.toString());
            }
        });
    }

    private void autoDetectIpInfoRequest() {
        autoDetectIPApi api = autoDetectIpRetrofitClient.newInstance().create(autoDetectIPApi.class);
        api.sendAutoDetectIpRequest(myToken).enqueue(new Callback<autoDetectResponse>() {
            @Override
            public void onResponse(Call<autoDetectResponse> call, Response<autoDetectResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("autoDetect", response.body().toString());
                    autoDetectResponse autoDetect= response.body();
                    String loc = autoDetect.loc;
                    String[] latLng = loc.split(",");
                    String lat = latLng[0];
                    String lng = latLng[1];
                    yelpRequest(keyWordStr, categoryStr, distanceStr, lat, lng);
                } else {
                    Log.d("autoDetect", "netWork error");
                }
            }

            @Override
            public void onFailure(Call<autoDetectResponse> call, Throwable t) {
                Log.d("autoDetect", t.toString());
            }
        });
    }

    private void googleLocationRequest(String locationStr) {
        GoogleApi api = GoogleRetrofitClient.newInstance().create(GoogleApi.class);
        api.getGoogleLocation(locationStr, "AIzaSyAII0DbVU9uKZI07_PMYNLyRDeiXXH3a98").enqueue(new Callback<locationResponse>() {
            @Override
            public void onResponse(Call<locationResponse> call, Response<locationResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("getGoogleLocation", response.body().toString());
                }
                String lat = response.body().results.get(0).geometry.location.lat;
                String lng = response.body().results.get(0).geometry.location.lng;
                Log.d("getGoogleLocation", lat);
                Log.d("getGoogleLocation", lng);
                backendBaseUrl += "term=" + keyWordStr + "&categories=" + categoryStr + "&distance=" + distanceStr +
                        "&latitude=" + lat + "&longitude=" + lng;
                Log.d("keyWord", backendBaseUrl);
                yelpRequest(keyWordStr, categoryStr, distanceStr, lat, lng);
            }
            @Override
            public void onFailure(Call<locationResponse> call, Throwable t) {
                Log.d("getGoogleLocation", t.toString());
            }
        });
    }

    private void yelpRequest(String term, String category, String distance, String latitude, String longitude) {
        BackendYelpApi api = BackendYelpRetrofitClient.newInstance().create(BackendYelpApi.class);
        api.sendYelpRequest(term, category, distance, latitude, longitude).enqueue(new Callback<yelpResponse>() {
            @Override
            public void onResponse(Call<yelpResponse> call, Response<yelpResponse> response) {
                if (response.isSuccessful()) {
                    List<Business> returnedData = response.body().businesses;
                    if (returnedData.size() <= 0) {
                        findViewById(R.id.noResult).setVisibility(View.VISIBLE);
                    }
                    for (int i = 0; i < returnedData.size(); i++) {
                        Business cur = returnedData.get(i);
                        Log.d("getGoogleLocation", cur.name);
                        Log.d("getGoogleLocation", cur.id);
                        Log.d("getGoogleLocation", cur.image_url);
                        Log.d("getGoogleLocation", new String(String.valueOf(cur.distance)));
                        Log.d("getGoogleLocation", new String(String.valueOf(cur.rating)));
                        Business businesses = new Business();
                        businesses.setName(cur.name);
                        businesses.setImage_url(cur.image_url);
                        businesses.setDistance(cur.distance);
                        businesses.setRating(cur.rating);
                        businesses.setId(cur.id);
                        businesses.setPrice(cur.price);
                        businesses.setPhone(cur.display_phone);
                        businesses.setUrl(cur.url);
                        businesses.setLocation(cur.location);
                        businesses.setCoordinates(cur.coordinates);
                        Log.d("detail", String.valueOf(cur.coordinates.latitude));
                        businessesList.add(businesses);
                    }
                    adapter.notifyDataSetChanged();
                    adapter.setOnItemClickListener(MainActivity.this);
                }
            }

            @Override
            public void onFailure(Call<yelpResponse> call, Throwable t) {
                Log.d("getGoogleLocation", t.toString());
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        Business clickItem = businessesList.get(position);
        detailIntent.putExtra(EXTRA_ID, clickItem.getId());
        detailIntent.putExtra(EXTRA_NAME, clickItem.getName());
        detailIntent.putExtra(EXTRA_LAT, String.valueOf(clickItem.getCoordinates().getLatitude()));
        detailIntent.putExtra(EXTRA_LNG, String.valueOf(clickItem.getCoordinates().getLongitude()));
        detailIntent.putExtra(EXTRA_LINK, clickItem.getUrl());
        Log.d("pass", String.valueOf(clickItem.getCoordinates().getLatitude()));
        startActivity(detailIntent);
    }

    private String partitionLocation(BusinessLocation loc) {
        String[] locationArray = loc.display_address.toArray(new String[0]);
        String displayAddress = "";
        for (int i = 0 ; i < locationArray.length; i++) {
            displayAddress += locationArray[i] + ",";
        }
        return displayAddress.substring(0, displayAddress.length() - 1);
    }
}