package com.fereshte.event_reminder.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.fereshte.event_reminder.data.model.EventModel;
import com.fereshte.event_reminder.database.DataBaseHelper;
import java.util.ArrayList;

public class EventTable {

    private static final String EVENT_TABLE = "event_table";
    private static final String ID = "id";
    private static final String EVENT_TITLE = "event_title";
    private static final String EVENT_DATE = "event_date";
    private static final String EVENT_OCCASION = "event_occasion";
    private static final String EVENT_NOTE = "event_note";
    private static final String EVENT_LOCATION = "event_location";
    private static final String EVENT_LINK = "event_link";
    private static final String EVENT_COLOR = "event_color";

    public static final String CREATE_TABLE_EVENTS = "CREATE TABLE " + EVENT_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + EVENT_TITLE + " TEXT NOT NULL,"
            + EVENT_DATE + " LONG NOT NULL,"
            + EVENT_OCCASION + " TEXT,"
            + EVENT_NOTE + " TEXT,"
            + EVENT_LOCATION + " TEXT,"
            + EVENT_LINK + " TEXT,"
            + EVENT_COLOR + " INTEGER);";

    public final DataBaseHelper dataBaseHelper;

    public EventTable(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public Long insertNewEventToDb(EventModel eventModel){
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EVENT_TITLE, eventModel.getEventTitle());
        values.put(EVENT_DATE, eventModel.getEventDate());
        values.put(EVENT_OCCASION, eventModel.getEventOccasion());
        values.put(EVENT_NOTE, eventModel.getEventNote());
        values.put(EVENT_LOCATION, eventModel.getEventLocation());
        values.put(EVENT_LINK, eventModel.getEventLink());
        values.put(EVENT_COLOR, eventModel.getEventColor());
        return database.insert(EVENT_TABLE, null, values);
    }

    public ArrayList<EventModel> fetchEventsFromDb(){
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        ArrayList<EventModel> eventArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + EVENT_TABLE;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                EventModel eventModel = new EventModel();
                eventModel.setEventId(cursor.getInt(cursor.getColumnIndex(ID)));
                eventModel.setEventTitle(cursor.getString(cursor.getColumnIndex(EVENT_TITLE)));
                eventModel.setEventDate(cursor.getLong(cursor.getColumnIndex(EVENT_DATE)));
                eventModel.setEventOccasion(cursor.getString(cursor.getColumnIndex(EVENT_OCCASION)));
                eventModel.setEventNote(cursor.getString(cursor.getColumnIndex(EVENT_NOTE)));
                eventModel.setEventLocation(cursor.getString(cursor.getColumnIndex(EVENT_LOCATION)));
                eventModel.setEventLink(cursor.getString(cursor.getColumnIndex(EVENT_LINK)));
                eventModel.setEventColor(cursor.getInt(cursor.getColumnIndex(EVENT_COLOR)));

                eventArrayList.add(eventModel);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return eventArrayList;
    }

    public EventModel fetchEventById(int id){
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        String selectByIdQuery = "SELECT * FROM " + EVENT_TABLE + " WHERE " + ID + " = " + "'" + id + "'";
        Cursor cursor = database.rawQuery(selectByIdQuery, null);
        EventModel eventModel = new EventModel();

        if(cursor.moveToFirst()){
                eventModel.setEventId(cursor.getInt(cursor.getColumnIndex(ID)));
                eventModel.setEventTitle(cursor.getString(cursor.getColumnIndex(EVENT_TITLE)));
                eventModel.setEventDate(cursor.getLong(cursor.getColumnIndex(EVENT_DATE)));
                eventModel.setEventOccasion(cursor.getString(cursor.getColumnIndex(EVENT_OCCASION)));
                eventModel.setEventNote(cursor.getString(cursor.getColumnIndex(EVENT_NOTE)));
                eventModel.setEventLocation(cursor.getString(cursor.getColumnIndex(EVENT_LOCATION)));
                eventModel.setEventLink(cursor.getString(cursor.getColumnIndex(EVENT_LINK)));
                eventModel.setEventColor(cursor.getInt(cursor.getColumnIndex(EVENT_COLOR)));
        }
        cursor.close();
        return eventModel;
    }

    public void deleteEventFromDb(int id){
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        database.delete(EVENT_TABLE, "id=" + id, null);
    }

    public void updateEvent(EventModel eventModel, int id){
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EVENT_TITLE, eventModel.getEventTitle());
        values.put(EVENT_DATE, eventModel.getEventDate());
        values.put(EVENT_OCCASION, eventModel.getEventOccasion());
        values.put(EVENT_NOTE, eventModel.getEventNote());
        values.put(EVENT_LOCATION, eventModel.getEventLocation());
        values.put(EVENT_LINK, eventModel.getEventLink());
        values.put(EVENT_COLOR, eventModel.getEventColor());

        database.update(EVENT_TABLE, values, "id=" + id, null);
    }
}
