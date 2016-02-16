package com.ggalasso.BggCollectionManager.api;

import android.os.AsyncTask;
import android.util.Log;

import com.ggalasso.BggCollectionManager.controller.BoardGameManager;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by Edward on 9/8/2015.
 */
public class ThingAPI {

    public BoardGameManager getGameManager(String idList) {
        // BoardGameManager bgm = BoardGameManager.getInstance();
        try {
            return getDetail(idList);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public BoardGameManager getDetail(String queryString) throws ExecutionException, InterruptedException {
        String downloadURL = "https://boardgamegeek.com/xmlapi2/thing?id=" + queryString + "&stats=1";
        Log.i("BGCM-TAPI", "Attempting to download data from: " + downloadURL);
        AsyncTask<String, Void, BoardGameManager> getXMLTask = new getBoardGameDetails().execute(downloadURL);
        try {
            return getXMLTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class getBoardGameDetails extends AsyncTask<String, Void, BoardGameManager> {
        @Override
        protected BoardGameManager doInBackground(String... params) {
            BoardGameManager bgm = BoardGameManager.getInstance();
            Log.i("BGCM-TAPI", "REACHED doInBackground");
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                try {
                    InputStream is = new BufferedInputStream(con.getInputStream());
                    Serializer serializer = new Persister();
                    bgm = serializer.read(BoardGameManager.class, is, false);

                    Log.i("BGCM-TAPI", "Finished Serializing");
                } finally {
                    con.disconnect();
                }
                Log.i("BGCM-TAPI", "Reached end of input stream.");

            } catch (Exception e) {
                Log.e("BGCM-TAPI", "Logging exception: " + e);
            }
            return bgm;
        }
    }
}