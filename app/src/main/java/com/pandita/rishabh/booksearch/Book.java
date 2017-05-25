package com.pandita.rishabh.booksearch;

import android.graphics.Bitmap;

/**
 * Created by Rishabh on 18-05-2017.
 */

public class Book {
    private String bookName;
    private String bookAuthor;
    private Bitmap bookCoverImage;
    private String previewLink;
    private String buyLink;

    public Book(String bookName,String bookAuthor, Bitmap bookCoverThumbnail, String previewLink, String buyLink){
        this.bookName =bookName;
        this.bookAuthor = bookAuthor;
        bookCoverImage = bookCoverThumbnail;
        this.previewLink = previewLink;
        this.buyLink=buyLink;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public Bitmap getBookCoverImage() {
        return bookCoverImage;
    }

    public void setBookCoverImage(Bitmap bookCoverImage) {
        this.bookCoverImage = bookCoverImage;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }
}
