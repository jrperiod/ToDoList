package com.vitalgroundz.todolist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.vitalgroundz.todolist.data.Task;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class TaskListFragment extends Fragment {

    public static final String FIRE_BASE_URL = "https://blistering-heat-8433.firebaseio.com/";


    private OnTaskListFragmentListener mCallback;

    private Firebase myFirebaseRef;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private FloatingActionButton fab;

    public TaskListFragment() {

    }

    // Container Activity must implement this interface
    public interface OnTaskListFragmentListener {
        void onTaskSelected(Task task);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnTaskListFragmentListener) getActivity();
            TaskAdapter.ViewHolder.setOnTaskListFragmentListner(mCallback);
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnTaskListFragmentlistener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(getActivity());
        myFirebaseRef = new Firebase(FIRE_BASE_URL + Task.TASKS_ROUTE);
        myFirebaseRef.addValueEventListener(valueEventListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout =  inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.toDoListRecyclerView);

        fab = (FloatingActionButton) layout.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("flow", "FAB CLICKED");
                addTask();
            }
        });
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void loadTasks(ArrayList<Task> tasks) {

        if(tasks == null) {
            return;
        }

        adapter = new TaskAdapter(tasks);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void saveTask(Task task) {
//        Previously written save
//        myFirebaseRef.push().setValue(task);

        Firebase newRef = myFirebaseRef.push();
        String taskId = newRef.getKey();
        task.setTaskId(taskId);
        newRef.setValue(task);
    }

    private void addTask() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Create Task");
        builder.setMessage("What do you want to do?");
        final EditText inputField = new EditText(getActivity());
        builder.setView(inputField);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Add Task", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveTask(new Task(inputField.getText().toString(), inputField.getText().toString()));
            }
        });

        builder.create().show();
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            ArrayList<Task> tasks = new ArrayList<>();

            for (DataSnapshot taskSnapshot: dataSnapshot.getChildren()) {
                Task task = taskSnapshot.getValue(Task.class);
                tasks.add(task);
                System.out.println(task.getTaskTitle() + " - " + task.getTask());
            }

            loadTasks(tasks);
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    };
}