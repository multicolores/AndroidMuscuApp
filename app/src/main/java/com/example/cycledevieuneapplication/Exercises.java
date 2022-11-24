package com.example.cycledevieuneapplication;

import java.util.ArrayList;

public class Exercises {
    public static ArrayList<Exercises> exoArrayList = new ArrayList<>();


    private String id;
    private String name;
    private String description;
    private String muscles;
    private String lastsWorkoutRepetitions;
    private String lastsWorkoutRecup;
    private String lastsWorkoutPoids;
    private String lastsWorkoutDate;

    public Exercises()  {

    }

    public Exercises(String id, String name, String description, String muscles) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.muscles = muscles;
    }

    public Exercises(String id, String name, String description, String muscles, String lastsWorkoutRepetitions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.muscles = muscles;
        this.lastsWorkoutRepetitions = lastsWorkoutRepetitions;
    }

    public Exercises(String id, String name, String description, String muscles, String lastsWorkoutRepetitions, String lastsWorkoutDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.muscles = muscles;
        this.lastsWorkoutRepetitions = lastsWorkoutRepetitions;
        this.lastsWorkoutDate = lastsWorkoutDate;
    }

    public Exercises(String id, String name, String description, String muscles, String lastsWorkoutRepetitions, String lastsWorkoutRecup, String lastsWorkoutPoids, String lastsWorkoutDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.muscles = muscles;
        this.lastsWorkoutRepetitions = lastsWorkoutRepetitions;
        this.lastsWorkoutRecup = lastsWorkoutRecup;
        this.lastsWorkoutPoids = lastsWorkoutPoids;
        this.lastsWorkoutDate = lastsWorkoutDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMuscles() {
        return muscles;
    }

    public void setMuscles(String muscles) {
        this.muscles = muscles;
    }

    public String getLastsWorkoutRepetitions() {
        return lastsWorkoutRepetitions;
    }

    public void setLastsWorkoutRepetitions(String lastsWorkoutRepetitions) {
        this.lastsWorkoutRepetitions = lastsWorkoutRepetitions;
    }

    public String getLastsWorkoutDate() {
        return lastsWorkoutDate;
    }

    public void setLastsWorkoutDate(String lastsWorkoutDate) {
        this.lastsWorkoutDate = lastsWorkoutDate;
    }

    public String getLastsWorkoutRecup() {
        return lastsWorkoutRecup;
    }

    public void setLastsWorkoutRecup(String lastsWorkoutRecup) {
        this.lastsWorkoutRecup = lastsWorkoutRecup;
    }

    public String getLastsWorkoutPoids() {
        return lastsWorkoutPoids;
    }

    public void setLastsWorkoutPoids(String lastsWorkoutPoids) {
        this.lastsWorkoutPoids = lastsWorkoutPoids;
    }
}