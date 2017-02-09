package com.example.gesanidas.news;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by gesanidas on 12/17/2016.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>
{
    private Cursor cursor;
    private final NewsAdapterOnClickHandler newsAdapterOnClickHandler;


    public interface NewsAdapterOnClickHandler
    {
        void onClick(String news);
    }


    public NewsAdapter(NewsAdapterOnClickHandler handler)
    {
        newsAdapterOnClickHandler=handler;
    }



    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context=parent.getContext();
        int layoutIdForListItem = R.layout.news_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position)
    {
        cursor.moveToPosition(position);
        String title=cursor.getString(0);
        String description=cursor.getString(1);
        String url=cursor.getString(2);

        holder.textView.setText(title);

    }

    @Override
    public int getItemCount()
    {
        if (null==cursor)
            return 0;
        else
            return cursor.getCount();
    }

    void swapCursor(Cursor newCursor)
    {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        public final TextView textView;


        public NewsAdapterViewHolder(View view)
        {
            super(view);
            textView = (TextView) view.findViewById(R.id.news_data);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view)
        {

            String news = textView.getText().toString();
            newsAdapterOnClickHandler.onClick(news);
        }

    }
}
