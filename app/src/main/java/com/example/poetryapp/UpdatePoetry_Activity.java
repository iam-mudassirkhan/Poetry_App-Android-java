package com.example.poetryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.poetryapp.Api.ApiClient;
import com.example.poetryapp.Api.ApiInterface;
import com.example.poetryapp.Response.DeletePoetryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdatePoetry_Activity extends AppCompatActivity {
    Toolbar toolbar;
    EditText updatePoetry_edittext, updatePoetName;
    AppCompatButton updateSumbitBtn;

    int poetryID;
    String poetryDataString;
    String poet_NameString;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_poetry);
        initialization();
        setUpToobar();

        updateSumbitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatePoetryString = updatePoetry_edittext.getText().toString();
                String updatePoet_Name= updatePoetName.getText().toString();
                if (updatePoetryString.equals("")){
                    updatePoetry_edittext.setError("Field is Empty");
                }
                else {
                    if (updatePoet_Name.equals("")){
                        updatePoetName.setError("Field is Empty");
                    }
                    else {
                        callApi(updatePoetryString, poetryID+"");
                        Toast.makeText(UpdatePoetry_Activity.this, "Update Poetry API Called", Toast.LENGTH_SHORT).show();
                        updatePoetry_edittext.setText("");
                    }
                }

            }
        });
    }

    public void initialization() {
        toolbar = findViewById(R.id.update_Poetry_toolbar);
        updatePoetry_edittext = findViewById(R.id.update_Poetry_EditText);
        updateSumbitBtn = findViewById(R.id.update_Poetry_SubmitBtn);
        updatePoetName = findViewById(R.id.update_PoetName_EditText);

        poetryID = getIntent().getIntExtra("Poetry_id", 0);
        poetryDataString = getIntent().getStringExtra("Poetry_Data");
        poet_NameString = getIntent().getStringExtra("Poet_Name");
        updatePoetry_edittext.setText(poetryDataString);
        updatePoetName.setText(poet_NameString);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);



    }
    private void setUpToobar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void callApi(String P_Data, String P_Id){
        apiInterface.updatePoetry(P_Data, P_Id).enqueue(new Callback<DeletePoetryResponse>() {
            @Override
            public void onResponse(Call<DeletePoetryResponse> call, Response<DeletePoetryResponse> response) {

                try {
                    if (response.body().getStatus().equals("1")){
                        Toast.makeText(UpdatePoetry_Activity.this, "Poetry Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(UpdatePoetry_Activity.this, "Poetry Not Updated", Toast.LENGTH_SHORT).show();

                }
                catch (Exception e){
                    Log.e("exp", e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DeletePoetryResponse> call, Throwable t) {
                Log.e("Failure", t.getLocalizedMessage());

            }
        });
    }

}