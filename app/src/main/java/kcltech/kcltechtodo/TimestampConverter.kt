package kcltech.kcltechtodo

import android.arch.persistence.room.TypeConverter
import org.joda.time.DateTime

class TimestampConverter {
    @TypeConverter
    fun fromTimestamp(value: Long): DateTime {
        return DateTime(value)
    }

    @TypeConverter
    fun fromDateTime(value : DateTime) : Long  {
        return value.millis
    }
}