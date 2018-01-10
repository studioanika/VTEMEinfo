package com.example.root.mpolispager.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.root.mpolispager.MainActivity;
import com.example.root.mpolispager.R;
import com.example.root.mpolispager.model.Category;

import java.util.List;

/**
 * Created by root on 28.12.17.
 */


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryAdapterViewHolder> {
    public static final String TAG = CategoryAdapter.class.getSimpleName();

    MainActivity activity;
    List<Category> list;
    public CategoryAdapter(List<Category> _list, Context context) {
        this.list = _list;
        activity = (MainActivity) context;
    }


    @Override
    public CategoryAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryAdapterViewHolder holder, final int position) {
        String title = list.get(position).getName();
        holder.title.setText(title);
        Glide.with(holder.img.getContext()).load(list.get(position).getTitle()).into(holder.img);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startListCompany();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    static class CategoryAdapterViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView img;
        CardView cardView;

        public CategoryAdapterViewHolder(View view){
            super(view);
            this.title = (TextView) view.findViewById(R.id.textViewShops);
            this.img = (ImageView) view.findViewById(R.id.imageViewShops);
            this.cardView = (CardView) view.findViewById(R.id.category_card);
        }
    }
}
