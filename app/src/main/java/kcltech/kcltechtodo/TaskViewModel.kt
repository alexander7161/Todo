package kcltech.kcltechtodo

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import org.jetbrains.annotations.NotNull
import android.arch.lifecycle.LiveData
import android.os.AsyncTask


class TaskViewModel(@NotNull application: Application) : AndroidViewModel(application) {
    var listTasks: LiveData<List<TaskData>>
    private val appDb: TaskDataBase

    init {
        appDb = TaskDataBase.getInstance(this.getApplication())
        listTasks = appDb.taskDataDao().getIncompleteTasks()
    }

    fun getCurrentData(): LiveData<List<TaskData>>? {
        return listTasks
    }

    fun getTask(taskId: Long) : TaskData {
        return  appDb.taskDataDao().getTask(taskId)
    }

    fun addTask(task: TaskData) {
        addAsynTask(appDb).execute(task)
    }


    class addAsynTask(db: TaskDataBase) : AsyncTask<TaskData, Void, Void>() {
        private var contactDb = db
        override fun doInBackground(vararg params: TaskData): Void? {
            contactDb.taskDataDao().insert(params[0])
            return null
        }

    }

}