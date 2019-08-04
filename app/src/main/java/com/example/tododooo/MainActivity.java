package com.example.tododooo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tododooo.adapter.ToDoRealmAdapter;
import com.example.tododooo.dao.ToDoItemDao;
import com.example.tododooo.model.ToDoItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private ToDoItemDao toDoItemDao;
    private ToDoRealmAdapter toDoRealmAdapter;
    private RealmRecyclerView realmRecyclerView;
    private FloatingActionButton fabAddToDo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initRealm();
        setUpRecyclerView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        fabAddToDo = findViewById(R.id.fab_add_todo);
        fabAddToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });
    }

    private void initRealm() {
        realm = Realm.getInstance(this);

        if (realm != null) {
            toDoItemDao = new ToDoItemDao(realm);
        }
    }

    private void setUpRecyclerView() {
        realmRecyclerView = findViewById(R.id.realm_recycler_view);

        RealmResults<ToDoItem> toDoItems = toDoItemDao.findAllToDo();
        toDoRealmAdapter = new ToDoRealmAdapter(
                this,
                toDoItems,
                true,
                true
        );
        realmRecyclerView.setAdapter(toDoRealmAdapter);
    }


    private void showInputDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(R.string.create_todo);

        LayoutInflater layoutInflater = LayoutInflater.from(this);

        View dialogView = layoutInflater.inflate(R.layout.dialog_todo_input, null);
        final EditText input = dialogView.findViewById(R.id.et_todo_input);

        builder.setView(dialogView);
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addToDoItem(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = builder.show();

        input.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE ||
                                (event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                            dialog.dismiss();
                            addToDoItem(input.getText().toString());
                            return true;
                        }
                        return false;
                    }
                });
    }

    private void addToDoItem(String toDoItemText) {
        if (toDoItemText == null || toDoItemText.trim().isEmpty()) {
            Toast.makeText(this, "Empty Tododooo is a no-no!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        realm.beginTransaction();

        ToDoItem todoItem = toDoItemDao.createToDo();
        todoItem.setId(System.currentTimeMillis());
        todoItem.setDescription(toDoItemText);

        realm.commitTransaction();
    }


}
