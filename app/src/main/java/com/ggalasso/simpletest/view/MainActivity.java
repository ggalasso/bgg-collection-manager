package com.ggalasso.simpletest.view;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ggalasso.simpletest.R;
import com.ggalasso.simpletest.api.CollectionAPI;
import com.ggalasso.simpletest.api.ThingAPI;
import com.ggalasso.simpletest.controller.BoardGameManager;
import com.ggalasso.simpletest.controller.GameIdManager;
import com.ggalasso.simpletest.db.BoardGameTable;
import com.ggalasso.simpletest.model.BoardGame;
import com.ggalasso.simpletest.model.GameId;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SQLController dbCon = new SQLController(ctx);
        BoardGameTable bgtCon = new BoardGameTable(ctx);
        //bgtCon.destroyEverything();


        CollectionAPI capi = new CollectionAPI();
        ThingAPI tapi = new ThingAPI();

        GameIdManager gim = GameIdManager.getInstance();
        BoardGameManager bgm = BoardGameManager.getInstance();

        try {
            gim = capi.getIDList();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            bgm = tapi.getDetail(gim.getIdListString());
            //Log.i("MY", "blah");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //bgtCon.open();
        //bgtCon.dropAllTables();
        //bgtCon.testTables();
        //bgtCon.createAllTables();
        //bgtCon.testTables();

        // 09/20/15 - GAG - To remove everything from the board_game table use the deleteAll
        //bgtCon.deleteAll();


        bgtCon.syncBoardGameCollection(bgm.getBoardGames());

        ArrayList<BoardGame> bgList = bgtCon.fetchAllBoardGames();

        if (bgList.size() > 0) {
            for (BoardGame bg : bgList) {
                Log.d("BGCM-MA", "ID: " + bg.getId());
                Log.d("BGCM-MA", "Name: " + bg.getPrimaryName());
                Log.d("BGCM-MA", "Year Published: " + bg.getYearPub());
                Log.d("BGCM-MA", "Description: " + bg.getDescription().substring(0,75));
                Log.d("BGCM-MA", "Rating: " + bg.getRating());
            }
            Log.d("BGCM-MA", "TOTAL: " + bgList.size());
        }

        //bgtCon.close();

        // Delete Board Game from Table by ID as a string
        bgtCon.delete(bgm.getBoardGameById("35052").getId());

        // Delete Board Game from Table by passing Board Game object
        bgtCon.delete(bgm.getBoardGameById("153938"));


        //09-20-15 GAG - Fetch all game ID's and print them out.
        ArrayList<String> gameIDList = bgtCon.fetchAllGameIds();
        if (gameIDList.size() > 0) {
            for ( String id : gameIDList) {
                Log.d("BGCM-MA", "Array ID: " + id); }
        }

        //bgtCon.destroyEverything();

        //Log.i("MY ERROR", "BoardGame: " + bgm.getIdListString());
        //Log.i("My Stuff", "Blah");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
