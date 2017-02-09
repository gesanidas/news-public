package com.example.gesanidas.news.sync;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by gesanidas on 12/18/2016.
 */

public class NewsSyncIntentService extends IntentService
{

    public NewsSyncIntentService()
    {
        super("NewsSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        NewsSyncTask.syncNews(this);


    }
}
