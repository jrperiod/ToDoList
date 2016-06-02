package com.vitalgroundz.todolist.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.vitalgroundz.todolist.R;
import com.vitalgroundz.todolist.TaskListFragment;
import com.vitalgroundz.todolist.data.Task;
import com.firebase.client.Firebase;

/**
 * Created by jrperiod on 5/19/16.
 */
public class TaskFragment extends Fragment {

    public static final String TASK_KEY = "task_key";

    private Firebase myFirebaseRef;
    private EditText taskDescription;
    private EditText titleName;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getActivity());
        myFirebaseRef = new Firebase(TaskListFragment.FIRE_BASE_URL + Task.TASKS_ROUTE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getArguments() != null) {
            task = (Task) getArguments().getParcelable(TASK_KEY);
            taskDescription.setText(task.getTask());
            titleName.setText(task.getTaskTitle());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_fragment, container, false);
        taskDescription = (EditText) view.findViewById(R.id.task_fullDescription);
        titleName = (EditText) view.findViewById(R.id.taskName);

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
    public void onPause() {
        super.onPause();

        if(task == null || mCallback == null)  {
            return;
        }

        task.setTask(taskDescription.getEditableText().toString());
        task.setTaskTitle(titleName.getEditableText().toString());
        mCallback.onTaskUpdated(task);
    }




}