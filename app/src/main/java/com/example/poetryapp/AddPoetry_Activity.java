package com.example.poetryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.poetryapp.Adapter.PoetryAdapter;
import com.example.poetryapp.Api.ApiClient;
import com.example.poetryapp.Api.ApiInterface;
import com.example.poetryapp.Response.DeletePoetryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPoetry_Activity extends AppCompatActivity {
    Toolbar toolbar;
    EditText PoetName, PoetryData;
    AppCompatButton submitBtn;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poetry);
        initialization();
        setUpToolbar();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String poetryDataStrig = PoetryData.getText().toString();
                String poetNameString = PoetName.getText().toString();
                if (poetryDataStrig.equals("")) {
                    PoetryData.setError("Field is Empty");
                } else {
                    if (poetNameString.equals("")) {
                        PoetName.setError("Field is Empty");
                    } else {
                        callApi(poetryDataStrig, poetNameString);
                        PoetryData.setText("");
                        PoetName.setText("");
                    }
                }

            }

        });

    }

    private void initialization() {
        toolbar = findViewById(R.id.add_Poetry_toolbar);
        PoetName = findViewById(R.id.add_PoetName_EditText);
        PoetryData = findViewById(R.id.add_Poetry_EditText);
        submitBtn = findViewById(R.id.add_Poetry_SubmitBtn);
        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void callApi(String poetryData, String poetName) {

        apiInterface.addPoetry(poetryData, poetName).enqueue(new Callback<DeletePoetryResponse>() {
            @Override
            public void onResponse(Call<DeletePoetryResponse> call, Response<DeletePoetryResponse> response) {
                try {
                    if (response.body().getStatus().equals("1")) {
                        Toast.makeText(AddPoetry_Activity.this, "Poetry Inserted Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddPoetry_Activity.this, "Poetry Not Inserted", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.getLocalizedMessage();
                }
            }

            @Override
            public void onFailure(Call<DeletePoetryResponse> call, Throwable t) {
                Log.e("Failure", t.getLocalizedMessage());
            }
        });
    }
}