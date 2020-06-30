package com.lollipop.uprint.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lollipop.uprint.Config;
import com.lollipop.uprint.R;
import com.lollipop.uprint.adapter.HistoryAdapter;
import com.lollipop.uprint.model.DataHistory;
import com.lollipop.uprint.model.ListDataHistory;
import com.lollipop.uprint.utils.BaseApiService;
import com.lollipop.uprint.utils.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Config session;
    Context mContext;

    HistoryAdapter mAdapter;
    RecyclerView mRecyclerView;

    private List<DataHistory> dataHistoryList;
    FloatingActionButton btnAdd;

    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mApiService = UtilsApi.getAPIService();

        session = new Config(getApplicationContext());
        session.checkLogin();

        mRecyclerView = findViewById(R.id.recyle_view);
        showList();
    }

    private void showList() {
        mApiService.dataHistory("{\n" +
                "\"case\":\"show_history\"\n" +
                "}").enqueue(new Callback<ListDataHistory>() {
            @Override
            public void onResponse(Call<ListDataHistory> call, Response<ListDataHistory> response) {
                if(response.isSuccessful()){
                    try{
                        String status = response.body().getStatus();
                        if(status.equals("404")){
                            Toast.makeText(mContext, "Tidak ada data", Toast.LENGTH_SHORT).show();
                        }else{
                            generateDataList(response.body().getDataList());
                        }
                    }catch (Exception e){
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                }
            }

            @Override
            public void onFailure(Call<ListDataHistory> call, Throwable t) {

            }
        });
    }

    private void generateDataList(ArrayList<DataHistory> dataList) {
        mAdapter = new HistoryAdapter(mContext, dataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }
}
