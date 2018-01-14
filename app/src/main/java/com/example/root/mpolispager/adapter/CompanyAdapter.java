package com.example.root.mpolispager.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dinuscxj.ellipsize.EllipsizeTextView;
import com.example.root.mpolispager.ListCompanyActivity;
import com.example.root.mpolispager.R;
import com.example.root.mpolispager.model.Company;

import java.util.List;

/**
 * Created by root on 10.1.18.
 */


public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyAdapterViewHolder> {
    public static final String TAG = CategoryAdapter.class.getSimpleName();

    ListCompanyActivity activity;
    List<Company> list;
    public CompanyAdapter(List<Company> _list, Context context) {
        this.list = _list;
        activity = (ListCompanyActivity) context;
    }


    @Override
    public CompanyAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_company, parent, false);
        return new CompanyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CompanyAdapterViewHolder holder, final int position) {

        final Company company = list.get(position);

        if(!company.getCity().isEmpty())holder.city.setText(company.getCity());
        if(!company.getCat_name().isEmpty())holder.cat_name.setText(company.getCat_name());
        if(!company.getTitle().isEmpty())holder.name.setText(company.getTitle());
        if(!company.getTime().isEmpty()) {
            holder.time.setText(company.getTime());
            holder.time_img.setVisibility(View.VISIBLE);
        }
        if(!company.getPhone().isEmpty()) holder.phone.setText(company.getPhone());

        if(!company.getIcon().isEmpty())Glide.with(holder.icon.getContext()).load(company.getIcon()).into(holder.icon);
        Glide.with(holder.cat_img.getContext()).load(company.getCat_img()).into(holder.cat_img);

        if(!company.getDescription().isEmpty()) holder.descr.setText(company.getDescription());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startInfo(company);
            }
        });

        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!company.getPhone().isEmpty()){
                    activity.call(company.getPhone());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    static class CompanyAdapterViewHolder extends RecyclerView.ViewHolder{
        private TextView city, cat_name, name, time, phone;
        private ImageView icon,cat_img, time_img;
        CardView cardView;
        EllipsizeTextView descr;

        public CompanyAdapterViewHolder(View view){
            super(view);
            this.cardView = (CardView) view.findViewById(R.id.company_card);
            this.city = (TextView) view.findViewById(R.id.company_city);
            this.cat_name = (TextView) view.findViewById(R.id.company_cat_name);
            this.name = (TextView) view.findViewById(R.id.company_name);
            this.time = (TextView) view.findViewById(R.id.company_time);
            this.phone = (TextView) view.findViewById(R.id.company_phone);
            this.icon = (ImageView) view.findViewById(R.id.company_icon);
            this.cat_img = (ImageView) view.findViewById(R.id.company_cat_img);
            this.time_img = (ImageView) view.findViewById(R.id.company_time_img);
            this.descr = (EllipsizeTextView) view.findViewById(R.id.company_descr);
        }
    }
}
