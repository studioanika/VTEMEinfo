package com.example.root.mpolispager.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dinuscxj.ellipsize.EllipsizeTextView;
import com.example.root.mpolispager.MainActivity;
import com.example.root.mpolispager.R;
import com.example.root.mpolispager.fragments.FragmentAfisha;
import com.example.root.mpolispager.model.Category;
import com.example.root.mpolispager.model.News;
import com.example.root.mpolispager.model.Shop;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 3.1.18.
 */

public class NewsAdapter extends  RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    public List<News> list = new ArrayList<>();
    Context activity;
    int type;

    // type  0 - новости
    //       1 - афиша


    public NewsAdapter(List<News> list, Context activity, int type) {
        this.list = list;
        this.activity = activity;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_news_1, parent, false);

        //ViewHolder vh = new ViewHolder(v);
        return new NewsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final News news = list.get(position);

        //if(news.getType()!= 0){

            holder.tvDate1.setText(news.getDate());
            holder.rel2.setVisibility(View.GONE);

            holder.tvTitle1.setText(news.getTitle());
            Glide.with(holder.img1.getContext()).load(news.getImg()).into(holder.img1);

            holder.descr.setGravity(Gravity.RIGHT);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity act = (MainActivity) activity;
                    if(type==1){

                        act.startAfisha(news.getHref(), "");
                    }else {
                        act.startNews(news.getHref(), news.getTitle());
                    }
                }
            });
//

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //List<TypePA> list;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит только из одного TextView
        TextView tvDate1, tvDate2;
        TextView tvTitle2;
        EllipsizeTextView tvTitle1;
        ImageView img1, img2;
        TextView descr;
        RelativeLayout rel1, rel2;
        public ProgressBar progressBar;
        public TextView description;
        CardView cardView;

        public ViewHolder(View v) {
            super(v);

            tvDate1 = (TextView) v.findViewById(R.id.card1_title);
            tvDate2 = (TextView) v.findViewById(R.id.card2_title);

            tvTitle1 = (EllipsizeTextView) v.findViewById(R.id.card1_text);
            tvTitle2 = (TextView) v.findViewById(R.id.card2_text);

            img1 = (ImageView) v.findViewById(R.id.car1_img);
            img2 = (ImageView) v.findViewById(R.id.card2_img);

            descr = (TextView) v.findViewById(R.id.card1_descr);

            rel1 = (RelativeLayout) v.findViewById(R.id.news_rel1);
            rel2 = (RelativeLayout) v.findViewById(R.id.news_rel2);

            progressBar = (ProgressBar) v.findViewById(R.id.card1_progress);
            description = (TextView) v.findViewById(R.id.card1_description);

            cardView = (CardView) v.findViewById(R.id.news_card);

        }
    }


}