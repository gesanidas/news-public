package com.example.gesanidas.news.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by gesanidas on 12/18/2016.
 */

public class NewsContentProvider extends ContentProvider
{
    private NewsDbHelper newsDbHelper;
    public static final int NEWS=100;
    public static final int NEWS_WITH_ID=101;
    private static final UriMatcher sUriMatcher=buildUriMatcher();

    public static UriMatcher buildUriMatcher()
    {
        UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(NewsContract.AUTHORITY,NewsContract.PATH_NEWS,NEWS);

        uriMatcher.addURI(NewsContract.AUTHORITY,NewsContract.PATH_NEWS+"/#",NEWS_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate()
    {
        Context context=getContext();
        newsDbHelper=new NewsDbHelper(context);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri,  String[] projection, String selection,
                        String[] selectionArgs, String sortOrder)
    {
        final SQLiteDatabase db = newsDbHelper.getReadableDatabase();
        int match =sUriMatcher.match(uri);
        Cursor cursor;
        switch(match)
        {
            case NEWS:  cursor=db.query(NewsContract.NewsEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:    throw new UnsupportedOperationException("Unknown uri: "+uri);

        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);



        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues)
    {

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        int numRowsDeleted;
        if (null == selection) selection = "1";


        switch (sUriMatcher.match(uri))
        {
            case NEWS:
                numRowsDeleted = newsDbHelper.getWritableDatabase().delete(
                        NewsContract.NewsEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
             default:
                 throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (numRowsDeleted != 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings)
    {
        return 0;
    }


    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values)
    {
        final SQLiteDatabase db = newsDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri))
        {

            case NEWS:
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values)
                    {
                        long _id = db.insert(NewsContract.NewsEntry.TABLE_NAME, null, value);
                        if (_id != -1)
                        {
                            rowsInserted++;
                            Log.i("tag","wrtting to db");
                        }
                    }
                    db.setTransactionSuccessful();
                } finally
                {
                    db.endTransaction();
                }

                if (rowsInserted > 0)
                {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }
    }





}
