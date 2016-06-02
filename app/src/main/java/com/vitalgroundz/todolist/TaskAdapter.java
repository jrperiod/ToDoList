package com.vitalgroundz.todolist;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vitalgroundz.todolist.data.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrperiod on 5/23/16.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    public static ArrayList<Task> tasks;


    public TaskAdapter(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView textView;
        private LinearLayout layout;

        private static TaskListFragment.OnTaskListFragmentListener mCallback;

        public static void setOnTaskListFragmentListner(TaskListFragment.OnTaskListFragmentListener mCallback) {
            ViewHolder.mCallback = mCallback;
        }

        public ViewHolder(View view) {
            super(view);

            textView = (TextView) view.findViewById(R.id.taskName);
            layout = (LinearLayout) view.findViewById(R.id.taskRowLayout);
            layout.setOnClickListener(this);
        }

        public TextView getTextView() {
            return textView;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.taskRowLayout:
                    if(mCallback != null) {
                        mCallback.onTaskSelected(tasks.get(getAdapterPosition()));
                    }
                    break;
            }
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getTextView().setText(tasks.get(position).getTaskTitle());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


}