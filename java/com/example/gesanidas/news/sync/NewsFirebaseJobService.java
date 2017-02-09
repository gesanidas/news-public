package com.example.gesanidas.news.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.RetryStrategy;

/**
 * Created by gesanidas on 12/21/2016.
 */

public class NewsFirebaseJobService extends JobService
{
    private AsyncTask<Void,Void,Void> newsSyncTask;


    @Override
    public boolean onStartJob(final JobParameters job)
    {
        newsSyncTask=new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                Context context=getApplicationContext();
                NewsSyncTask.syncNews(context);

                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid)
            {

                jobFinished(job,false);
            }
        };
        newsSyncTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job)
    {
        if (newsSyncTask!=null)
        {
            newsSyncTask.cancel(true);
        }
        return true;
    }
}
