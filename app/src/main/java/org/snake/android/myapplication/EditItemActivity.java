package org.snake.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText editItemActivityEditText;
    int editItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String EditItem = getIntent().getStringExtra("mainActivityEditItem");
        editItemPosition = getIntent().getIntExtra("mainActivityEditItemPosition",1);
        editItemActivityEditText = (EditText) findViewById(R.id.editItemActivityEditText);
        editItemActivityEditText.setText(EditItem);

    }

    public void btneditItemSave(View view)
    {
        editItemActivityEditText = (EditText) findViewById(R.id.editItemActivityEditText);
        Intent resultbackMainActivity = new Intent();
        resultbackMainActivity.putExtra("flag",0);
        resultbackMainActivity.putExtra("editedItemEditItemActivty",editItemActivityEditText.getText().toString());
        resultbackMainActivity.putExtra("editedItemPosition",editItemPosition);
        setResult(RESULT_OK, resultbackMainActivity);
        this.finish();
    }

    public void deleteItem (View view)
    {   Intent resultbackMainActivity = new Intent();
        resultbackMainActivity.putExtra("flag",1);
        resultbackMainActivity.putExtra("editedItemPosition",editItemPosition);
        setResult(RESULT_OK, resultbackMainActivity);
        this.finish();
    }
}
