package kcltech.kcltechtodo

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edit_task.*
import android.widget.Toast
import org.joda.time.DateTime




class EditTaskActivity : AppCompatActivity() {
    private var mViewModel: TaskViewModel? = null

    //activity state
    private var createNew = true
    private var editId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)
        mViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)

        // Get extras if task to be edited
        val extras = intent.extras
        if (extras != null && extras.containsKey("task_id")) {
            createNew = false
            editId = extras.getLong("task_id")
        }

        save.setOnClickListener({ v -> saveButtonClicked()})

        // Try to load task.
        loadTask()
    }
    /*
    If we are loading a task
     */

    private fun loadTask() {
        if (!createNew) {
            val task = mViewModel!!.getTask(editId)
            taskLoaded(task)
        }
    }

    private fun taskLoaded(t: TaskData?) {
        if (t ==
                null) {
            Toast.makeText(applicationContext, R.string.editTaskActivityLoadFailed, Toast.LENGTH_LONG).show()
        } else {
            titleInput.setText(t.title)
            notesInput.setText(t.notes)
            dateInput.init(
                    t.dueDate.year,
                    t.dueDate.monthOfYear -1,
                    t.dueDate.dayOfMonth, null
            )
        }
    }

    /*
       This is called when the save button is clicked
     */

    private fun saveButtonClicked() {
        val title = titleInput.text.toString().trim()
        val notes = notesInput.text.toString().trim()
        val dueDate = DateTime(
                dateInput.year,
                dateInput.month + 1,
                dateInput.dayOfMonth,
                23, 59, 59
        )

        // check title
        if (title.length == 0) {
            Toast.makeText(applicationContext, R.string.editTasKActivityNoTitleError, Toast.LENGTH_LONG).show()
            return
        }

        //check date
        if (dueDate.isBeforeNow) {
            Toast.makeText(applicationContext, R.string.editTasKActivityPastDateError, Toast.LENGTH_LONG).show()
            return
        }
        val task : TaskData
        //make a new task object
        if(createNew) {
            task = TaskData(null, title, notes, false, dueDate)
        } else {
            task = TaskData(editId, title, notes, false, dueDate)
        }
        mViewModel!!.addTask(task)
        Toast.makeText(applicationContext, R.string.editTasKActivityTaskSaved, Toast.LENGTH_LONG).show()
        // Return to list
        finish()

    }
}
