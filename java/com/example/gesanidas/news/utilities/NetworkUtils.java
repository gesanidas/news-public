package com.example.gesanidas.news.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.gesanidas.news.data.NewsPreferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by gesanidas on 12/17/2016.
 */

public class NetworkUtils
{

    private static final String BASE_URL="https://newsapi.org/v1/articles?source=";
    private static final String SOURCE="bild";
    private static final String END_URL=BuildConfig.API_KEY;


    public static URL getUrl(Context context)
    {
        String source=NewsPreferences.getPreferredSource(context);
        Log.i("source = ",source);
        String url=BASE_URL+source+END_URL;
        try
        {

            return new URL(url);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public static URL fromString()
    {
        String st="https://newsapi.org/v1/articles?source=espn&sortBy=top&apiKey=e48594bf95054e9995526745f92e432d";
        try
        {
            return new URL(st);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException
    {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String result=null;


        try
        {


            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null)
            {

                result = null;

            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null)
            {
                buffer.append(line + "\n");
            }

            result = buffer.toString();
        }
        catch (IOException e)
        {
            Log.e("PlaceholderFragment", "Error ", e);
            result = null;
        }
        finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (final IOException e)
                {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }

        return result;

    }


}
