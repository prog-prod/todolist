package com.android.todolist;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String text;
    private int priority;

    public Note(int id, String text, int priority) {
        this.id = id;
        this.text = text;
        this.priority = priority;
    }

    @Ignore
    public Note(String text, int priority) {
        this(0,text, priority);
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getPriority() {
        return priority;
    }

    public int getColor() {
        int bg;
        switch (getPriority()){
            case 0:
                return android.R.color.holo_green_light;
            case 1:
                return android.R.color.holo_orange_light;
            case 2:
            default:
                return android.R.color.holo_red_light;
        }
    }
}
