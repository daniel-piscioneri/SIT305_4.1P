package com.example.task41;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button viewButton, Add;
    EditText Title, Desc, DueDate, ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        viewButton = findViewById(R.id.buttonView);
        Add = findViewById(R.id.buttonAdd);
        ID = findViewById(R.id.EditID);
        Title = findViewById(R.id.EditTitle);
        Desc = findViewById(R.id.EditDesc);
        DueDate = findViewById(R.id.EditDueDate);

//        ID.setVisibility(View.GONE);
//        Title.setVisibility(View.GONE);
//        Desc.setVisibility(View.GONE);
//        DueDate.setVisibility(View.GONE);

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
                startActivity(intent);
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringText = Title.getText().toString();
                String stringDesc = Desc.getText().toString();
                String stringdue = DueDate.getText().toString();
                String intID = ID.getText().toString();

                if (stringText.length() <=0 || stringDesc.length() <=0 || stringdue.length() <=0 || intID.length()<=0){
                    Toast.makeText(MainActivity.this, "Enter All Data", Toast.LENGTH_SHORT).show();
                }else {
                    TaskManagerDB databaseHelperClass = new TaskManagerDB(MainActivity.this);
                    Task taskModelClass = new Task(intID,stringText, stringDesc, stringdue);
                    databaseHelperClass.addTask(taskModelClass);
                    Toast.makeText(MainActivity.this, "Add Task Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                }
            }
        });


    }


}