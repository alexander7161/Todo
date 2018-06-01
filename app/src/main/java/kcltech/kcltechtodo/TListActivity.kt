package kcltech.kcltechtodo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_task_list.*
import android.content.DialogInterface
import android.support.v7.app.AlertDialog


class TListActivity : AppCompatActivity(), TListAdapter.OnItemClickListener {
    private var db: TaskDataBase? = null
    private var mViewModel: TaskViewModel? = null
    private var taskAdapter: TListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        mViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)
        setTitle(R.string.taskListActivityTitle)

        db = TaskDataBase.getInstance(this)

        taskAdapter = TListAdapter(arrayListOf(), this)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = taskAdapter

        // If liveData changes, update the recyclerView adapter
        mViewModel!!.getCurrentData().observe(this, Observer { tasks ->
            taskAdapter!!.addTasks(tasks!!)
        })

        //Floating action button to add new task.
        fab.setOnClickListener {
            val intent = Intent(applicationContext, EditTaskActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onItemClick(task: TaskData) {
        taskClicked(task)
    }

    private fun taskClicked(task: TaskData) {
        val dialogBuilder = AlertDialog.Builder(this)

        //Positive button - Set task to be completed
        dialogBuilder.setPositiveButton(
                R.string.taskListActivityCompleteButton,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    task.isComplete = true
                    mViewModel!!.addTask(task)
                }
        )

        //Neutral button - Change to Edit Task Activity
        dialogBuilder.setNeutralButton(
                R.string.taskListActivityEditButton,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    val goToEditTask = Intent(applicationContext, EditTaskActivity::class.java)

                    //pass TaskId as an Extra to the new activity. Used to identify whether we are editing
                    //a task or creating a new one
                    goToEditTask.putExtra("task_id", task.id)
                    startActivity(goToEditTask)
                }
        )

        //If the task has a message, display it in the dialog box
        if (task.notes.length > 0) {
            dialogBuilder.setMessage(task.notes)
        }
        dialogBuilder.setCancelable(true)
        val dialog = dialogBuilder.create()
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }
}
