package com.chen.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import com.chen.app.R;
import com.chen.app.util.PermissionUtilActivity;

/**
 * Created by chen on 2017/4/4.
 */
public class NoteListActivity extends Activity {
    Button btnNewNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notelist);
        Intent intent = new Intent(NoteListActivity.this, PermissionUtilActivity.class);
        startActivity(intent);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnNewNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
