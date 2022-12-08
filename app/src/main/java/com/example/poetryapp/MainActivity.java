package com.example.poetryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.poetryapp.Adapter.PoetryAdapter;
import com.example.poetryapp.Api.ApiClient;
import com.example.poetryapp.Api.ApiInterface;
import com.example.poetryapp.Models.PoetryModel;
import com.example.poetryapp.Response.readPoetryResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ApiInterface apiInterface;
    PoetryAdapter poetryAdapter;
    Toolbar toolbar;

//    List<PoetryModel> poetryModelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        poetryModelList.add(new PoetryModel(1,"Allam Iqbal","phool ki patti se kat sakta hai heeray ka jigar\n" +
//                "\n" +
//                "mard e nadan per kalam narm o nazik by asar","09 Nov 2022"));
//
//        poetryModelList.add(new PoetryModel(1,"Mirza Ghalib","nahi hai na-umed iqbal apni kasht e veeran say\n" +
//                "\n" +
//                "zara num ho tu ye miti bari zarkhez ha saqi\n" +
//                "\n","09 Nov 2022"));
//
//        poetryModelList.add(new PoetryModel(1,"Mudassir Khan","tu raaz e kun fikan hai apni ankhon par ayan ho ja\n" +
//                "\n" +
//                "khudi ka raazdan ho ja khuda ka tarjman ho ja","09 Nov 2022"));
        initialization();
        getdata();
        setSupportActionBar(toolbar);

    }

    private void initialization() {
        recyclerView = findViewById(R.id.poetry_recyclerView);
        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
        toolbar = findViewById(R.id.main_toolBar);
    }

    private void setPoetryAdapter(List<PoetryModel> poetryModels) {
        poetryAdapter = new PoetryAdapter(this, poetryModels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(poetryAdapter);

    }

    private void getdata() {
        apiInterface.getPoetry().enqueue(new Callback<readPoetryResponse>() {
            @Override
            public void onResponse(Call<readPoetryResponse> call, Response<readPoetryResponse> response) {
                try {
                    if (response != null) {
                        if (response.body().getStatus().equals("1")) {
                            setPoetryAdapter(response.body().getData());
                        } else {
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    Log.e("exp", e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<readPoetryResponse> call, Throwable t) {
                Log.e("Failure", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_Poetry:
                Intent moveToAddPoetry_Activity = new Intent(MainActivity.this, AddPoetry_Activity.class);
                startActivity(moveToAddPoetry_Activity);
                Toast.makeText(this, "Add Poetry is Clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}