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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by LeeDaYeon on 2016-06-25.
 */
public class UpdateActivity extends AppCompatActivity {

    EditText etTitle;
    TextView etDate;
    EditText etPlace;
    EditText etWeather;
    EditText etContent;

    MyDiary receivedData;

    MyDiaryDBHelper myDBHelper;

    int year_x, month_x, day_x;
    static final int DIALOG_ID = 1;
    MyDiary myDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        myDBHelper = new MyDiaryDBHelper(this);

        etTitle = (EditText)findViewById(R.id.editTextAddTitle);
        etDate = (TextView) findViewById(R.id.editTextAddDate);
        etPlace = (EditText)findViewById(R.id.editTextAddPlace);
        etWeather = (EditText)findViewById(R.id.editTextAddWeather);
        etContent = (EditText)findViewById(R.id.editTextAddContent);

        Intent intent =  getIntent();
        receivedData = (MyDiary)intent.getSerializableExtra("update_data");

        etTitle.setText(receivedData.getTitle());
        etDate.setText(receivedData.getDate());
        etPlace.setText(receivedData.getPlace());
        etWeather.setText(receivedData.getWeather());
        etContent.setText(receivedData.getContent());

        year_x = receivedData.getYear();
        month_x = receivedData.getMonth();
        day_x = receivedData.getDay();

        myDate = new MyDiary(year_x, month_x, day_x);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == DIALOG_ID) {
            return new DatePickerDialog(this, dpickerListener, year_x, (month_x-1), day_x);

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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton:
            case R.id.editTextAddDate:
                showDialog(DIALOG_ID);
                break;

            case R.id.buttonAddOK:
                String Title = etTitle.getText().toString();
                String Date = etDate.getText().toString();
                String Place = etPlace.getText().toString();
                String Weather = etWeather.getText().toString();
                String Content = etContent.getText().toString();

                if (Title.equals(receivedData.getTitle()) && Date.equals(receivedData.getDate()) && Place.equals(receivedData.getPlace())
                        && Weather.equals(receivedData.getWeather()) && Content.equals(receivedData.getContent())) {
                    Toast.makeText(this, "수정할 내용이 없습니다. \n수정하려면 수정할 내용을 클릭하십시오", Toast.LENGTH_SHORT).show();
                } else if (Title.equals("") || Date.equals("") || Place.equals("") || Weather.equals("") || Content.equals("")) {
                    Toast.makeText(UpdateActivity.this, "입력되지 않은 항목이 있습니다!", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase db = myDBHelper.getWritableDatabase();

                    ContentValues row = new ContentValues();

                    row.put("title", Title);
                    row.put("date", Date);
                    row.put("content", Content);
                    row.put("place", Place);
                    row.put("weather", Weather);
                    row.put("year", myDate.getYear());
                    row.put("month", myDate.getMonth());
                    row.put("day", myDate.getDay());

                    String whereClause = "_id=?";
                    String[] whereArgs = new String[]{
                            Integer.valueOf(receivedData.get_id()).toString()
                    };

                    db.update(MyDiaryDBHelper.TABLE_NAME, row, whereClause, whereArgs);
                    db.close();

                    Toast.makeText(this, "수정하였습니다", Toast.LENGTH_SHORT).show();

                    MyDiary resultData = new MyDiary(Content, Date, myDate.getDay(), myDate.getMonth(), Place, Title, Weather, myDate.getYear());

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("returned_data", resultData);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
                break;

            case R.id.buttonUpdateCancel:
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
        }
    }
}
