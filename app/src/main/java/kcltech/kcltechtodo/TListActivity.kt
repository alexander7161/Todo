package kcltech.kcltechtodo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_task_list.*


class TListActivity : AppCompatActivity() {

    private var mViewModel: TaskViewModel? = null
    private var listAdapter: TListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        mViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)
        setTitle(R.string.taskListActivityTitle)

        listAdapter = TListAdapter(arrayListOf(), this)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = listAdapter

        mViewModel!!.getCurrentData().observe(this, Observer { tasks ->
            listAdapter!!.addTasks(tasks!!)
        })

        fab.setOnClickListener {
            val intent = Intent(applicationContext, EditTaskActivity::class.java)
            startActivity(intent)
        }


    }
}
