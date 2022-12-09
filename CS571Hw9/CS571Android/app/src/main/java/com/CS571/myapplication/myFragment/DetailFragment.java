package com.CS571.myapplication.myFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.CS571.myapplication.R;
import com.CS571.myapplication.ViewPagerAdapter;
import com.CS571.myapplication.databinding.FragmentDetailBinding;
import com.CS571.myapplication.databinding.ReservationFormBinding;
import com.CS571.myapplication.model.detailYelp.detailCategory;
import com.CS571.myapplication.model.detailYelp.detailResponse;
import com.CS571.myapplication.network.detailYelpApi;
import com.CS571.myapplication.network.detailYelpRetrofitClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    // Business detail info
    public String detailId;
    public String detailName;
    public String detailAddress;
    public String detailPrice;
    public String detailPhoneNumber;
    public boolean detailStatus;
    public String detailLink;
    public String detailCategory;
    public ArrayList<String> detailPhotos;
    // Binding
    FragmentDetailBinding binding;
    // Dialog Builder
    AlertDialog.Builder builder;
    // Calender in Date picker
    Calendar dateCalendar;
    int currentDay;
    int currentMonth;
    int currentYear;
    // Calender in Time picker
    Calendar calendar;
    int currentMinute;
    int currentHour;
    String amPm;
    // input text in the reservation form
    EditText emailInput;
    EditText dateInput;
    EditText timeInput;
    // basic information of the booked reservation.
    String reservationName;
    String reservationEmail;
    String reservationDate;
    String reservationTime;
    SharedPreferences preferences;
    // View page adapter
    ViewPagerAdapter displayPicPagerAdapter;
    List<Fragment> displayPicList;

    public DetailFragment(String detailId) {
        this.detailId = detailId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        // Retrofit call to get business detail information
        businessDetailRequest(detailId);
        // Local Storage
//        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        preferences = getContext().getSharedPreferences("cs571", 0);
        // create the alertDialog
        builder = new AlertDialog.Builder(getContext());
        binding.reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // construct the reservation form;
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View reservationView = inflater.inflate(R.layout.reservation_form, null);
                emailInput = reservationView.findViewById(R.id.reserveEmailInput);
                dateInput = reservationView.findViewById(R.id.reserveDateInput);
                timeInput = reservationView.findViewById(R.id.reserveTimeInput);
                TextView businessName = reservationView.findViewById(R.id.reservationName);
                businessName.setText(detailName);
                // create the time picker once the user click on the reserveDateInput edit text:
                dateInput.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePickerDialog();
                    }
                });

                // create the time picker once the user click on the reserveTimeInput edit text:
                timeInput.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calendar = Calendar.getInstance();
                        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                        currentMinute = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                                    if (hourOfDay >= 12) {
                                         amPm = "PM";
                                    } else {
                                        amPm = "AM";
                                    }
                                    timeInput.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                               }
                            }, currentHour, currentMinute, false);
                        timePickerDialog.show();
                    }
                });

                // construct the reserve now button
                builder.setView(reservationView);
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Reservation cancelled",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("showEmail", emailInput.getText().toString());
                        if (!checkEmail(emailInput.getText().toString())) {
                            Toast.makeText(getContext(), "Invalid Email Address",Toast.LENGTH_SHORT).show();
                        } else if (!checkTime(timeInput.getText().toString())) {
                            Toast.makeText(getContext(), "Time Should be between 10AM AND 5PM",Toast.LENGTH_SHORT).show();
                        } else {
                            // All the three input texts are validate.
                            reservationName = detailName;
                            reservationEmail = emailInput.getText().toString();
                            reservationDate = dateInput.getText().toString();
                            reservationTime = timeInput.getText().toString();
                            SharedPreferences.Editor editor = preferences.edit();
                            String str = reservationName + ';' + reservationDate
                                    + ';' + reservationTime + ';' + reservationEmail + ';' + detailId;
                            editor.putString(detailId, str);
                            editor.apply();
                            Toast.makeText(getContext(), "Reservation Booked",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                AlertDialog dialog =  builder.create();
                dialog.show();
            }
        });
        return binding.getRoot();

    }

    // date picker dialog
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
    // implement onDateSet
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = month + "/" + dayOfMonth + "/" + year;
        dateInput.setText(date);
    }

    // send yelpRequest for business Detail
    private void businessDetailRequest(String businessId) {
        detailYelpApi api = detailYelpRetrofitClient.newInstance().create(detailYelpApi.class);
        api.sendDetailYelpRequest(businessId).enqueue(new Callback<detailResponse>() {
            @Override
            public void onResponse(Call<detailResponse> call, Response<detailResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("newPage", response.body().toString());
                    Log.d("newPage", "get detail response");
                    detailResponse detail = response.body();
                    detailName = detail.name;
                    detailPhoneNumber = detail.display_phone;
                    detailPrice = detail.price;
                    detailStatus = detail.hours.get(0).is_open_now;
                    detailAddress = partitionAddress(detail.location.display_address);
                    detailCategory = partitionCategories(detail.categories);
                    detailLink = detail.url;
                    detailPhotos = detail.photos;
                    createBusinessDetailPage();
                    // create the business picture slider
                    displayPicList = new ArrayList<>();
                    displayPicList.add(displayImage1.newInstance(detailPhotos.get(0)));
                    displayPicList.add(displayImage2.newInstance(detailPhotos.get(1)));
                    displayPicList.add(displayImage3.newInstance(detailPhotos.get(2)));
                    displayPicPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), displayPicList);
                    binding.detailViewPager.setAdapter(displayPicPagerAdapter);
                }
            }
            @Override
            public void onFailure(Call<detailResponse> call, Throwable t) {
                Log.d("newPage", t.toString());
            }
        });
    }
    // get the display_address
    private String partitionAddress(ArrayList<String> display_address) {
        String displayAddress = "";
        for (int i = 0; i < display_address.size(); i++) {
            displayAddress += display_address.get(i) + ",";
        }
        return displayAddress.substring(0, displayAddress.length() - 1);
    }
    // get the display_category
    public String partitionCategories(ArrayList<detailCategory> category) {
        String displayCategory = "";
        for (int i = 0; i < category.size(); i++) {
            displayCategory += category.get(i).title + " | ";
        }
        return displayCategory.substring(0, displayCategory.length() - 3);
    }
    // create xml page for the business detail
    public void createBusinessDetailPage() {
        TextView textViewAddress = binding.displayAddress;
        TextView textViewPrice = binding.displayPrice;
        TextView textViewPhoneNumber = binding.displayPhoneNumber;
        TextView textViewCategory = binding.displayCategory;
        TextView textViewLink = binding.displayLink;
        textViewAddress.setText(detailAddress);
        textViewPrice.setText(detailPrice);
        textViewPhoneNumber.setText(detailPhoneNumber);
        textViewCategory.setText(detailCategory);
        // create the clickable web link
        textViewLink.setClickable(true);
        textViewLink.setMovementMethod(LinkMovementMethod.getInstance());
        String text = String.format("<a href= %s > BusinessLink </a>", detailLink);
        textViewLink.setText(Html.fromHtml(text));
        textViewLink.setMovementMethod(LinkMovementMethod.getInstance());
        // Set the status of the current Business property.
        TextView textViewStatus = binding.displayStatus;
        if (detailStatus) {
            textViewStatus.setText("Open Now");
            textViewStatus.setTextColor(Color.parseColor("#5bff35"));
        } else {
            textViewStatus.setText("Closed");
            textViewStatus.setTextColor(Color.parseColor("#ff3535"));
        }
    }
    // email validation
    public boolean checkEmail(String email) {
        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }
        return false;
    }
    // date validation
    public boolean checkDate(String date) {
        return false;
    }
    // time validation
    public boolean checkTime(String time) {
        // 20:48PM
        if (time == null || time.length() == 0 || time.length() <=5) {
            return false;
        }
        int hour;
        int minute;
        hour = (time.charAt(0) - '0') * 10 + (time.charAt(1) - '0');
        minute = (int)time.charAt(3) * 10 + (int)time.charAt(4);
        Log.d("scheduleTime", String.valueOf(hour) + '/' +String.valueOf(minute));
        if (hour >= 10 && hour <= 17) {
            return true;
        }
        return false;
    }
}