package com.styliii.mytodos.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.styliii.mytodos.R;
import com.styliii.mytodos.adapters.TodoItemAdapter;
import com.styliii.mytodos.models.TodoItem;

import java.util.ArrayList;


public class todosListActivity extends ActionBarActivity {
    TodoItemAdapter todoAdapter;
    ListView lvItems;
    ArrayList<TodoItem> items;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_list);

        //Construct data source
        items = TodoItem.allSortedItems();
        // Create the adapter to convert array to views
        todoAdapter = new TodoItemAdapter(this, items);
        // Attach adapter to ListView
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(todoAdapter);

        setupListViewListener();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todos_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        int newTodoItemIndex = items.size();

        TodoItem todoItem = new TodoItem();
        todoItem.description = itemText;
        todoItem.listIndex = newTodoItemIndex;
        todoItem.save();

        todoAdapter.add(todoItem);
        etNewItem.setText("");
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        TodoItem todoItem = TodoItem.getByListIndex(pos);
                        items.remove(pos);
                        todoItem.delete();
                        todoAdapter.persistListIndex(items);
                        todoAdapter.notifyDataSetChanged();
                        return true;
                    }
                }
        );
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                        launchEditItem(pos);
                    }
                }
        );
    }

    public void launchEditItem(int pos) {
        TodoItem todoItem = items.get(pos);
        Intent i = new Intent(todosListActivity.this, EditTodoActivity.class);
        i.putExtra("listIndex", "" + todoItem.listIndex);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            todoAdapter.notifyDataSetChanged();
        }
    }
}
