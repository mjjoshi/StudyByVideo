package com.android.studybyvideo.model;

import com.google.gson.annotations.SerializedName;

public class CoursesPurchasedList {
    @SerializedName("BookName")
    private String BookName;
    @SerializedName("Book_Des")
    private String Book_Des;
    @SerializedName("BookImage")
    private String BookImage;
    @SerializedName("price")
    private String price;


    public String getBookImage() {
        return BookImage;
    }

    public void setBookImage(String bookImage) {
        BookImage = bookImage;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getBook_Des() {
        return Book_Des;
    }

    public void setBook_Des(String book_Des) {
        Book_Des = book_Des;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
