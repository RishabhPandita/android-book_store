package com.pandita.rishabh.booksearch;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;

import java.util.List;

/**
 * Created by Rishabh on 19-05-2017.
 */
public class SearchLoader extends AsyncTaskLoader<List<Book>> {
    private static final String LOG_TAG = SearchAdapter.class.getName();
    private String url;

    public SearchLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {

        if(url == null){
            return null;
        }
        List<Book> bookList = QueryUtils.fetchBookData(url);
        return bookList;

    }
}
