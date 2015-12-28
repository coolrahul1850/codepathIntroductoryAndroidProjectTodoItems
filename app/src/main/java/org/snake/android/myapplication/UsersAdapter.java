package org.snake.android.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UsersAdapter extends ArrayAdapter<ImplementCustomAdapter> {
    public UsersAdapter(Context context, ArrayList<ImplementCustomAdapter> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ImplementCustomAdapter user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.implementcustomadapter, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.todoItemName);
        TextView tvHome = (TextView) convertView.findViewById(R.id.todoPriority);
        // Populate the data into the template view using the data object
        tvName.setText(user.todoItem);
        tvHome.setText(user.todoPriority);
        // Return the completed view to render on screen
        return convertView;
    }

}
