package com.amit.opinverse;

import android.graphics.Bitmap;

public class YoutubeCategoryModel {
    String youtubeCategoryName, youtubeCategoryDesc;
    Bitmap imageBitmap;

    YoutubeCategoryModel(){

    }

    YoutubeCategoryModel(String youtubeCategoryName, String youtubeCategoryDesc, Bitmap imageBitmap){
        this.youtubeCategoryName = youtubeCategoryName;
        this.youtubeCategoryDesc = youtubeCategoryDesc;
        this.imageBitmap = imageBitmap;
    }

    public String getYoutubeCategoryDesc() {
        return youtubeCategoryDesc;
    }

    public String getYoutubeCategoryName() {
        return youtubeCategoryName;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }
}
