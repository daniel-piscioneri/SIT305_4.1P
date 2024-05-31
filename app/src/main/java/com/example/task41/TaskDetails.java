package com.example.task41;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class TaskDetails extends AppCompatActivity {
    TaskManagerDB taskManagerDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        TextView Id = findViewById(R.id.TaskDetailsID);
        TextView Title = findViewById(R.id.TaskDetailsTitle);
        TextView Desc = findViewById(R.id.TaskDetailsDesc);
        TextView DueDate = findViewById(R.id.TaskDetailsDueDate);
        EdgeToEdge.enable(this);

        taskManagerDB = new TaskManagerDB(this);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id"))
        {
            String taskid = intent.getStringExtra("id");
            Task task = GetTaskById(Integer.parseInt(taskid));
            if (task != null)
            {
                Id.setText("ID: " + task.getId());
                Title.setText("Title: " + task.getTitle());
                Desc.setText("Desc: " + task.getDescription());
                DueDate.setText("DueDate: " + task.getDueDate());
            } else {
                Toast.makeText(this, "task not found", Toast.LENGTH_SHORT).show();
                finish();
            }

        } else {
            Toast.makeText(this, "no task id provided", Toast.LENGTH_SHORT).show();
            finish();
        }






        Button buttonBack = findViewById(R.id.buttonBackDetails);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button delete = findViewById(R.id.DeleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskid = intent.getStringExtra("id");
//                Task task = GetTaskById(Integer.parseInt(taskid));
                taskManagerDB.deleteOneRow(taskid);
                finish();
            }
        });




    }
    public Task GetTaskById (int taskid)
    {
        List<Task> tasks = taskManagerDB.getAllTasks();

        String taskidstring = String.valueOf(taskid);

        for(Task task : tasks)
        {
            if(task.getId().equals(taskidstring))
            {
                return task;
            }
        }
        return null;
    }
}