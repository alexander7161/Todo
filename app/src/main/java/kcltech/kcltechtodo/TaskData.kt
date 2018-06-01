package kcltech.kcltechtodo

import android.arch.persistence.room.*
import org.joda.time.DateTime


@Entity(tableName = "tasks")
data class TaskData(@PrimaryKey(autoGenerate = true) var id: Long?,
                       @ColumnInfo(name = "title") var title: String,
                       @ColumnInfo(name = "notes") var notes: String,
                       @ColumnInfo(name = "isComplete") var isComplete: Boolean,
                       @ColumnInfo(name = "dueDate") var dueDate: DateTime)