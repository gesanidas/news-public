package com.example.gesanidas.news.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by gesanidas on 12/18/2016.
 */

public final class NewsContract
{

    public static final String AUTHORITY="com.example.gesanidas.news";
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+AUTHORITY);
    public static final String PATH_NEWS="news";


    public static final class NewsEntry implements BaseColumns
    {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_NEWS).build();


        public static final String TABLE_NAME="news";
        public static final String COLUMN_NEWS_ID="news_id";
        public static final String COLUMN_AUTHOR="author";
        public static final String COLUMN_TITLE="title";
        public static final String COLUMN_DESCRIPTION="description";
        public static final String COLUMN_URL="url";
        public static final String COLUMN_IMAGE="image";

    }
}
