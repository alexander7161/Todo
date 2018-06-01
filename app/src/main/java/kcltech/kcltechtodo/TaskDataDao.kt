package kcltech.kcltechtodo

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE

@Dao
interface TaskDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(taskData: TaskData)

    @Query("DELETE from tasks")
    fun deleteAll()

    @Query("SELECT * FROM tasks WHERE isComplete = 0 ORDER BY dueDate ASC;")
    fun getIncompleteTasks() : LiveData<List<TaskData>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    fun getTask(taskId: Long?) : TaskData
}