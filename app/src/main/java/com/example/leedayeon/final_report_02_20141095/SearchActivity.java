package com.example.leedayeon.final_report_02_20141095;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by LeeDaYeon on 2016-06-26.
 */
public class SearchActivity extends AppCompatActivity {
    RadioButton rbYear;
    RadioButton rbMonth;
    EditText etSearch;
    ImageButton ibSearch;

    MyDiaryDBHelper myDBHelper;
    private ArrayList<MyDiary> myData;
    private MyDiaryAdapter myAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        rbYear = (RadioButton) findViewById(R.id.rbYear);
        rbMonth = (RadioButton) findViewById(R.id.rbMonth);
        etSearch = (EditText) findViewById(R.id.editTextSearch);
        ibSearch = (ImageButton)findViewById(R.id.imageButtonSearch);
        listView = (ListView)findViewById(R.id.listView2);
        myDBHelper = new MyDiaryDBHelper(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonSearch:
                if(etSearch.getText().toString().equals("") || etSearch.getText().toString().equals("")) {
                    Toast.makeText(SearchActivity.this, "검색할 값을 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    int inputYear = Integer.valueOf(etSearch.getText().toString());
                    int inputMonth = Integer.valueOf(etSearch.getText().toString());
                    if (rbYear.isChecked() && (inputYear >= 1900)) { //연도별 검색
                        myData = new ArrayList<MyDiary>();
                        SQLiteDatabase db = myDBHelper.getReadableDatabase();

                        String[] columns = {"_id", "date", "title", "content", "place", "weather", "year", "month", "day"};
                        String selection = "year=?";
                        String[] selectArgs = new String[]{etSearch.getText().toString()};
                        Cursor cursor = db.query(MyDiaryDBHelper.TABLE_NAME, columns, selection, selectArgs, null, null, null, null);

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
                            myData.add(newItem);
                        }
                        cursor.close();
                        myDBHelper.close();

                        if(myData.size() == 0) {Toast.makeText(SearchActivity.this, "검색결과가 없습니다", Toast.LENGTH_SHORT).show();}
                        myAdapter = new MyDiaryAdapter(this, R.layout.diary_view, myData);
                        listView.setAdapter(myAdapter);
                    } else if (rbMonth.isChecked() && (inputMonth >= 1 && inputMonth <= 12)) { //월별검색
                        myData = new ArrayList<MyDiary>();
                        SQLiteDatabase db = myDBHelper.getReadableDatabase();

                        String[] columns = {"_id", "date", "title", "content", "place", "weather", "year", "month", "day"};
                        String selection = "month=?";
                        String[] selectArgs = new String[]{etSearch.getText().toString()};
                        Cursor cursor = db.query(MyDiaryDBHelper.TABLE_NAME, columns, selection, selectArgs, null, null, null, null);

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
                            myData.add(newItem);
                        }
                        cursor.close();
                        myDBHelper.close();

                        if(myData.size() == 0) {Toast.makeText(SearchActivity.this, "검색결과가 없습니다", Toast.LENGTH_SHORT).show();}
                        myAdapter = new MyDiaryAdapter(this, R.layout.diary_view, myData);
                        listView.setAdapter(myAdapter);
                    } else {
                        Toast.makeText(SearchActivity.this, "검색할 값을 제대로 입력하세요", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.button2:
                finish();
                break;
        }
    }
}


