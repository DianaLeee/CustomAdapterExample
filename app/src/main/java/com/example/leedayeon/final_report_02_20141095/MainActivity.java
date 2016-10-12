// 과제명: 나의 일기장 앱
// 분반: 02 분반
// 학번: 20141095 성명: 이다연
// 제출일: 2016년 6월 26일
package com.example.leedayeon.final_report_02_20141095;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int REQ_CODE = 100;
    private final int ADD_CODE = 200;
    private ArrayList<MyDiary> myData;
    private MyDiaryAdapter myAdapter;
    private ListView listView;

    MyDiaryDBHelper myDiaryDBHelper;

    int clickDataIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);

        myDiaryDBHelper = new MyDiaryDBHelper(this);

        myData = readDatabase();

        myAdapter = new MyDiaryAdapter(this, R.layout.diary_view, myData);

        listView.setAdapter(myAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("삭제확인");
                builder.setMessage("삭제하시겠습니까?");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDiary currentData = myData.get(i);
                        SQLiteDatabase db = myDiaryDBHelper.getWritableDatabase();

                        String whereClause = "_id=?";
                        String[] whereArgs = new String[] { Integer.valueOf(currentData.get_id()).toString()};

                        int result = db.delete(MyDiaryDBHelper.TABLE_NAME, whereClause, whereArgs);
                        myDiaryDBHelper.close();

                        if (result > 0) {
                            myData.remove(i);
                            myAdapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, "삭제하였습니다.", Toast.LENGTH_SHORT).show();
                        } else  {
                            Toast.makeText(MainActivity.this, "삭제 오류 관리자에게 문의요망", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    clickDataIndex = i;

                    MyDiary currentData = myData.get(i);

                    Intent intent = new Intent(MainActivity.this, UpdateActivity.class);

                    intent.putExtra("update_data", currentData);

                    startActivityForResult(intent, REQ_CODE);

            }
        });
    }

    private ArrayList<MyDiary> readDatabase() {
        ArrayList<MyDiary> list = new ArrayList<MyDiary>();

        SQLiteDatabase db = myDiaryDBHelper.getReadableDatabase();

        String[] columns = {"_id", "date", "title", "content", "place", "weather", "year", "month", "day"};
        Cursor cursor = db.query(MyDiaryDBHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String date = cursor.getString(1);
            String title = cursor.getString(2);
            String content = cursor.getString(3);
            String place = cursor.getString(4);
            String weather = cursor.getString(5);
            int year = cursor.getInt(6);
            int month = cursor.getInt(7);
            int day = cursor.getInt(8);

            MyDiary newItem = new MyDiary(id, content, date, day, month, place, title, weather, year);
            list.add(newItem);
        }
        cursor.close();
        myDiaryDBHelper.close();
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:

                    MyDiary updatedData = myData.get(clickDataIndex);

                    MyDiary returnedData = (MyDiary)data.getSerializableExtra("returned_data");

                    updatedData.setTitle(returnedData.getTitle());
                    updatedData.setDate(returnedData.getDate());
                    updatedData.setPlace(returnedData.getPlace());
                    updatedData.setWeather(returnedData.getWeather());
                    updatedData.setContent(returnedData.getContent());
                    updatedData.setYear(returnedData.getYear());
                    updatedData.setMonth(returnedData.getMonth());
                    updatedData.setDay(returnedData.getDay());

                    myAdapter.notifyDataSetChanged();
                    break;

                case Activity.RESULT_CANCELED:
                    break;
            }
        }
        if(requestCode == ADD_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    myData = readDatabase();
                    myAdapter = new MyDiaryAdapter(this, R.layout.diary_view, myData);
                    listView.setAdapter(myAdapter);
                    break;

                case Activity.RESULT_CANCELED:
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent_add = new Intent(this, AddActivity.class);
                startActivityForResult(intent_add, ADD_CODE);
                break;
            case R.id.action_myself:
                Intent intent_myself = new Intent(this, MyselfActivity.class);
                startActivity(intent_myself);
                break;
            case R.id.action_quit:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("종료확인");
                builder.setMessage("종료하시겠습니까?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();
                break;
            case R.id.action_search:
                Intent intent_seacth = new Intent(this, SearchActivity.class);
                startActivity(intent_seacth);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("종료확인");
        builder.setMessage("종료하시겠습니까?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("취소", null);
        builder.show();
    }
}
