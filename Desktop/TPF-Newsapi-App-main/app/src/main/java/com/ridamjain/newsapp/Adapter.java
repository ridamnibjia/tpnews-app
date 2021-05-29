package com.ridamjain.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ridamjain.newsapp.models.Article;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    List<Article> articles;
    public Adapter(Context context,List<Article>articles)
    {
        this.context = context;
        this.articles = articles;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Article art = articles.get(position);
        String url = art.getUrl();
        holder.title.setText(art.getTitle());
        holder.desc.setText(art.getDescription());
        holder.author.setText(art.getAuthor());
        holder.published.setText(art.getPublishedAt());
        holder.source.setText(art.getSource().getName());
        Glide.with(holder.itemView.getContext())
                .load(articles.get(position).getUrlToImage()).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,DetailNews.class);
                intent.putExtra("title",art.getTitle());
                intent.putExtra("source",art.getSource().getName());
                intent.putExtra("time",art.getPublishedAt());
                intent.putExtra("imageUrl",art.getUrlToImage());
                intent.putExtra("url1",art.getUrl());
                intent.putExtra("decs",art.getDescription());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,desc,author,published,source;
        ImageView imageView;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.title);
            desc =  itemView.findViewById(R.id.desc);
            author =  itemView.findViewById(R.id.author);
            published =  itemView.findViewById(R.id.publishedAt);
            source=  itemView.findViewById(R.id.source);
            imageView =  itemView.findViewById(R.id.img);
            cardView =  itemView.findViewById(R.id.card);
        }
    }
}
