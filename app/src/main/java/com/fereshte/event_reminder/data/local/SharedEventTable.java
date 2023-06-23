package com.fereshte.event_reminder.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.fereshte.event_reminder.data.model.ResponseModel;
import com.fereshte.event_reminder.data.model.SharedEventModel;
import com.fereshte.event_reminder.database.DataBaseHelper;

public class SharedEventTable {

    private static final String SHARE_NAME = "sharedEventTabel";
    private static final String ID = "id";
    private static final String EVENT_ID = "eventId";
    private static final String BASE_ID = "baseId";
    private static final String TOKEN = "token";
    private static final String LINK = "link";
    private static final String EDITED = "edited";

    public static final String CREATE_SHARED_EVENT_TABLE = "CREATE TABLE " + SHARE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + EVENT_ID + " INTEGER, "
            + BASE_ID + " TEXT, "
            + TOKEN + " TEXT, "
            + LINK + " TEXT, "
            + EDITED + " TEXT);";

    public final DataBaseHelper dataBaseHelper;

    public SharedEventTable(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public void insertSharedEvent(SharedEventModel sharedEventModel) {
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENT_ID, sharedEventModel.getEventId());
        contentValues.put(BASE_ID, sharedEventModel.getResponseModel().getBaseId());
        contentValues.put(TOKEN, sharedEventModel.getResponseModel().getToken());
        contentValues.put(LINK, sharedEventModel.getResponseModel().getLink());
        contentValues.put(EDITED, sharedEventModel.getEditedEvent());

        database.insert(SHARE_NAME, null, contentValues);
    }

    public SharedEventModel fetchSharedEventsFromDb(int eventId) {
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + SHARE_NAME + " WHERE " + EVENT_ID + " = " + "'" + eventId + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        SharedEventModel sharedEventModel = new SharedEventModel();

        if (cursor.moveToFirst()) {
            sharedEventModel.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            sharedEventModel.setEventId(cursor.getInt(cursor.getColumnIndex(EVENT_ID)));

            String link = cursor.getString(cursor.getColumnIndex(LINK));
            String baseId = cursor.getString(cursor.getColumnIndex(BASE_ID));
            String token = cursor.getString(cursor.getColumnIndex(TOKEN));
            sharedEventModel.setResponseModel(new ResponseModel(link, baseId, token));

            sharedEventModel.setEditedEvent(cursor.getString(cursor.getColumnIndex(EDITED)));
        }

        cursor.close();
        return sharedEventModel;
    }

    public boolean existEventInDb(int eventId) {
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + SHARE_NAME + " WHERE " + EVENT_ID + " = " + "'" + eventId + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        boolean exist = cursor.moveToFirst();
        cursor.close();
        return exist;
    }

    public boolean isEventEdited(int eventId) {
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + SHARE_NAME + " WHERE " + EVENT_ID + " = " + "'" + eventId + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        boolean edited = false;

        if (cursor.moveToFirst()) {
            String editedEvent = cursor.getString(cursor.getColumnIndex(EDITED));
            edited = !editedEvent.equals("");
        }
        cursor.close();
        return edited;
    }

    public void updateEditedColumn(int eventId, String edited) {
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        String editQuery = "UPDATE " + SHARE_NAME + " SET " +  EDITED + " = " + "'" + edited +  "'" + " WHERE " + EVENT_ID + " = " + "'" + eventId + "'";
        database.execSQL(editQuery);
    }

    public void updateSharedEvent(int eventId, SharedEventModel sharedEventModel) {
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENT_ID, sharedEventModel.getEventId());
        contentValues.put(BASE_ID, sharedEventModel.getResponseModel().getBaseId());
        contentValues.put(TOKEN, sharedEventModel.getResponseModel().getToken());
        contentValues.put(LINK, sharedEventModel.getResponseModel().getLink());
        contentValues.put(EDITED, sharedEventModel.getEditedEvent());

        database.update(SHARE_NAME, contentValues, "eventId = " + eventId, null);
    }

    public void deleteSharedEvent(int id) {
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        database.delete(SHARE_NAME, "id=" + id, null);
    }

}
