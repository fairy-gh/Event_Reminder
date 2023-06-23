package com.fereshte.event_reminder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.fereshte.event_reminder.data.local.EventTable;
import com.fereshte.event_reminder.data.local.SharedEventTable;
import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "proDb.db";
    private static final int DB_VERSION = 1;

    public DataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(EventTable.CREATE_TABLE_EVENTS);
        sqLiteDatabase.execSQL(SharedEventTable.CREATE_SHARED_EVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
}