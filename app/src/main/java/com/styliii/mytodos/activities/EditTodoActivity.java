package com.styliii.mytodos.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.styliii.mytodos.R;
import com.styliii.mytodos.models.TodoItem;


public class EditTodoActivity extends ActionBarActivity {
    int itemListIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);

        itemListIndex = Integer.parseInt(getIntent().getStringExtra("listIndex"));
        TodoItem todoItem = TodoItem.getByListIndex(itemListIndex);
        etEditItem.append(todoItem.description);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_todo, menu);
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

    public void onSaveItem(View v) {
        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
        String itemText = etEditItem.getText().toString();
        TodoItem todoItem = TodoItem.getByListIndex(itemListIndex);
        todoItem.description = itemText;
        todoItem.save();
        Intent data = new Intent();
        data.putExtra("listIndex", "" + itemListIndex);
        setResult(RESULT_OK, data);
        finish();
    }
}
