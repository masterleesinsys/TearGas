package com.fly.teargas.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.entity.RecordInfo;

import java.util.List;


/**
 * 日志信息
 */
public class LogInformationAdapter extends RecyclerView.Adapter<LogInformationAdapter.MyViewHolder> {
    private Context context;
    private List<RecordInfo> list;

    public LogInformationAdapter(Context context, List<RecordInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_log_information, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_status.setText(list.get(position).getText());
        holder.tv_date.setText(list.get(position).getTime());
        holder.tv_operator.setText("操作员：" + MyApplication.getUserName());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_status, tv_date, tv_operator;

        MyViewHolder(View itemView) {
            super(itemView);

            tv_status = itemView.findViewById(R.id.tv_status);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_operator = itemView.findViewById(R.id.tv_operator);
        }
    }
}
