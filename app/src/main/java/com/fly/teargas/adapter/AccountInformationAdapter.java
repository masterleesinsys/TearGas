package com.fly.teargas.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fly.teargas.R;


/**
 * 查看所有账户
 */
public class AccountInformationAdapter extends RecyclerView.Adapter<AccountInformationAdapter.MyViewHolder> {
    private Context context;
    private OnItemClickListener onItemClickListener;

    public AccountInformationAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_account_information, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        switch (position) {
            case 2:
            case 3:
            case 5:
                holder.tv_is_activation.setText("冻结中");
                holder.tv_is_activation.setTextColor(ContextCompat.getColor(context, R.color.abnormal));
                break;
        }
    }

    @Override
    public int getItemCount() {
//        return list == null ? 0 : list.size();
        return 10;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_equipment;
        private TextView tv_equipment_name, tv_identity, tv_is_activation, tv_numberOfDevices;

        MyViewHolder(View itemView) {
            super(itemView);

            iv_equipment = itemView.findViewById(R.id.iv_equipment);
            tv_equipment_name = itemView.findViewById(R.id.tv_equipment_name);
            tv_identity = itemView.findViewById(R.id.tv_identity);
            tv_is_activation = itemView.findViewById(R.id.tv_is_activation);
            tv_numberOfDevices = itemView.findViewById(R.id.tv_numberOfDevices);

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
