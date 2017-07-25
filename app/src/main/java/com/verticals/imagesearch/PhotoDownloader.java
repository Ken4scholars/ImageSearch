package com.verticals.imagesearch;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenneth on 25.07.17.
 */

public class PhotoDownloader implements PhotoService {

    private PhotoView view;
    private static final String BASE_URL = "http://www.google.com/search?q=";
    private final String USER_AGENT = "Mozilla/5.0";

    public static final String TAG = "PhotoDownloader";

    public PhotoDownloader(PhotoView view) {
        this.view = view;
    }

    @Override
    public void search(String query)throws Exception {

        final String url = query;
        new AsyncTask<Void, Void, Void>() {
            Exception exception = null;
            List<String> photos = new ArrayList<>();
            int count = 0;
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    URL obj = new URL(BASE_URL + url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("User-Agent", USER_AGENT);

                    int responseCode = con.getResponseCode();
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        String[] contents = inputLine.split(">");
                        for(int i=0; i<contents.length; i++){
                            if(contents[i].length() > 5 && contents[i].startsWith("<img")){
                                String photo = parseUrl(contents[i]);
                                if(photo != null){
                                    photos.add(photo);
                                    count++;
//                                    if(count == 5)publishProgress();
                                }
                            }
                        }
                    }
                    in.close();
                    publishProgress();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Void... values) {

//                List<String> currentPhotos = photos.subList(photos.size()-count, photos.size());
//                count = 0;
//                if(currentPhotos.size() >= 5){
//                    view.fillPhoto(currentPhotos);
//                }else view.addPhotos(photos);
                view.addPhotos(photos);
            }
        }.execute();

    }

    private String parseUrl(String tag){
        String photoUrl = null;
        int srcIndex = tag.indexOf("src=");
        if(srcIndex > 0){
            photoUrl = tag.substring(srcIndex + 5, tag.indexOf("\"", srcIndex + 5));
            if(!photoUrl.startsWith("http"))photoUrl = null;
            else Log.d(TAG, photoUrl);
        }
        return photoUrl;
    }
}
