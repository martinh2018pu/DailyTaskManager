package com.example.dailytaskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ModifyTask extends DbActivity {

    private TextView taskIdTxt;
    private EditText taskDescriptionTxt;
    private EditText taskDateTxt;

    private String taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_task);

        taskIdTxt = findViewById(R.id.taskIdTxt);
        taskDescriptionTxt = findViewById(R.id.taskDescriptionTxt);
        taskDateTxt = findViewById(R.id.taskDateTxt);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            taskId = bundle.getString("taskId");
            String taskDescription = bundle.getString("taskDescription");
            String taskDate = bundle.getString("taskDate");

            if (taskId != null) {
                taskIdTxt.setText(taskId);
            }
            if (taskDescription != null) {
                taskDescriptionTxt.setText(taskDescription);
            }
            taskDateTxt.setText(taskDate);
        }
    }

    public void SaveTask(View view) {
        try {
            if (taskId != null) {
                ExecuteDbQuery(
                        "UPDATE TASK SET DESCRIPTION = ?, DATE = ? WHERE ID = ? ",
                        new Object[]{
                                taskDescriptionTxt.getText().toString(),
                                taskDateTxt.getText().toString(),
                                taskIdTxt.getText().toString()
                        }
                );
            } else {
                InsertInDb(
                        taskDescriptionTxt.getText().toString(),
                        taskDateTxt.getText().toString()
                );
            }
        } catch (Exception e) {
            ToastShowLong(this, "Save task error: " + e.getMessage());
        }

        ToastShowLong(this, "Save task successful.");

        BackToMainActivity();
    }

    public void DeleteTask(View view) {
        try {
            ExecuteDbQuery(
                    "DELETE FROM TASK WHERE ID = ? ",
                    new Object[]{
                            taskIdTxt.getText().toString()
                    }
            );
        } catch (Exception e) {
            ToastShowLong(this, "Delete task error: " + e.getMessage());
        }

        ToastShowLong(this, "Delete task successful.");

        BackToMainActivity();
    }

    private void BackToMainActivity() {
        finishActivity(200);

        Intent intent = new Intent(ModifyTask.this, MainActivity.class);

        startActivity(intent);
    }
}