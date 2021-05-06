package com.example.dailytaskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.CallSuper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends DbActivity {

    private TextView selectedDateTxt;
    private Spinner selectedMonthSpn;
    private ListView selectedTasksList;

    private Calendar selectedCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedDateTxt = findViewById(R.id.selectedDateTxt);

        selectedMonthSpn = findViewById(R.id.selectedMonthSpn);
        selectedTasksList = findViewById(R.id.selectedTasksList);

        InitializeDb();

        selectedCalendar = Calendar.getInstance();
        selectedDateTxt.setText(
                new SimpleDateFormat("dd MMMM yyyy")
                        .format(selectedCalendar.getTime())
        );

        String[] months = new String[]{"януари", "февруари", "март", "април", "май", "юни", "юли", "август", "септември", "октомври", "ноември", "декември"};
        ArrayAdapter<String> monthsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, months);
        selectedMonthSpn.setAdapter(monthsAdapter);

        selectedMonthSpn.setSelection(selectedCalendar.get(Calendar.MONTH));

        selectedMonthSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                SelectMonth();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        InitializeListView();

        selectedTasksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView lvTaskIdTxt = view.findViewById(R.id.lvTaskIdTxt);
                TextView lvTaskDescriptionTxt = view.findViewById(R.id.lvTaskDescriptionTxt);
                TextView lvTaskDateTxt = view.findViewById(R.id.lvTaskDateTxt);

                String taskId = lvTaskIdTxt.getText().toString();
                String taskDescription = lvTaskDescriptionTxt.getText().toString();
                String taskDate = lvTaskDateTxt.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("taskId", taskId);
                bundle.putString("taskDescription", taskDescription);
                bundle.putString("taskDate", taskDate);

                Intent intent = new Intent(
                        MainActivity.this,
                        ModifyTask.class
                );
                intent.putExtras(bundle);

                startActivityForResult(intent, 200, bundle);
            }
        });
    }

    @Override
    @CallSuper
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        InitializeListView();
    }

    public void SelectPreviousDate(View view) {
        selectedCalendar.add(Calendar.DATE, -1);

        selectedMonthSpn.setSelection(selectedCalendar.get(Calendar.MONTH));

        selectedDateTxt.setText(
                new SimpleDateFormat("dd MMMM yyyy")
                        .format(selectedCalendar.getTime())
        );

        InitializeListView();
    }

    public void SelectNextDate(View view) {
        selectedCalendar.add(Calendar.DATE, 1);

        selectedMonthSpn.setSelection(selectedCalendar.get(Calendar.MONTH));

        selectedDateTxt.setText(
                new SimpleDateFormat("dd MMMM yyyy")
                        .format(selectedCalendar.getTime())
        );

        InitializeListView();
    }

    public void CreateNewTask(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(
                "taskDate",
                new SimpleDateFormat("dd/MM/yyyy").format(selectedCalendar.getTime())
        );

        Intent intent = new Intent(
                MainActivity.this,
                ModifyTask.class
        );
        intent.putExtras(bundle);

        startActivityForResult(intent, 200, bundle);
    }

    private void SelectMonth() {
        int selectedPosition = selectedMonthSpn.getSelectedItemPosition();

        selectedCalendar.set(Calendar.MONTH, selectedPosition);

        selectedDateTxt.setText(
                new SimpleDateFormat("dd MMMM yyyy")
                        .format(selectedCalendar.getTime())
        );

        InitializeListView();
    }

    private void InitializeListView() {
        try {
            ArrayList<Task> tasks = SelectFromDb(
                    "SELECT * FROM TASK WHERE DATE = ? ",
                    new String[]{
                            new SimpleDateFormat("dd/MM/yyyy").format(selectedCalendar.getTime())
                    }
            );

            TasksAdapter tasksAdapter = new TasksAdapter(this, tasks);

            selectedTasksList = findViewById(R.id.selectedTasksList);
            selectedTasksList.clearChoices();
            selectedTasksList.setAdapter(tasksAdapter);
        } catch (Exception e) {
            ToastShowLong(this, "Initialize list view error: " + e.getMessage());
        }

        //ToastShowLong(this, "Initialize list view successful.");
    }
}