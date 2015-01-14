package com.styliii.mytodos.models;

import com.activeandroid.annotation.Table;
import android.app.LauncherActivity;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;


import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Table(name = "TodoItems")
public class TodoItem extends Model{
    // This is a regular field
    @Column(name = "Description")
    public String description;

    @Column(name = "ListIndex")
    public int listIndex;

    // Make sure to have a default constructor for every ActiveAndroid model
    public TodoItem(){
        super();
    }

    public TodoItem(String description, int listIndex ){
        super();
        this.description = description;
        this.listIndex = listIndex;
    }

    public static TodoItem getByListIndex(int listIndex){
        return new Select()
                .from(TodoItem.class)
                .where("ListIndex = ?", listIndex)
                .executeSingle();
    }

    public static ArrayList<TodoItem> allSortedItems(){
        return new Select()
                .from(TodoItem.class)
                .orderBy("ListIndex ASC")
                .execute();
    }
}
