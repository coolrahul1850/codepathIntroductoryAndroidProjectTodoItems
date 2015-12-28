package org.snake.android.myapplication;

public class Tasks {
    private int _id;
    private String _taskname;

    public Tasks()
    {

    }

    public Tasks(String _taskname) {
        this._taskname = _taskname;
    }

    public void set_taskname(String _taskname) {
        this._taskname = _taskname;
    }

    public int get_id() {
        return _id;
    }

    public String get_taskname() {
        return _taskname;
    }
}
