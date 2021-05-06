package com.example.dailytaskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TasksAdapter extends ArrayAdapter<Task> {

    public TasksAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_simplest_list_view, parent, false);
        }

        TextView taskID = (TextView) convertView.findViewById(R.id.lvTaskIdTxt);
        TextView taskDescription = (TextView) convertView.findViewById(R.id.lvTaskDescriptionTxt);
        TextView taskDate = (TextView) convertView.findViewById(R.id.lvTaskDateTxt);

        taskID.setText(task.TaskID);
        taskDescription.setText(task.TaskDescription);
        taskDate.setText(task.TaskDate);

        return convertView;
    }
}
