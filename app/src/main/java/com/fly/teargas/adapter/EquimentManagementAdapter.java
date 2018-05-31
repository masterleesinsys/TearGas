package com.fly.teargas.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fly.teargas.R;
import com.fly.teargas.entity.DeviceInfo;

import java.util.List;


/**
 * 设备管理
 */
public class EquimentManagementAdapter extends RecyclerView.Adapter<EquimentManagementAdapter.MyViewHolder> {
    private Context context;
    private OnImgClickListener onImgClickListener;
    private OnViewClickListener onViewClickListener;
    private List<DeviceInfo> list = null;

    public EquimentManagementAdapter(Context context, List<DeviceInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_equiment_management, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_equipment_name.setText("设备" + list.get(position).getDeviceID());

        holder.tv_status.setText(list.get(position).getCurState());
        holder.tv_address.setText(list.get(position).getDescription());

        if (list.get(position).isbOnLine()) {
            holder.iv_equipment.setImageResource(R.drawable.ico_computer_active);
        } else {
            holder.iv_equipment.setImageResource(R.drawable.ico_computer);
        }

        if ("布防".equals(list.get(position).getCurState())) {
            holder.tv_is_normal.setText("正常");
            holder.tv_is_normal.setTextColor(ContextCompat.getColor(context, R.color.normal));

            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.normal));
        } else {
            holder.tv_is_normal.setText("异常");
            holder.tv_is_normal.setTextColor(ContextCompat.getColor(context, R.color.main_update_color));
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.main_update_color));
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_equipment;
        private TextView tv_equipment_name, tv_address, tv_is_normal, tv_status;
        private RelativeLayout rl_view;

        MyViewHolder(View itemView) {
            super(itemView);

            rl_view = itemView.findViewById(R.id.rl_view);
            iv_equipment = itemView.findViewById(R.id.iv_equipment);
            tv_equipment_name = itemView.findViewById(R.id.tv_equipment_name);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_is_normal = itemView.findViewById(R.id.tv_is_normal);
            tv_status = itemView.findViewById(R.id.tv_status);

            iv_equipment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onImgClickListener.onImgClickListener(getAdapterPosition());
                }
            });

            rl_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onViewClickListener.onViewClickListener(getAdapterPosition(), list.get(getAdapterPosition()).getDeviceID());
                }
            });
        }
    }

    public interface OnImgClickListener {
        void onImgClickListener(int position);
    }

    public interface OnViewClickListener {
        void onViewClickListener(int position, String deviceID);
    }

    public void setOnImgClickListener(OnImgClickListener onImgClickListener) {
        this.onImgClickListener = onImgClickListener;
    }

    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }
}
