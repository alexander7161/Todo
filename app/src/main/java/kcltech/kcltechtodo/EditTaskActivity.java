package kcltech.kcltechtodo;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.joda.time.DateTime;

public class EditTaskActivity extends AppCompatActivity {

    private TaskViewModel mViewModel;

    //View components
    private EditText titleInput;
    private EditText notesInput;
    private DatePicker dateInput;

    //activity state
    private boolean createNew = true;
    private long editId = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if(extras !=  null && extras.containsKey("task_id"))
        {
            createNew = false;
            editId = extras.getLong("task_id");
        }

        setContentView(R.layout.activity_edit_task);
        mViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);


        titleInput = findViewById(R.id.titleInput);
        notesInput = findViewById(R.id.notesInput);
        dateInput = findViewById(R.id.dateInput);
        Button saveButton = findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButtonClicked();
            }
        });

        loadTask();
    }


    /*
    If we are loading a task
     */

    private void loadTask()
    {
        if(!createNew)
        {
            final TaskData task = mViewModel.getTask(editId);
            taskLoaded(task);
        }
    }

    private void taskLoaded(TaskData t)
    {
        if (t == null)
        {
            Toast.makeText(getApplicationContext(), R.string.editTaskActivityLoadFailed, Toast.LENGTH_LONG).show();
        }
        else{
            titleInput.setText(t.getTitle());
            notesInput.setText(t.getNotes());
            dateInput.init(
                    t.getDueDate().getYear(),
                    t.getDueDate().getMonthOfYear(),
                    t.getDueDate().getDayOfMonth(),
                    null
            );
        }
    }

    /*
       This is called when the save button is clicked
     */

    private void saveButtonClicked()
    {
        String title = titleInput.getText().toString().trim();
        String notes= notesInput.getText().toString().trim();
        DateTime dueDate = new DateTime(
                dateInput.getYear(),
                dateInput.getMonth() + 1,
                dateInput.getDayOfMonth(),
                23,59,59
        );

        // check title
        if(title.length() == 0)
        {
            Toast.makeText(getApplicationContext(), R.string.editTasKActivityNoTitleError,Toast.LENGTH_LONG).show();
            return;
        }

        //check date
        if(dueDate.isBeforeNow())
        {
            Toast.makeText(getApplicationContext(), R.string.editTasKActivityPastDateError,Toast.LENGTH_LONG).show();
            return;
        }

        //make a new task object

        final TaskData task = new TaskData(editId,title,notes,false,dueDate);


        mViewModel.addTask(task);
        Toast.makeText(getApplicationContext(), R.string.editTasKActivityTaskSaved,Toast.LENGTH_LONG).show();

    }


}
