package com.ggalasso.BggCollectionManager.db;

import android.content.Context;
import android.util.Log;

import com.ggalasso.BggCollectionManager.db.Schema.MechanicHelper;
import com.ggalasso.BggCollectionManager.db.Schema.MechanicInGameHelper;
import com.ggalasso.BggCollectionManager.model.UtilityConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by truthd on 9/20/2015.
 */
public class MechanicInGameTable extends SQLController {

    public MechanicInGameTable(){

    }

    public MechanicInGameTable(Context c) {
        super(c);
    }

    public void deleteAllRowsFromTable(){
        deleteAllRowsFromTable(MechanicInGameHelper.getTableName());
    }

    public void insertAllMechanicsInGame(Map<String, ArrayList<String>> bgMecMap ) {
        String mechanicInGameTable = MechanicInGameHelper.getTableName();
        List<String> columns = Arrays.asList(MechanicInGameHelper.mg_bg_Id,MechanicInGameHelper.mg_me_Id);

        String insertSQL = getInsertSQL(bgMecMap, mechanicInGameTable, columns);

        super.insertToDatabase(insertSQL);
        Log.d("BGCM-CT", "Bulk insert into " + mechanicInGameTable + "\nSQL statement: \n" + insertSQL);
    }

    public String getInsertSQL(Map<String, ArrayList<String>> bgMecMap, String tableName, List<String> columns) {
        String insertSQL = "INSERT OR IGNORE INTO " + tableName + " (";
        insertSQL += super.getColumns(columns);
        insertSQL += " VALUES ";
        Map<String, ArrayList<String>> treeMap = new TreeMap<>(bgMecMap);
        for (Map.Entry<String, ArrayList<String>> elem : treeMap.entrySet()) {
            String key = elem.getKey();
            insertSQL += super.getRowValues(key, elem.getValue());
        }
        insertSQL = insertSQL.substring(0, insertSQL.length() - UtilityConstants.TRIM_COMMA.getValue());
        insertSQL += ";";
        return insertSQL;
    }

}
