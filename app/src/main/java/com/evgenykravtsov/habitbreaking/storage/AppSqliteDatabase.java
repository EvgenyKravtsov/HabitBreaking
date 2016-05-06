package com.evgenykravtsov.habitbreaking.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.evgenykravtsov.habitbreaking.model.MainApp;

public class AppSqliteDatabase extends SQLiteOpenHelper {

    private static final String TAG = AppSqliteDatabase.class.getSimpleName();

    private static final String DATABASE_NAME = "app_sqlite_database";
    private static final int DATABASE_VERSION = 1;

    // Habit table
    private static final String HABIT_TABLE_NAME = "habit_table";
    private static final String COLUMN_DAY = "day";
    private static final String COLUMN_COUNT = "count";

    private static AppSqliteDatabase instance;

    private SQLiteDatabase database;

    ////

    private AppSqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    public static synchronized AppSqliteDatabase getInstance() {
        if (instance == null) {
            instance = new AppSqliteDatabase(MainApp.getAppContext());
        }
        return instance;
    }

    ////

    public int getHabitCountByDay(long day) {
        Cursor cursor = database.rawQuery("SELECT count FROM " + HABIT_TABLE_NAME
            + " WHERE " + COLUMN_DAY + " = " + day + ";", null);

        int count = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            count = cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();

        return count;
    }

    public void writeNewHabitDay(long day) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DAY, day);
        contentValues.put(COLUMN_COUNT, 1);
        database.insert(HABIT_TABLE_NAME, null, contentValues);
    }

    public void updateHabitCountByDay(long day) {
        int count = getHabitCountByDay(day) + 1;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COUNT, count);
        database.update(HABIT_TABLE_NAME, contentValues, COLUMN_DAY + "=" + day, null);
    }

    public int getRowCountFromHabitTable() {
        Cursor cursor = database.rawQuery("SELECT * FROM " + HABIT_TABLE_NAME + ";", null);

        int rowCount = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            rowCount++;
            cursor.moveToNext();
        }
        cursor.close();

        return rowCount;
    }

    public void deleteFirstRowFromHabitTable() {
        Cursor cursor = database.rawQuery("SELECT * FROM " + HABIT_TABLE_NAME + ";", null);
        cursor.moveToFirst();
        int dayToDelete = cursor.getInt(0);
        database.delete(HABIT_TABLE_NAME, COLUMN_DAY + "=" + dayToDelete, null);
        cursor.close();
    }

    ////

    @Override
    public void onCreate(SQLiteDatabase db) {
        createHabitTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropHabitTable(db);
        onCreate(db);
    }

    ////

    private void createHabitTable(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + HABIT_TABLE_NAME + "("
            + COLUMN_DAY + " INTEGER NOT NULL, "
            + COLUMN_COUNT + " INTEGER NOT NULL);");
    }

    private void dropHabitTable(SQLiteDatabase database) {
        database.execSQL("DROP TABLE IF EXISTS " + HABIT_TABLE_NAME);
    }

    private void showHabitTableToColsole() {
        Cursor cursor = database.rawQuery("SELECT * FROM " + HABIT_TABLE_NAME + ";", null);

        Log.d(TAG, "Habit table >>>");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, COLUMN_DAY + " - " + cursor.getInt(0) + " | " + COLUMN_COUNT + " - "
                + cursor.getInt(1));
            cursor.moveToNext();
        }
        cursor.close();
    }
}
