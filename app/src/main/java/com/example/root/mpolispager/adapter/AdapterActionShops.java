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
import com.example.root.mpolispager.model.InfoActionShop;

import java.util.ArrayList;

/**
 * Created by root on 3.1.18.
 */

public class AdapterActionShops extends BaseAdapter {

    private Context context;
    private ArrayList<InfoActionShop> products;
    LayoutInflater lInflater;


    public AdapterActionShops(Activity context, ArrayList<InfoActionShop> products){
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
        TextView term;
        TextView price;
        TextView skidka;
        TextView weight;

    }


    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = lInflater.inflate(R.layout.item_card_info_shops, null, true);
        final Holder holder = new Holder();

        holder.img = (ImageView) v.findViewById(R.id.imageViewInfoSops);
        holder.text = (TextView) v.findViewById(R.id.textViewTitleInfoShops);
        holder.term = (TextView) v.findViewById(R.id.textViewTermInfoShops);
        holder.price = (TextView) v.findViewById(R.id.textViewPriceInfoShops);
        holder.skidka = (TextView) v.findViewById(R.id.textViewActionInfoShops);
        holder.weight = (TextView) v.findViewById(R.id.textViewWeight);

        //Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/myfonts.ttf");
        //holder.text.setTypeface(type);

        holder.text.setText(products.get(position).getName());
        holder.weight.setText("");
        holder.term.setText(products.get(position).getTerm());
        holder.price.setText(products.get(position).getPrice());
        holder.skidka.setText(products.get(position).getSkidka());


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