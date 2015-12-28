package org.snake.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvlItems;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        populateArrayItems();
        lvlItems = (ListView) findViewById(R.id.lvlItems);
        lvlItems.setAdapter(itemsAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvlItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentEditItem = new Intent(MainActivity.this,EditItemActivity.class);
                intentEditItem.putExtra("mainActivityEditItem", itemsAdapter.getItem(position));
                intentEditItem.putExtra("mainActivityEditItemPosition",position);
                startActivityForResult(intentEditItem, position);
                writeItems();
            }
        });

    }


    public void populateArrayItems()
    {
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
    }

    public void onAddItem (View view)
    {
        String str;
        str = etEditText.getText().toString();
        if(str.trim().equals(""))
        {
            Toast.makeText(this,"This field cannot be blank",Toast.LENGTH_SHORT).show();
        }
        else
        {
            itemsAdapter.add(str);
            etEditText.setText("");
            writeItems();

        }
    }

    private void readItems()
    {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e)
        {
            Log.d("TodoApp","Input File not readiable");
        }
    }

    private void writeItems()
    {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(file, items);
        } catch (IOException e)
        {
            Log.d("TodoApp","Write File not readiable");
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            int flag = data.getExtras().getInt("flag");
            int position = data.getExtras().getInt("editedItemPosition");
            if (flag == 0) {
                String name = data.getExtras().getString("editedItemEditItemActivty");
                itemsAdapter.remove(itemsAdapter.getItem(position));
                itemsAdapter.insert(name, position);
                Log.d("Edit", "Position is this" + position +"and Flag is" + flag);

            }
           else if (flag ==1)
            {
                Log.d("Delete","Position is this" + position + "and Flag is" + flag);
                itemsAdapter.remove(itemsAdapter.getItem(position));
            }
            writeItems();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
