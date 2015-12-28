package org.snake.android.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    ArrayList<ImplementCustomAdapter> customItems;
    UsersAdapter customItemsadapter;
    EditText etEditText;
    public String newItemStr;


    @Override //On create method
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        populateArrayItems();
        etEditText = (EditText) findViewById(R.id.etEditText);

    }

    //Method to populate the custom list array and when a specific list item is clicked
    public void populateArrayItems()
    {
        customItems = new ArrayList<ImplementCustomAdapter>();
        customItemsadapter = new UsersAdapter(this,customItems);
        ImplementCustomAdapter todoEntry1 = new ImplementCustomAdapter("BasketBall", "      High");
        ImplementCustomAdapter todoEntry2 = new ImplementCustomAdapter("Halo3", "     Low");
        ImplementCustomAdapter todoEntry3 = new ImplementCustomAdapter("Breakfast", "      Medium");
        customItemsadapter.add(todoEntry1);
        customItemsadapter.add(todoEntry2);
        customItemsadapter.add(todoEntry3);
        ListView customlvlItems = (ListView) findViewById(R.id.lvlItems);
        customlvlItems.setAdapter(customItemsadapter);
        customlvlItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentEditItem = new Intent(MainActivity.this, EditItemActivity.class);
                intentEditItem.putExtra("mainActivityEditItem", customItemsadapter.getItem(position).todoItem);
                intentEditItem.putExtra("mainActivityEditItemPriority", customItemsadapter.getItem(position).todoPriority);
                intentEditItem.putExtra("mainActivityEditItemPosition", position);
                startActivityForResult(intentEditItem, position);
                writeItems();
            }
        });
     //   readItems();

   //     itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
    }


    //method implements when you add a new item. Also implements the priority funcaitonality
    public void onAddItem (View view)
    {
        newItemStr = etEditText.getText().toString();
        if(newItemStr.trim().equals(""))
        {
            Toast.makeText(this,"This field cannot be blank",Toast.LENGTH_SHORT).show();
        }
        else
        {
            //     writeItems();
            final AlertDialog.Builder priority = new AlertDialog.Builder(this);
            priority.setTitle("Priority");
            String [] p1 = new String[]{
                    "High",
                    "Low",
                    "Medium"
            };
            priority.setSingleChoiceItems(p1, 1, null);
            DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AlertDialog alert = (AlertDialog)dialog;
                    int position = alert.getListView().getCheckedItemPosition();
                    String priorityPosition;
                    switch (position)
                    {
                        case 0 : priorityPosition = "         High";
                            break;
                        case 1 : priorityPosition = "         Low";
                            break;
                        case 2 : priorityPosition = "         Medium";
                            break;
                        default: priorityPosition = "";
                            break;

                    }
                    ImplementCustomAdapter temp = new ImplementCustomAdapter(newItemStr, priorityPosition);
                    customItemsadapter.add(temp);
                    etEditText.setText("");
                }
            };
            priority.setPositiveButton("Ok", positiveListener);
            priority.setNegativeButton("Cancel", null);
            priority.show();

        }
        }

    //currently not working
    private void readItems()
    {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");

//        try{
//          // customItems = new ArrayList<ImplementCustomAdapter>(FileUtils.readLines(file));
//        } catch (IOException e)
//        {
//            Log.d("TodoApp","Input File not readiable");
//    }
    }

    //currently not working
    private void writeItems()
    {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{

            FileUtils.writeLines(file, customItems);
        } catch (IOException e)
        {
            Log.d("TodoApp","Write File not readiable");
        }
    }

    //Implements modifying an todoItem functionality
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            int flag = data.getExtras().getInt("flag");
            int position = data.getExtras().getInt("editedItemPosition");
            if (flag == 0) {
                String name = data.getExtras().getString("editedItemEditItemActivty");
                String newPriority = data.getExtras().getString("editItemPriority");
                ImplementCustomAdapter temp = new ImplementCustomAdapter(name,"       "+newPriority);
                customItemsadapter.remove(customItemsadapter.getItem(position));
                customItemsadapter.insert(temp,position);
            }
           else if (flag ==1)
            {
                customItemsadapter.remove(customItemsadapter.getItem(position));
            }
         //   writeItems();
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
