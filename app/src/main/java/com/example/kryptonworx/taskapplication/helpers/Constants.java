package com.example.kryptonworx.taskapplication.helpers;


public class Constants {

    public static String Api_key="f215a67afdb72fe1c0b48384925b047d";
    public static String Secret_key="f215a67afdb72fe1c0b48384925b047d";

    public static String Base_Service_URL="https://api.flickr.com/services/rest/?method=flickr.";
    public static String Search_Service_URL=Base_Service_URL+"photos.search&api_key="+Api_key+"&text=%s&format=json&nojsoncallback=1&api_sign=%s";
    public static String Img_Base_URL="https://farm%s.staticflickr.com/%s/%s_%s.jpg";
    public static String Details_Service_URL=Base_Service_URL+"people.getPhotos&api_key="+Api_key+"&user_id=%s&format=json&nojsoncallback=1&api_sign=%s";
}
