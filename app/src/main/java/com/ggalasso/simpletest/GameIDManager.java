package com.ggalasso.simpletest;

import android.util.Log;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name="items")
public class GameIDManager {

    private static GameIDManager ourInstance = null;

    @ElementList(entry="item", inline=true, required=false)
    private ArrayList<GameID> GameIDs;
    //@ElementList(entry="item", inline=true, required=false)
    //private ArrayList<BoardGame> BoardGames;

    private GameIDManager() { Log.i("INFO", "Instantiated GameID"); }

    public static GameIDManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new GameIDManager();
        }
        return ourInstance;
    }

//    public ArrayList<BoardGame> getBoardGames() {
//        return BoardGames;
//    }

    public ArrayList<GameID> getGameIDs() {
        return GameIDs;
    }

//    public String getIdListString() {
//        String idList = "";
//        for (BoardGame game : getBoardGames()) {
//            if (idList == "") {
//                idList = game.getObjectid();
//            } else {
//                idList += "," + game.getObjectid();
//            }
//        }
//        return idList;
//    }

    public String getIdListString() {
        String idList = "";
        for (GameID game : getGameIDs()) {
            if (idList == "") {
                idList = game.getObjectid();
            } else {
                idList += "," + game.getObjectid();
            }
        }
        return idList;
    }
}
