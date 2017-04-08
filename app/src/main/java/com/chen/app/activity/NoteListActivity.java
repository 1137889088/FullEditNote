package com.chen.app.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.chen.app.R;
import com.chen.app.db.NoteDB;
import com.chen.app.domain.NoteInfoBean;
import com.chen.app.util.PermissionUtilActivity;
import com.chen.app.view.ScollerLeftDeleteListView;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends Activity {
    NoteDB db;
    private SQLiteDatabase readableDatabase;
    private SQLiteDatabase writableDatabase;
    private List<NoteInfoBean> mData;
    private ScollerLeftDeleteListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notelist);
        db = new NoteDB(this);
        readableDatabase = db.getReadableDatabase();
        writableDatabase = db.getWritableDatabase();
        mData = queryAll();
   /*     for (NoteInfoBean mDatum : mData) {
            insert(mDatum);
        }*/
        FloatingActionButton fabNewNote = (FloatingActionButton) findViewById(R.id.btnNewNote);
        Intent intent = new Intent(NoteListActivity.this, PermissionUtilActivity.class);
        startActivity(intent);
        fabNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteListActivity.this, EditActivity.class);
                intent.putExtra("id",-1+"");
                startActivity(intent);
            }
        });

        mListView = (ScollerLeftDeleteListView) findViewById(R.id.list);
        SocllerLeftDeleteListAdapter socllerLeftDeleteListAdapter = new SocllerLeftDeleteListAdapter();
        socllerLeftDeleteListAdapter.setDate(mData);
        mListView.setAdapter(socllerLeftDeleteListAdapter);
    }

    class SocllerLeftDeleteListAdapter extends BaseAdapter {
        private List<NoteInfoBean> itemInfos = null;

        public void setDate(List<NoteInfoBean> itemInfo) {
            this.itemInfos = itemInfo;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (null == convertView) {
                convertView = View.inflate(NoteListActivity.this, R.layout.item, null);
            }
            TextView text_data = (TextView) convertView.findViewById(R.id.text_data);
            TextView text_date = (TextView) convertView.findViewById(R.id.text_date);

            final TextView delete = (TextView) convertView.findViewById(R.id.delete);
            TextView edit = (TextView) convertView.findViewById(R.id.edit);
            if (itemInfos != null) {
                text_data.setText(itemInfos.get(position).getNoteTitle());
                text_date.setText(itemInfos.get(position).getNoteDate());
            }
            final int pos = position;
            delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    delete(mData.get(pos).getNoteId());
                    itemInfos.remove(pos);
                    notifyDataSetChanged();
                    mListView.turnToNormal();
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                /**
                 * Called when a view has been clicked.
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NoteListActivity.this, EditActivity.class);
                    intent.putExtra("id",mData.get(pos).getNoteId());
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }



    public int update(NoteInfoBean noteInfoBean) {
        ContentValues cv = new ContentValues();

        cv.put(NoteDB.COLUM_NAME_NOTES_TITLE, noteInfoBean.getNoteTitle());
        cv.put(NoteDB.COLUM_NAME_NOTES_CONTENT, noteInfoBean.getNoteContext());
        //new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())
        cv.put(NoteDB.COLUM_NAME_NOTES_DATE, noteInfoBean.getNoteDate());
        return writableDatabase.update(NoteDB.TABLE_NAME_NOTES, cv,
                NoteDB.COLUM_NAME_NOTES_ID + "=?",
                new String[]{noteInfoBean.getNoteId()});
    }

    public List<NoteInfoBean> queryAll() {
        List<NoteInfoBean> list = new ArrayList<NoteInfoBean>();
        Cursor query = readableDatabase.query(NoteDB.TABLE_NAME_NOTES, null, null, null, null, null, null);
        while (query.moveToNext()) {
            NoteInfoBean bean = new NoteInfoBean();
            bean.setNoteId(query.getString(0));
            bean.setNoteTitle(query.getString(1));
            bean.setNoteContext(query.getString(2));
            bean.setNoteDate(query.getString(3));
            list.add(bean);
        }
        return list;
    }
    public int delete(String id) {
        List<NoteInfoBean> list = new ArrayList<NoteInfoBean>();
        int delete = writableDatabase.delete(NoteDB.TABLE_NAME_NOTES,NoteDB.COLUM_NAME_NOTES_ID + "=?", new String[]{id});
        return delete;
    }
}
