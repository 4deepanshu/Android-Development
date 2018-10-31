package co.netguru.todolist.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.threeten.bp.LocalDate;

import java.util.List;

import co.netguru.todolist.data.local.model.TaskDb;
import io.reactivex.Flowable;

@Dao
public interface TasksDao {

    @Insert
    long insertTask(TaskDb taskDb);

    @Update
    int updateTask(TaskDb taskDb);

    @Query("SELECT * FROM task WHERE is_done = 0")
    Flowable<List<TaskDb>> getToDoTasks();

    @Query("SELECT * FROM task WHERE is_done = 1")
    Flowable<List<TaskDb>> getDoneTasks();

    @Query("SELECT * FROM task WHERE is_done = 0 AND due_date < :localDate")
    Flowable<List<TaskDb>> getTaskWithDueDateBefore(LocalDate localDate);

    @Delete
    void deleteTask(TaskDb taskDb);
}
