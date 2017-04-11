package com.stevensbytes.steven.househelper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.stevensbytes.steven.househelper.adapter.TaskListAdapter;
import com.stevensbytes.steven.househelper.db.Task;
import com.stevensbytes.steven.househelper.db.TaskDataSource;
import com.stevensbytes.steven.househelper.fragment.AddTaskDialogFragment;

public class MainActivity extends AppCompatActivity{

    private ImageButton addTask;
    private ImageButton audioBtn;
    private ListView taskList;

    private TaskDataSource taskDS;
    private GestureDetectorCompat listDetector;

    public static final int TASK_MSG = 8246;

    public static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViews();
        createListeners();
        createHandlers();
        initLists();
    }

    private void initLists() {
        taskDS = new TaskDataSource(this);
        taskList.setAdapter(new TaskListAdapter(this, taskDS.getAllTasks()));
        ((TaskListAdapter)taskList.getAdapter()).notifyDataSetChanged();
    }

    private void createHandlers() {
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message inMessage){
                switch (inMessage.what){
                    case TASK_MSG:
                        Bundle b = inMessage.getData();
                        String desc = b.getString("desc");
                        long group = b.getLong("group");
                        SaveEvent(desc, group);
                        break;
                    default:
                }
                super.handleMessage(inMessage);
            }
        };
    }

    private void getViews() {
        addTask = (ImageButton)  this.findViewById(R.id.add_task);
        audioBtn = (ImageButton) this.findViewById(R.id.audio_player_btn);
        taskList = (ListView) this.findViewById(R.id.task_list);
    }

    private void createListeners() {
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskDialogFragment newTask = new AddTaskDialogFragment();
                newTask.show(getSupportFragmentManager(), "add_task");
            }
        });
        audioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.super.getBaseContext(), WorkTunesActivity.class);
                startActivity(intent);
            }
        });
        listDetector = new GestureDetectorCompat(this, new ListGestureListener());
        View.OnTouchListener taskTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return listDetector.onTouchEvent(event);
            }
        };
        taskList.setOnTouchListener(taskTouchListener);
    }

    private void SaveEvent(String task_desc, long task_group) {
        TaskDataSource t = new TaskDataSource(this);
        t.open();
        Task tsk = t.createTask(task_desc, task_group);
        t.close();
        ((TaskListAdapter)taskList.getAdapter()).notifyDataSetChanged();
        Toast.makeText(this, R.string.toast_event_saved, Toast.LENGTH_LONG).show();
    }

    class ListGestureListener extends GestureDetector.SimpleOnGestureListener{

        private MotionEvent lastOnDownEvent = null;

        @Override
        public boolean onDown(MotionEvent event) {
            lastOnDownEvent = event;
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            if(event1 == null){
                event1 = lastOnDownEvent;
            }
            if (event2 == null && event1 == null){
                return false;
            }
            float deltaX = event2.getX() - event1.getX();

            if (Math.abs(velocityX) >= 100 && Math.abs(deltaX) >= 150 ) {
                final int pos = taskList.pointToPosition((int)event1.getX(), (int)event1.getY());
                if (pos >= 0) {
                    View listItem = taskList.getAdapter().getView(pos, taskList.getChildAt(pos), taskList);
                    if (deltaX > 0) {
                        listItemAnimator(listItem, pos, true);
                    } else {
                        listItemAnimator(listItem, pos, false);
                    }
                }
                return true;
            }
            return true;
        }
        private void listItemAnimator(View view, int position, boolean right){
            final int pos = position;
            Display display = getWindowManager().getDefaultDisplay();
            view.clearAnimation();
            int trans = 0;
            if (right){
                trans = display.getWidth();
            } else {
                trans = -display.getWidth();
            }
            TranslateAnimation translateAnim = new TranslateAnimation(0, trans, 0, 0);
            translateAnim.setDuration(250);
            translateAnim.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Task task = (Task) taskList.getAdapter().getItem(pos);
                    taskDS.deleteTask(task);
                    ((TaskListAdapter) taskList.getAdapter()).notifyDataSetChanged();
                }
            });
            view.startAnimation(translateAnim);
        }
    }
}
