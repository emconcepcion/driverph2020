package com.cav.DriverphTruckerlearningPH2020;

import android.provider.BaseColumns;

import java.text.SimpleDateFormat;

public class DbContract {

    public static final int SYNC_STATUS_SAVED = 0;
    public static final int SYNC_STATUS_FAILED = 1;

    public static class ScoresTable implements BaseColumns {

        //android access to localhost
        //public static final String SERVER_URL = "http://10.0.2.2:8080/driverph/syncinfo.php";
//        public static final String SERVER_URL = "http://10.0.2.2:8080/driverph2020/score_test.php";
        public static final String SERVER_URL = "https://driver-ph.000webhostapp.com/driverphtest/scores.php";
        public static final String UI_UPDATE_BROADCAST = "com.cav.quizinstructions.uiupdatebroadcast";

        public static final String DATABASE_NAME = "id15683662_driverph";
        public static final String TABLE_NAME_SCORES = "tbl_scores";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_SCORE = "score";
        public static final String COLUMN_NAME_NUM_ITEMS = "num_of_items";
        public static final String COLUMN_NAME_CHAPTER = "chapter";
        public static final String COLUMN_NAME_NUM_ATTEMPT = "num_of_attempt";
        public static final String COLUMN_NAME_DATE_TAKEN = "date_taken";
        public static final String SYNC_STATUS = "syncStatus";

    }


}
