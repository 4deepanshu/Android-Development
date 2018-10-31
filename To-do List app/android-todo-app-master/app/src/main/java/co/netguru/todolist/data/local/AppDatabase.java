package co.netguru.todolist.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import co.netguru.todolist.data.local.converters.LocalDateConverter;
import co.netguru.todolist.data.local.model.ChecklistItemDb;
import co.netguru.todolist.data.local.model.TaskDb;

@Database(version = 1, entities = {TaskDb.class, ChecklistItemDb.class})
@TypeConverters(LocalDateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TasksDao tasksDao();

    public abstract ChecklistDao checklistDao();
}
