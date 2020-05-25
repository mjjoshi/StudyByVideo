package com.android.studybyvideo.model;

import com.google.gson.annotations.SerializedName;

public class CoursesOfferedList {
    @SerializedName("BookName")
    private String BookName;
    @SerializedName("Book_Des")
    private String Book_Des;

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
}
