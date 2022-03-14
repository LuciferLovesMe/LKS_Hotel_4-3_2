package com.example.abim.lks_hotel4_mobile_3_2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<Cart> carts;
    Context ctx;

    public Adapter(List<Cart> carts, Context ctx) {
        this.carts = carts;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_data, viewGroup, false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Cart cart = carts.get(i);
        viewHolder.tv_price.setText(String.valueOf(cart.getTotalPrice()));
        viewHolder.tv_name.setText(String.valueOf(cart.getFdName()));
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_name, tv_price;
        Button btn;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);
            btn = itemView.findViewById(R.id.btn_del);

            btn.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            carts.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(), carts.size());
        }
    }
}
