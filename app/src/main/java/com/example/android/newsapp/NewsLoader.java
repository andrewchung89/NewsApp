package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;


public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String nUrl;
    public NewsLoader(Context context, String url) {
        super(context);
        nUrl = url;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (nUrl == null) {
            return null;
        }
        List<News> news = QueryUtils.fetchNewsData(nUrl);
        return news;
    }
}