package com.example.leedayeon.final_report_02_20141095;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LeeDaYeon on 2016-06-24.
 */
public class MyDiaryDBHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "my_db";
    public static final String TABLE_NAME = "my_table";

    public MyDiaryDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, title TEXT, content TEXT, place TEXT, weather TEXT, year INTEGER, month INTEGER, day INTEGER)";
        sqLiteDatabase.execSQL(createTable);

        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values (null, '2016년 6월 26일', '여름방학이다!', '드디어 방학을 했다. 이번 여름방학은 알차게 보내야 할텐데...', '동덕여대', '소나기', '2016', '6' , '26');");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values (null, '2016년 5월 26일', '친구들을 만났다', '고등학교때 친구들을 만났다. 다들 시간내기가 힘들어 굉장히 오랜만에 만났다. 반가웠다!', '신촌', '맑음', '2016', '5' , '26');");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values (null, '2016년 5월 1일', '감기에 걸렸다', '목도 붓고 기침도 나고 힘들다. 내일은 병원을 가야지', '집', '구름많음', '2016', '5' , '1');");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values (null, '2014년 3월 2일', '개강ㅠㅠ', '개강이라니 말도안돼 으어어', '한강', '눈', '2014', '3' , '2');");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values (null, '2014년 12월 31일', '올해 연기대상은', '올해 연기대상은 배우 000이 받았다. 우리 엄마가 굉장히 좋아했다', '집', '맑음', '2014', '12' , '31');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
