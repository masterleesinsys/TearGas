package com.fly.teargas.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fly.teargas.R;
import com.fly.teargas.entity.UserInfo;

import java.util.List;


/**
 * 查看所有账户
 */
public class AccountInformationAdapter extends RecyclerView.Adapter<AccountInformationAdapter.MyViewHolder> {
    private Context context;
    private OnItemClickListener onItemClickListener;
    private List<UserInfo> list;

    public AccountInformationAdapter(Context context, List<UserInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_account_information, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_equipment_name.setText(list.get(position).getName());
        holder.tv_identity.setText(list.get(position).getQx());
        holder.tv_numberOfDevices.setText("管辖设备数：" + list.get(position).getTel());

//        holder.tv_is_activation.setText("冻结中");
//        holder.tv_is_activation.setTextColor(ContextCompat.getColor(context, R.color.abnormal));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
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
