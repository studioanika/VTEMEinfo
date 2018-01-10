package com.example.root.mpolispager.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.root.mpolispager.R;
import com.example.root.mpolispager.model.Shop;

import java.util.ArrayList;

/**
 * Created by root on 2.1.18.
 */

public class ShopsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Shop> products;
    LayoutInflater lInflater;


    public ShopsAdapter(Context context, ArrayList<Shop> products){
        this.context = context;
        this.products = products;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class Holder{
        ImageView img;
        TextView text;

    }


    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = lInflater.inflate(R.layout.item_card_shops, null, true);
        final Holder holder = new Holder();

        holder.text = (TextView) v.findViewById(R.id.textViewShops);
        holder.text.setText(products.get(position).getName());
        holder.img = (ImageView) v.findViewById(R.id.imageViewShops);

        try {
            Glide.with(context)
                    .load(products.get(position).getImg())
                    .into(holder.img);
        } catch (Exception e) {
            e.printStackTrace();

        }


        return v;
    }
}