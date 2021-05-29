package com.ridamjain.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.ridamjain.newsapp.api.APIClient;
import com.ridamjain.newsapp.models.Article;
import com.ridamjain.newsapp.models.News;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String API_KEY = "74ee508176be435d90e2e567b1298c51";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<Article> articles=new ArrayList<>();
        private Adapter adapter;
        private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        onLoading();
    }
    public void loadGson()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        Call<News> call= APIClient.getInstance().getApi().getNews(Utils.getCountry(),API_KEY);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticle()!=null){
                    articles.clear();
                    articles=response.body().getArticle();

                    adapter =new Adapter(MainActivity.this, articles);
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }

        });
    }

    @Override
    public void onRefresh() {
        loadGson();
    }
    private void onLoading()
    {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadGson();
            }
        });
    }
}