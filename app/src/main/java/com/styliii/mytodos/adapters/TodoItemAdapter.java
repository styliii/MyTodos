package com.styliii.mytodos.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.styliii.mytodos.R;
import com.styliii.mytodos.models.TodoItem;

import java.util.ArrayList;

public class TodoItemAdapter extends ArrayAdapter<TodoItem> {

    public TodoItemAdapter(Context context, ArrayList<TodoItem> todoItems) {
        super(context, 0, todoItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoItem todoItem = TodoItem.getByListIndex(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
        TextView tvListIndex = (TextView) convertView.findViewById(R.id.tvListIndex);
        TextView tvDueDate = (TextView) convertView.findViewById(R.id.tvDueDate);

        tvDescription.setText(todoItem.description);
        tvListIndex.setText(todoItem.listIndex + 1 + "");
        tvDueDate.setText(todoItem.dueDate);

        return convertView;
    }

    public static void persistListIndex(ArrayList<TodoItem> todoItems){
        ActiveAndroid.beginTransaction();
        try {
            int index = 0;
            for (TodoItem t : todoItems){
                t.listIndex = index;
                t.save();
                index++;

            }
            ActiveAndroid.setTransactionSuccessful();

        } finally {
            ActiveAndroid.endTransaction();
        }
    }
}
