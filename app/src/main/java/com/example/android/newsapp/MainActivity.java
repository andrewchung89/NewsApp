package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import android.content.Loader;
import android.app.LoaderManager.LoaderCallbacks;
import android.widget.TextView;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class MainActivity extends AppCompatActivity
        implements LoaderCallbacks<List<News>> {

    private static final String LOG_TAG = MainActivity.class.getName();

    private static final String USGS_REQUEST_URL =
            "http://content.guardianapis.com/";

    private static final int NEWS_LOADER_ID = 1;
    private NewsAdapter nAdapter;
    private TextView nEmptyStateTextView;
    String orderBy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView newsListView = (ListView) findViewById(R.id.list);
        nEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(nEmptyStateTextView);
        nAdapter = new NewsAdapter(this, new ArrayList<News>());
        newsListView.setAdapter(nAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News currentNews = nAdapter.getItem(position);
                Uri newsUri = Uri.parse(currentNews.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }
        });

       ConnectivityManager cnnMgr = (ConnectivityManager)
               getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cnnMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        }else{
            nEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }



    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);


            orderBy  = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendPath("search");
        uriBuilder.appendQueryParameter("order-by", "newest");
        uriBuilder.appendQueryParameter("show-references", "author");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("q", "Android");
        uriBuilder.appendQueryParameter("api-key", "test");
        return new NewsLoader(this,  uriBuilder.toString());
    }
    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        nEmptyStateTextView.setText(R.string.no_news);

        nAdapter.clear();

        if (news != null && !news.isEmpty()) {
            nAdapter.addAll(news);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        nAdapter.clear();
    }

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendPath("search");
        uriBuilder.appendQueryParameter("order-by", orderBy);
        uriBuilder.appendQueryParameter("show-references", "author");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("q", "Android");
        uriBuilder.appendQueryParameter("api-key", "test");

        super.onResume();

    }
}



