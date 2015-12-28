package org.snake.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {

    EditText editItemActivityEditText;
    int editItemPosition;
    RadioButton ItemPriority;
    String editItemPriority;
    RadioGroup groupItemPriority;
    RadioButton editRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        groupItemPriority = (RadioGroup) findViewById(R.id.radioBtnGroupPriority);
        String EditItem = getIntent().getStringExtra("mainActivityEditItem");
        editItemPosition = getIntent().getIntExtra("mainActivityEditItemPosition",1);
        editItemActivityEditText = (EditText) findViewById(R.id.editItemActivityEditText);
        editItemPriority = getIntent().getStringExtra("mainActivityEditItemPriority").trim();
        editItemActivityEditText.setText(EditItem);
        switch (editItemPriority.toLowerCase()){
            case "high":
                ItemPriority = (RadioButton) findViewById(R.id.radioBtnHigh);
                ItemPriority.setChecked(true);
                break;
            case "low":
                ItemPriority = (RadioButton) findViewById(R.id.radioBtnLow);
                ItemPriority.setChecked(true);
                break;
            case "medium":
                ItemPriority = (RadioButton) findViewById(R.id.radioBtnMedium);
                ItemPriority.setChecked(true);
                break;
            default:
                ItemPriority = (RadioButton) findViewById(R.id.radioBtnHigh);
                ItemPriority.setChecked(true);
                break;
        }

    }

    public void addRadioListenerOnButton()
    {

        int selectedId = groupItemPriority.getCheckedRadioButtonId();
        ItemPriority = (RadioButton) findViewById(selectedId);
        Toast.makeText(this, ItemPriority.getText(), Toast.LENGTH_SHORT).show();

    }
    public void btneditItemSave(View view)
    {
        int temp = groupItemPriority.getCheckedRadioButtonId();
        editRadioButton  = (RadioButton)findViewById(temp);
        editItemActivityEditText = (EditText) findViewById(R.id.editItemActivityEditText);
        Intent resultbackMainActivity = new Intent();
        resultbackMainActivity.putExtra("flag", 0);
        resultbackMainActivity.putExtra("editedItemEditItemActivty", editItemActivityEditText.getText().toString());
        resultbackMainActivity.putExtra("editedItemPosition", editItemPosition);
        resultbackMainActivity.putExtra("editItemPriority", editRadioButton.getText());


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
