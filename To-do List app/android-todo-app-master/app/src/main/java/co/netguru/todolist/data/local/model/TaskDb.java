package co.netguru.todolist.data.local.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

import org.threeten.bp.LocalDate;

@Entity(tableName = "task")
public class TaskDb {

    @PrimaryKey(autoGenerate = true)
    private final long id;

    private final String title;

    private final String description;

    @ColumnInfo(name = "is_done")
    private final boolean isDone;

    @Nullable
    @ColumnInfo(name = "due_date")
    private final LocalDate dueDate;

    public TaskDb(long id, String title, String description, boolean isDone, @Nullable LocalDate dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isDone = isDone;
        this.dueDate = dueDate;
    }

    public long getId() {
        return id;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Nullable
    public LocalDate getDueDate() {
        return dueDate;
    }
}
