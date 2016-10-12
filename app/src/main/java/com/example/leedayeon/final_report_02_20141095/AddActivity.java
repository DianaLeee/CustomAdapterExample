package com.example.leedayeon.final_report_02_20141095;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by LeeDaYeon on 2016-06-25.
 */

public class AddActivity extends AppCompatActivity {

    MyDiary myDate;
    MyDiaryDBHelper myDBHelper;

    EditText etTitle;
    TextView etDate;
    EditText etPlace;
    EditText etWeather;
    EditText etContent;

    Button btnInputDate;
    int year_x, month_x, day_x;
    static final int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etTitle = (EditText)findViewById(R.id.editTextAddTitle);
        etDate = (TextView) findViewById(R.id.editTextAddDate);
        etPlace = (EditText)findViewById(R.id.editTextAddPlace);
        etWeather = (EditText)findViewById(R.id.editTextAddWeather);
        etContent = (EditText)findViewById(R.id.editTextAddContent);

        myDBHelper = new MyDiaryDBHelper(this);

        etTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b == true && (etTitle.getText().toString().equals("") || etTitle.getText().toString().equals("제목을 입력하세요"))) {  etTitle.setText("");}
                else if(( b == false ) && ( etTitle.getText().toString().equals("") )){etTitle.setText("제목을 입력하세요");}
            }
        });
        etPlace.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b == true && (etPlace.getText().toString().equals("") || etPlace.getText().toString().equals("장소를 입력하세요"))) {  etPlace.setText("");}
                else if(( b == false ) && ( etPlace.getText().toString().equals("") )){etPlace.setText("장소를 입력하세요");}
            }
        });
        etWeather.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b == true && (etWeather.getText().toString().equals("") || etWeather.getText().toString().equals("날씨를 입력하세요"))) {  etWeather.setText("");}
                else if(( b == false ) && ( etWeather.getText().toString().equals("") )){etWeather.setText("날씨를 입력하세요");}
            }
        });
        etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b == true && (etContent.getText().toString().equals("") || etContent.getText().toString().equals("내용을 입력하세요"))) {  etContent.setText("");}
                else if(( b == false ) && ( etContent.getText().toString().equals("") )){etContent.setText("내용을 입력하세요");}
            }
        });

    }
    public void onClick(View v) {
        String title = etTitle.getText().toString();
        String date = etDate.getText().toString();
        String place = etPlace.getText().toString();
        String weather = etWeather.getText().toString();
        String content = etContent.getText().toString();

        btnInputDate = (Button)findViewById(R.id.btnInputDate);

        switch (v.getId()) {
            case R.id.btnInputDate:
            case R.id. editTextAddDate:
                showDialog(DIALOG_ID);
                break;

            case R.id.buttonAddOK:
                if(title.equals("") || date.equals("") || place.equals("") || weather.equals("") || content.equals("")) {
                    Toast.makeText(AddActivity.this, "항목을 빠짐없이 입력하세요", Toast.LENGTH_SHORT).show();
                } else if(title.equals("제목을 입력하세요") || date.equals("날짜를 입력하세요") ||
                        place.equals("장소를 입력하세요") || weather.equals("날씨를 입력하세요") ||
                        content.equals("내용을 입력하세요")) {
                    Toast.makeText(AddActivity.this, "당신의 일기를 작성하세요", Toast.LENGTH_SHORT).show();
                } else {
                    //실제 추가부분
                    SQLiteDatabase db = myDBHelper.getWritableDatabase();

                    ContentValues row = new ContentValues();
                    row.put("title", title);
                    row.put("date", date);
                    row.put("content", content);
                    row.put("place", place);
                    row.put("weather", weather);
                    row.put("year", myDate.getYear());
                    row.put("month", myDate.getMonth());
                    row.put("day", myDate.getDay());
                    db.insert(MyDiaryDBHelper.TABLE_NAME, null, row);

                    myDBHelper.close();

                    Toast.makeText(this, "일기를 저장하였습니다", Toast.LENGTH_SHORT).show();

                    MyDiary addData = new MyDiary(content, date, myDate.getDay(), myDate.getMonth(), place, title, weather, myDate.getYear());
                    Intent intent = new Intent();
                    intent.putExtra("added_data", addData);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                break;

            case R.id.buttonAddCancel:
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        if(id == DIALOG_ID) {
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear;
            day_x = dayOfMonth;

            myDate = new MyDiary(year_x, month_x+1, day_x);
            etDate.setText(year_x + "년 " + (month_x + 1) + "월 " + day_x + "일");
        }
    };
}