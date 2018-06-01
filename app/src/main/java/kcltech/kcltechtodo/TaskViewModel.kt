package kcltech.kcltechtodo

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import org.jetbrains.annotations.NotNull
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import kotlinx.android.synthetic.main.activity_task_list.view.*


class TaskViewModel(@NotNull application: Application) : AndroidViewModel(application) {
    var listTasks: LiveData<List<TaskData>>
    private val appDb: TaskDataBase

    init {
        appDb = TaskDataBase.getInstance(this.getApplication())
        listTasks = appDb.taskDataDao().getIncompleteTasks()
    }

    fun getCurrentData(): LiveData<List<TaskData>> {
        return listTasks
    }

    fun getTask(taskId: Long) : TaskData {
        return  getAsynTask(appDb).execute(taskId).get()
    }

    fun addTask(task: TaskData) {
        addAsynTask(appDb).execute(task)
    }


    class addAsynTask(db: TaskDataBase) : AsyncTask<TaskData, Void, Void>() {
        private var taskDb = db
        override fun doInBackground(vararg params: TaskData): Void? {
            taskDb.taskDataDao().insert(params[0])
            return null
        }

    }
    class getAsynTask(db: TaskDataBase) : AsyncTask<Long, Void, TaskData>() {
        private var taskDb = db
        override fun doInBackground(vararg p0: Long?): TaskData {
            return taskDb.taskDataDao().getTask(p0[0])
        }
    }

}