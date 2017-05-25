package com.pandita.rishabh.booksearch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Rishabh on 18-05-2017.
 */

public class SearchAdapter extends ArrayAdapter<Book> {

    public SearchAdapter(Context context, List<Book> bookList) {
        super(context, 0, bookList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.result_list, parent, false);
        }

        Book book = getItem(position);

        TextView bookNameText = (TextView) listItemView.findViewById(R.id.book_title);
        bookNameText.setText(book.getBookName());

        TextView bookAuthorText = (TextView) listItemView.findViewById(R.id.book_author);
        bookAuthorText.setText(book.getBookAuthor());

        ImageView imageThumbnail = (ImageView)listItemView.findViewById(R.id.image_thumbnail);
        Bitmap thumbnailUrl = book.getBookCoverImage();
        if(thumbnailUrl!=null)
            imageThumbnail.setImageBitmap(thumbnailUrl);

        return listItemView;
    }
}
