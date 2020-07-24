package com.lollipop.uprint.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    Config session;
    Context mContext;

    HistoryAdapter mAdapter;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<DataHistory> dataHistoryList;
    FloatingActionButton btnAdd, btnLogout;

    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mApiService = UtilsApi.getAPIService();

        session = new Config(getApplicationContext());
        session.checkLogin();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLay);
        mRecyclerView = findViewById(R.id.recyle_view);
        btnLogout = findViewById(R.id.btnLogout);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,PengajuanActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();
            }
        });

        showList();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                // Fetching data from server
                showList();
            }
        });

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
                            mSwipeRefreshLayout.setRefreshing(false);
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
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public void onRefresh() {
        showList();
    }
}
