package com.example.gesanidas.news.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.gesanidas.news.MainActivity;
import com.example.gesanidas.news.data.NewsContract;
import com.example.gesanidas.news.data.NewsPreferences;
import com.example.gesanidas.news.utilities.JsonUtils;
import com.example.gesanidas.news.utilities.NetworkUtils;

import java.net.URL;

/**
 * Created by gesanidas on 12/18/2016.
 */

public class NewsSyncTask
{
    synchronized public static void syncNews(Context context)
    {


        //String source= NewsPreferences.getPreferredSource(context);


        URL url = NetworkUtils.getUrl(context);

        try
        {
            String response = NetworkUtils.getResponseFromHttpUrl(url);
            ContentValues[] cv= JsonUtils.getContentValues(context,response);
            if (cv!=null && cv.length!=0)
            {
                ContentResolver contentResolver=context.getContentResolver();
                contentResolver.delete(NewsContract.NewsEntry.CONTENT_URI,null,null);
                contentResolver.bulkInsert(NewsContract.NewsEntry.CONTENT_URI, cv);
            }






        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
