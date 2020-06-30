package com.lollipop.uprint.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.lollipop.uprint.R;
import com.lollipop.uprint.model.DataHistory;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<DataHistory> dataList;

//    public interface OnContactClickListener {
//        void onItemClick(DataContact dataContact);
//    }

//    private final OnContactClickListener listener;

    public HistoryAdapter(Context context, ArrayList<DataHistory> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_history, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtFilename.setText(dataList.get(position).getFilename());
        holder.txtStatus.setText(dataList.get(position).getStatus());
        holder.txtDate.setText(dataList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtFilename,txtDate,txtStatus;

        MyViewHolder(View itemView) {
            super(itemView);
            txtFilename = itemView.findViewById(R.id.txtFilename);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtStatus = itemView.findViewById(R.id.txtStatus);
        }
    }

}
