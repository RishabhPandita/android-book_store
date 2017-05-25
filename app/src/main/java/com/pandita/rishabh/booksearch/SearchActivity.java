package com.pandita.rishabh.booksearch;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rishabh on 18-05-2017.
 */

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final int BOOKSEARCH_LOADER_ID = 1;
    private SearchAdapter searchAdapter;
    private String urlReq = "https://www.googleapis.com/books/v1/volumes";
    private TextView emptyStateTextView;
    private String searchKeyString ="Harry Potter";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);

        if(getIntent()!=null){
            searchKeyString = getIntent().getStringExtra("SEARCH_PARAM");
        }

        ListView bookListView = (ListView) findViewById(R.id.book_list);
        searchAdapter = new SearchAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(searchAdapter);
        emptyStateTextView= (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(emptyStateTextView);


        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book b = searchAdapter.getItem(position);
                String preview = b.getPreviewLink();
                Intent browser_intent = new Intent(Intent.ACTION_VIEW);
                browser_intent.setData(Uri.parse(preview));
                startActivity(browser_intent);
            }
        });

        bookListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Book b = searchAdapter.getItem(position);
                String buyLink = b.getBuyLink();

                if(!buyLink.equals("NA")) {
                    Intent browser_intent = new Intent(Intent.ACTION_VIEW);
                    browser_intent.setData(Uri.parse(buyLink));
                    startActivity(browser_intent);
                }
                else
                    Toast.makeText(getApplicationContext(),"Out of stock",Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOKSEARCH_LOADER_ID, null, this);

        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            emptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String minResultList = sharedPrefs.getString(getString(R.string.settings_min_result_key),"10");
        String orderBy = sharedPrefs.getString(getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(urlReq);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", searchKeyString);
        uriBuilder.appendQueryParameter("maxResults", minResultList);
        uriBuilder.appendQueryParameter("orderBy", orderBy);
        return new SearchLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> bookList) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        searchAdapter.clear();
        if (bookList != null && !bookList.isEmpty()) {
            searchAdapter.addAll(bookList);
        }
        emptyStateTextView.setText(R.string.no_bookList);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        searchAdapter.clear();
    }


}
