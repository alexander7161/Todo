package kcltech.kcltechtodo

import android.arch.persistence.room.*
import android.content.Context

@Database(entities = arrayOf(TaskData::class), version = 1)
@TypeConverters(TimestampConverter::class)
abstract class TaskDataBase : RoomDatabase() {

    abstract fun taskDataDao(): TaskDataDao

    companion object {
        private var INSTANCE: TaskDataBase? = null

        fun getInstance(context: Context): TaskDataBase {
            if (INSTANCE == null) {
                synchronized(TaskDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDataBase::class.java, "tasks-db")
                            .build()
                }
            }
            return INSTANCE as TaskDataBase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}