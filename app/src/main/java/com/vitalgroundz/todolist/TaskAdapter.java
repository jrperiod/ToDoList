package com.vitalgroundz.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vitalgroundz.todolist.data.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    private ArrayList<Task> tasks;

    public TaskAdapter(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private LinearLayout layout;

        public ViewHolder(View view) {
            super(view);

            textView = (TextView) view.findViewById(R.id.taskName);
            layout = (LinearLayout) view.findViewById(R.id.taskRowLayout);
        }

        public TextView getTextView() {
            return textView;
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