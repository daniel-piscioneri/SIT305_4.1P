package com.example.task41;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TaskListActivity extends AppCompatActivity {
    RecyclerView recyclerViewMain;
    VerticalAdapter vadapter;
    TaskManagerDB taskManagerDB;
    List<Task> taskList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        recyclerViewMain = findViewById(R.id.recyvlerView);
        taskManagerDB = new TaskManagerDB(this);
        createTaskList();

        Button buttonBack = findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        createTaskList();
    }

    private void createTaskList() {
        taskList = taskManagerDB.getAllTasks();
        if (taskList.size()>1)
        {
            recyclerViewMain.setAdapter(vadapter);
        } else{
            Toast.makeText(this, "There is no tasks within the database", Toast.LENGTH_SHORT).show();
        }

//        Log.i("test", "test");
        vadapter = new VerticalAdapter(taskList);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMain.setAdapter(vadapter);
    }

    public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.ViewHolder> {

        private List<Task> taskList;

        public VerticalAdapter(List<Task> taskList) {
            this.taskList = taskList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView titleTextView;
            private TextView dueDateTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                titleTextView = itemView.findViewById(R.id.titleTV);
                dueDateTextView = itemView.findViewById(R.id.dueDateTV);
            }

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Task task = taskList.get(position);
            holder.titleTextView.setText(task.getTitle());
            holder.dueDateTextView.setText(task.getDueDate());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), TaskDetails.class);
                    intent.putExtra("id", task.getId());
                    v.getContext().startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount() {
            if (this.taskList == null) {
                return 0;
            }
            return this.taskList.size();
        }
    }

    public interface onItemClickListener {
        void itemClick(Task task);
    }
}



