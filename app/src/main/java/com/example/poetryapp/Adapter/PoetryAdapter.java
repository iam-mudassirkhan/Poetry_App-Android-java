package com.example.poetryapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poetryapp.Api.ApiClient;
import com.example.poetryapp.Api.ApiInterface;
import com.example.poetryapp.Models.PoetryModel;
import com.example.poetryapp.R;
import com.example.poetryapp.Response.DeletePoetryResponse;
import com.example.poetryapp.UpdatePoetry_Activity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PoetryAdapter extends RecyclerView.Adapter<PoetryAdapter.ViewHolder> {
Context context;
List<PoetryModel> poetryModels;
ApiInterface apiInterface;
    public PoetryAdapter(Context context, List<PoetryModel> poetryModels) {
        this.context = context;
        this.poetryModels = poetryModels;
        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.poetry_list_design, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.poetName.setText(poetryModels.get(position).getPoet_name());
        holder.poetryData.setText(poetryModels.get(position).getPoetry_data());
        holder.poetryDateTime.setText(poetryModels.get(position).getDate_time());

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePoetry(poetryModels.get(position).getId()+"", position);
            }
        });

        holder.update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                The Toast which i comented is showing the PoetryID and Poetry data in Toast
//                Toast.makeText(context, poetryModels.get(position).getId()+"\n"+poetryModels.get(position).getPoetry_data(), Toast.LENGTH_SHORT).show();
                Intent goToUpdate_Activity = new Intent(context, UpdatePoetry_Activity.class);
                goToUpdate_Activity.putExtra("Poetry_id", poetryModels.get(position).getId());
                goToUpdate_Activity.putExtra("Poetry_Data", poetryModels.get(position).getPoetry_data());
                goToUpdate_Activity.putExtra("Poet_Name", poetryModels.get(position).getPoet_name());
                context.startActivity(goToUpdate_Activity);
            }
        });

    }




    @Override
    public int getItemCount() {
        return poetryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView poetName, poetryData, poetryDateTime;
        AppCompatButton update_btn, delete_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            poetName = itemView.findViewById(R.id.poetName);
            poetryData = itemView.findViewById(R.id.poetryData_tv);
            poetryDateTime = itemView.findViewById(R.id.date_time_tv);
            update_btn = itemView.findViewById(R.id.update_btn);
            delete_btn = itemView.findViewById(R.id.delete_btn);
        }
    }

    private void deletePoetry(String id, int pose){

        apiInterface.deletePoetry(id).enqueue(new Callback<DeletePoetryResponse>() {
            @Override
            public void onResponse(Call<DeletePoetryResponse> call, Response<DeletePoetryResponse> response) {
try {
    if (response != null){
        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();

    }
    if (response.body().getStatus().equals("1")){
        poetryModels.remove(pose);
        notifyDataSetChanged();
    }

} catch (Exception e) {
    Log.e("Exp", e.getLocalizedMessage());
}
            }

            @Override
            public void onFailure(Call<DeletePoetryResponse> call, Throwable t) {
                Log.e("Failure", t.getLocalizedMessage());
            }
        });

    }
}

