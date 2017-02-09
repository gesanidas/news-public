package com.example.gesanidas.news;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gesanidas.news.data.NewsContract;



import java.io.InputStream;
import java.net.URL;

public class DetailActivity extends AppCompatActivity
{
    private String news,desc,img,url;
    Cursor cursor;
    QueryTask q;


    TextView textView,textView3;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        textView=(TextView)findViewById(R.id.textView2);
        //textView3=(TextView) findViewById(R.id.textView3);
        imageView=(ImageView)findViewById(R.id.imageView);
        news=getIntent().getStringExtra(Intent.EXTRA_TEXT);
        q=new QueryTask();
        q.execute();
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });






    }


    private Intent createShareForecastIntent()
    {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(url.toString())
                .getIntent();
        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createShareForecastIntent());
        return true;
    }




    private class QueryTask extends AsyncTask<Void,Void,Void>
    {
        private Bitmap bmp;


        @Override
        protected Void doInBackground(Void... voids)
        {
            Uri newsQueryUri= NewsContract.NewsEntry.CONTENT_URI;
            String[] projectionColumns={NewsContract.NewsEntry.COLUMN_DESCRIPTION, NewsContract.NewsEntry.COLUMN_IMAGE, NewsContract.NewsEntry.COLUMN_URL};
            String selectionStatement= NewsContract.NewsEntry.COLUMN_TITLE+ " = ?";
            String[] selectionArgs=new String[1];
            selectionArgs[0]=news;


            cursor=getApplicationContext().getContentResolver().query(newsQueryUri,projectionColumns,selectionStatement,selectionArgs,null);

            if (null != cursor )
            {
                cursor.moveToFirst();
                desc=cursor.getString(0);
                img=cursor.getString(1);
                url=cursor.getString(2);


            }
            try
            {

                URL ur = new URL(img);
                InputStream is = ur.openConnection().getInputStream();

                bmp = BitmapFactory.decodeStream(is);
            }
            catch (Exception e)
            {
            }



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            textView.setText(desc);
            imageView.setImageBitmap(bmp);
            cursor.close();



        }
    }











}
