package com.example.gesanidas.news.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.gesanidas.news.MainActivity;
import com.example.gesanidas.news.data.NewsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gesanidas on 12/17/2016.
 */

public final class JsonUtils
{
    public static String[] parseJson(Context context,String inputJsonStr) throws JSONException
    {

        final  String AUTHOR="author";
        final  String TITLE="title";
        final  String DESCRIPTION="description";
        final  String URL="url";
        final String IMAGE="urlToImage";
        final String PUBLISH_DATE="publishedAt";

        String[] parsedData=new String[5];
        JSONObject json = new JSONObject(inputJsonStr);
        JSONArray data=json.getJSONArray("articles");
        for (int i=0;i<5;i++)
        {
            String author,title,description,url,image;
            JSONObject article = data.getJSONObject(i);
            author=article.getString(AUTHOR);
            title=article.getString(TITLE);
            description=article.getString(DESCRIPTION);
            url=article.getString(URL);
            image=article.getString(IMAGE);
            Log.i("tg","writting");
            parsedData[i]=title+description+url+image;

        }
        return parsedData;




    }

    public static ContentValues[] getContentValues(Context context,String inputJsonStr) throws JSONException
    {
        final  String AUTHOR="author";
        final  String TITLE="title";
        final  String DESCRIPTION="description";
        final  String URL="url";
        final String IMAGE="urlToImage";
        final String PUBLISH_DATE="publishedAt";

        JSONObject json = new JSONObject(inputJsonStr);
        JSONArray data=json.getJSONArray("articles");

        ContentValues[] contentNewsValues=new ContentValues[data.length()];


        for (int i=0;i<data.length();i++)
        {
            String author,title,description,url,image;
            JSONObject article = data.getJSONObject(i);
            author=article.getString(AUTHOR);
            title=article.getString(TITLE);
            description=article.getString(DESCRIPTION);
            url=article.getString(URL);
            image=article.getString(IMAGE);
            ContentValues contentValues = new ContentValues();

            Log.i("tg","writting");
            contentValues.put(NewsContract.NewsEntry.COLUMN_AUTHOR,author);
            contentValues.put(NewsContract.NewsEntry.COLUMN_DESCRIPTION,description);
            contentValues.put(NewsContract.NewsEntry.COLUMN_TITLE,title);
            contentValues.put(NewsContract.NewsEntry.COLUMN_URL,url);
            contentValues.put(NewsContract.NewsEntry.COLUMN_IMAGE,image);
            contentNewsValues[i]=contentValues;

        }
        return contentNewsValues;

    }



}
