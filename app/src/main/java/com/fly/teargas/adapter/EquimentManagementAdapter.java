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
import com.fly.teargas.entity.DeviceInfo;

import java.util.List;


/**
 * 设备管理
 */
public class EquimentManagementAdapter extends RecyclerView.Adapter<EquimentManagementAdapter.MyViewHolder> {
    private Context context;
    private OnItemClickListener onItemClickListener;
    private Integer type = 0;     //0全部  1正常  2异常
    private List<DeviceInfo> list = null;

    public EquimentManagementAdapter(Context context, Integer type, List<DeviceInfo> list) {
        this.context = context;
        this.type = type;
        this.list = list;
    }

    public EquimentManagementAdapter(Context context, Integer type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_equiment_management, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_is_normal.setText("异常");
        holder.tv_is_normal.setTextColor(ContextCompat.getColor(context, R.color.abnormal));
        holder.tv_status.setText("已撤防");
        holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.abnormal));

//        switch (type) {
//            case 0:
//                switch (position) {
//                    case 2:
//                    case 3:
//                    case 5:
//                        holder.tv_is_normal.setText("异常");
//                        holder.tv_is_normal.setTextColor(ContextCompat.getColor(context, R.color.abnormal));
//                        holder.tv_status.setText("已撤防");
//                        holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.abnormal));
//                        break;
//                }
//                break;
//            case 1:
//                break;
//            case 2:
//                holder.tv_is_normal.setText("异常");
//                holder.tv_is_normal.setTextColor(ContextCompat.getColor(context, R.color.abnormal));
//                holder.tv_status.setText("已撤防");
//                holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.abnormal));
//                break;
//        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_equipment;
        private TextView tv_equipment_name, tv_address, tv_is_normal, tv_status;

        MyViewHolder(View itemView) {
            super(itemView);

            iv_equipment = itemView.findViewById(R.id.iv_equipment);
            tv_equipment_name = itemView.findViewById(R.id.tv_equipment_name);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_is_normal = itemView.findViewById(R.id.tv_is_normal);
            tv_status = itemView.findViewById(R.id.tv_status);

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
