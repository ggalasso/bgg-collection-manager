package com.ggalasso.BggCollectionManager;

import android.app.Application;
import android.test.ApplicationTestCase;

//import com.ggalasso.BggCollectionManager.api.CollectionAPI;
import com.ggalasso.BggCollectionManager.controller.GameIdManager;
import com.ggalasso.BggCollectionManager.model.GameId;

import java.util.ArrayList;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
//public class ApplicationTest extends ApplicationTestCase<Application> {
public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest() {
        super(Application.class);
    }

    public void test() throws Exception {
        final int expected = 5;
        final int reality = 5;
        assertEquals(expected, reality);

    }

    // TODO : eqc | 09 may 2016 : Refactor this to use xapi
    public void testRetrieveTwentyGames() throws Exception {
//        final int expected = 20;
//        CollectionAPI capi = new CollectionAPI();
//        GameIdManager gim = capi.getIDManager();
//        ArrayList<GameId> gameIds = gim.getGameIds();
//        final int reality = gameIds.size();
//        assertEquals(expected, reality);
    }
}