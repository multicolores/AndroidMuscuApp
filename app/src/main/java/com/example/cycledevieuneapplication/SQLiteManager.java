package com.example.cycledevieuneapplication;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SQLiteManager extends SQLiteOpenHelper
{
    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "ExercisesDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Exercise";
    private static final String COUNTER = "Counter";

    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String DESC_FIELD = "description";
    private static final String MUSCLES_FIELD = "muscles";
    private static final String REP_FIELD = "lastsWorkoutRepetitions";
    private static final String RECUP_FIELD = "lastsWorkoutRecup";
    private static final String POIDS_FIELD = "lastsWorkoutPoids";
    private static final String DATE_FIELD = "lastsWorkoutDate";

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public SQLiteManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context)
    {
        if(sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        //Todo toussa la a changer look comment c'est fait la https://o7planning.org/10433/android-sqlite-database
        //todo et bien faire en sorte d'avoir les bonne colomne pour la table exo
        Log.d("ooooo", "on passe par là");

        String script = "CREATE TABLE " + TABLE_NAME + "("
                + ID_FIELD + " INTEGER PRIMARY KEY," + NAME_FIELD + " TEXT,"
                + DESC_FIELD + " TEXT," + MUSCLES_FIELD + " TEXT," + REP_FIELD + " TEXT," + RECUP_FIELD + " TEXT," + POIDS_FIELD + " TEXT," + DATE_FIELD + " TEXT" + ")";


        sqLiteDatabase.execSQL(script);
        // Execute Script.

        //sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        Log.i("tag", "MyDatabaseHelper.onUpgrade ... ");
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(sqLiteDatabase);
//        switch (oldVersion)
//        {
//            case 1:
//                sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
//            case 2:
//                sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
//        }
    }


    public void addExerciseToDatabase(Exercises exo)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, exo.getId());
        contentValues.put(NAME_FIELD, exo.getName());
        contentValues.put(DESC_FIELD, exo.getDescription());
        contentValues.put(MUSCLES_FIELD, exo.getMuscles());
        contentValues.put(REP_FIELD, exo.getLastsWorkoutRepetitions());
        contentValues.put(RECUP_FIELD, exo.getLastsWorkoutRecup());
        contentValues.put(POIDS_FIELD, exo.getLastsWorkoutPoids());
        contentValues.put(DATE_FIELD, exo.getLastsWorkoutDate());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public void populateExercisesListArray()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null))
        {
            if(result.getCount() != 0)
            {
                while (result.moveToNext())
                {
                    String id = result.getString(1);
                    String name = result.getString(2);
                    String desc = result.getString(3);
                    String muscles = result.getString(4);
                    String rep = result.getString(5);
                    //String date = result.getString(6);
                    Exercises exo = new Exercises(id, name, desc, muscles, rep);
                    Exercises.exoArrayList.add(exo);
                }
            }
        }
    }


    public void createDefaultExo()  {
        //deleteTitle("1");
        //deleteTitle("2");
        //deleteTitle("3");
        Exercises dips = new Exercises("1","Dips","Exercise poly-articulaire travaillant principalement les pecs et les triceps.",
                "pecs, triceps", "8 8 8 7, 9 8 7 7", "2min, 2m30", "50kg, 50kg",
                "Thu Nov 24 10:53:19 GMT+01:00 2022, Thu Nov 24 10:53:19 GMT+01:00 2022");
        Exercises devlp = new Exercises("2","Dvlp couchée","Exercise poly-articulaire travaillant principalement les pecs et les triceps.",
                "pecs, triceps", "8 8 8 7, 9 8 7 7", "2min, 2m30", "10kg, 10kg",
                "Thu Nov 24 10:53:19 GMT+01:00 2022, Thu Nov 24 10:53:19 GMT+01:00 2022");
        Exercises squat = new Exercises("3","Squat","la description", "quadriceps, ecshio, grand fessier", "8 8 8 7, 9 8 7 7, 8 8 8 7","2min, 2m30, 2m30", "70kg, 75kg, 75kg",
                "Thu Nov 24 10:53:19 GMT+01:00 2022, Thu Nov 24 10:53:19 GMT+01:00 2022, Thu Nov 24 10:53:19 GMT+01:00 2022\"");

        this.addExerciseToDatabase(dips);
        this.addExerciseToDatabase(devlp);
        this.addExerciseToDatabase(squat);
    }


    public List<Exercises> getAllExercises() {
        Log.i("Log", "MyDatabaseHelper.getAllNotes ... " );

        List<Exercises> exerciseList = new ArrayList<Exercises>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Exercises exo = new Exercises();
                exo.setId(cursor.getString(0));
                exo.setName(cursor.getString(1));
                exo.setDescription(cursor.getString(2));
                exo.setMuscles(cursor.getString(3));
                exo.setLastsWorkoutRepetitions(cursor.getString(4));
                exo.setLastsWorkoutDate(cursor.getString(5));
                // Adding note to list
                exerciseList.add(exo);
            } while (cursor.moveToNext());
        }

        // return note list
        return exerciseList;
    }

    public List<Exercises> getAllExercisesNames() {
        Log.i("Log", "MyDatabaseHelper.getAllNotes ... " );

        //List<Exercises> exerciseList = new ArrayList<Exercises>();
        ArrayList exercisesList = new ArrayList();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Exercises exo = new Exercises();
                exo.setId(cursor.getString(0));
                exo.setName(cursor.getString(1));
                exo.setDescription(cursor.getString(2));
                exo.setMuscles(cursor.getString(3));
                exo.setLastsWorkoutRepetitions(cursor.getString(4));
                //exo.setLastsWorkoutDate(cursor.getString(6));
                // Adding note to list
               // exerciseList.add(exo);
                //todo peut eter remettre le truc au dessus a voir
                exercisesList.add(exo.getName());
                //exercisesList.add(exo.getLastsWorkoutRepetitions());
            } while (cursor.moveToNext());
        }

        // return note list
        return exercisesList;
    }

    //public void updateNoteInDB(Exercises exo)
    //{
    //   SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    //   ContentValues contentValues = new ContentValues();
    //   contentValues.put(ID_FIELD, exo.getId());
    //   contentValues.put(TITLE_FIELD, exo.getTitle());
    //contentValues.put(DESC_FIELD, exo.getDescription());
    //  contentValues.put(DELETED_FIELD, getStringFromDate(exo.getDeleted()));

    // sqLiteDatabase.update(TABLE_NAME, contentValues, ID_FIELD + " =? ", new String[]{String.valueOf(note.getId())});
    //}

    private String getStringFromDate(Date date)
    {
        if(date == null)
            return null;
        return dateFormat.format(date);
    }

    private Date getDateFromString(String string)
    {
        try
        {
            return dateFormat.parse(string);
        }
        catch (ParseException | NullPointerException e)
        {
            return null;
        }
    }


    public int getExercisesNumber() {
        Log.i("tag", "MyDatabaseHelper.getExercisesNumber ... " );

        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }


    public Exercises getExerciseByname(String name) {
        Log.i("tag", "MyDatabaseHelper.getExerciseByname ... " + name);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { ID_FIELD,
                        NAME_FIELD, DESC_FIELD, MUSCLES_FIELD, REP_FIELD, RECUP_FIELD, POIDS_FIELD, DATE_FIELD }, NAME_FIELD + "=?",
                new String[] { name }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Exercises exo = new Exercises();
        exo.setId(cursor.getString(0));
        exo.setName(cursor.getString(1));
        exo.setDescription(cursor.getString(2));
        exo.setMuscles(cursor.getString(3));
        exo.setLastsWorkoutRepetitions(cursor.getString(4));
        exo.setLastsWorkoutRecup(cursor.getString(5));
        exo.setLastsWorkoutPoids(cursor.getString(6));
        exo.setLastsWorkoutDate(cursor.getString(7));
        // return note
        return exo;
    }


    public boolean deleteTitle(String name)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        return sqLiteDatabase.delete(TABLE_NAME, ID_FIELD + "=" + name, null) > 0;
    }
}