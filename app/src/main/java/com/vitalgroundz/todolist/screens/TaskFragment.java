package com.vitalgroundz.todolist.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vitalgroundz.todolist.R;
import com.vitalgroundz.todolist.TaskAdapter;
import com.vitalgroundz.todolist.data.Task;


public class TaskFragment extends Fragment implements View.OnClickListener{

    public static final String TASK_KEY = "task_key";

    private EditText taskDescription;
    private Button saveFragButton;
    private OnTaskFragmentListener mCallback;

    private Task task;

    public interface OnTaskFragmentListener {

        void onTaskUpdated(Task task);
    }

    public static TaskFragment newInstance(Task task) {
        TaskFragment fragment = new TaskFragment();

        Bundle args = new Bundle();
        args.putParcelable(TASK_KEY, task);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getArguments() != null) {
            task = (Task) getArguments().getParcelable(TASK_KEY);
            taskDescription.setText(task.getTask());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_fragment, container, false);

        taskDescription = (EditText) view.findViewById(R.id.task_fullDescription);
        saveFragButton = (Button) view.findViewById(R.id.save_frag_btn);
        saveFragButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnTaskFragmentListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnTaskListFragmentlistener");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_frag_btn:
                task.setTask(taskDescription.getEditableText().toString());
                if(mCallback != null) {
                    mCallback.onTaskUpdated(task);
                }
                break;
        }
    }


}