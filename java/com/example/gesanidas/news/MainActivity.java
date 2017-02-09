package com.example.gesanidas.news;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.StrictMode;
import android.support.v4.content.AsyncTaskLoader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.gesanidas.news.data.NewsContract;
import com.example.gesanidas.news.data.NewsPreferences;
import com.example.gesanidas.news.sync.NewsSyncUtils;
import com.example.gesanidas.news.utilities.JsonUtils;
import com.example.gesanidas.news.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        NewsAdapter.NewsAdapterOnClickHandler
{
    TextView textView;
    private RecyclerView recyclerView;
    public static  NewsAdapter newsAdapter;
    private ProgressBar indicator;
    private static final int FORECAST_LOADER_ID = 44;
    public static final String[] NEWS_LIST={NewsContract.NewsEntry.COLUMN_TITLE, NewsContract.NewsEntry.COLUMN_DESCRIPTION, NewsContract.NewsEntry.COLUMN_URL};
    private int pos=RecyclerView.NO_POSITION;





    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            Intent intent=new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.textView);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview_news);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        newsAdapter=new NewsAdapter(this);
        recyclerView.setAdapter(newsAdapter);
        indicator = (ProgressBar) findViewById(R.id.indicator);
        int loaderId = FORECAST_LOADER_ID;
        getSupportLoaderManager().initLoader(loaderId, null, this);
        NewsSyncUtils.initialize(this);



    }



    private void showNewsDataView()
    {
        textView.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showLoading()
    {
        indicator.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(String news)
    {
        Intent intent=new Intent(this,DetailActivity.class);
        intent.putExtra(intent.EXTRA_TEXT,news);

        startActivity(intent);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {

        switch (id)
        {
            case FORECAST_LOADER_ID:

                Uri queryUri= NewsContract.NewsEntry.CONTENT_URI;
                return new CursorLoader(this,
                        queryUri,
                        NEWS_LIST,
                        null,
                        null,
                        null);
            default:throw new RuntimeException("Loader Not Implemented: " + id);

        }


    }




    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        newsAdapter.swapCursor(data);
        if(pos==RecyclerView.NO_POSITION)
        {
            pos=0;
        }
        recyclerView.smoothScrollToPosition(pos);
        if(data.getCount()!=0) showNewsDataView();


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        newsAdapter.swapCursor(null);
    }





}
