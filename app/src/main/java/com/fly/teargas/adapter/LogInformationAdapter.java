package com.fly.teargas.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fly.teargas.R;


/**
 * 日志信息
 */
public class LogInformationAdapter extends RecyclerView.Adapter<LogInformationAdapter.MyViewHolder> {
    private Context context;
    private OnItemClickListener onItemClickListener;

    public LogInformationAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_log_information, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
//        return list == null ? 0 : list.size();
        return 10;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_status, tv_date,  tv_operator;

        MyViewHolder(View itemView) {
            super(itemView);

            tv_status = itemView.findViewById(R.id.tv_status);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_operator = itemView.findViewById(R.id.tv_operator);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClickListener(getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
