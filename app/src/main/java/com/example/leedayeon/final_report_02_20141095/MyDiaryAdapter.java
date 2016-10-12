package com.example.leedayeon.final_report_02_20141095;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by LeeDaYeon on 2016-06-24.
 */
public class MyDiaryAdapter extends BaseAdapter{
    private Context context;
    private int layout;
    private ArrayList<MyDiary> myData;
    private LayoutInflater inflater;

    public MyDiaryAdapter(Context context, int layout, ArrayList<MyDiary> myData) {
        this.context = context;
        this.layout = layout;
        this.myData = myData;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myData.size();
    }
    @Override
    public Object getItem(int i) {
        return myData.get(i);
    }
    @Override
    public long getItemId(int i) {
        return (long)myData.get(i).get_id();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;

        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        TextView textDate = (TextView)convertView.findViewById(R.id.textViewDate);
        TextView textTitle = (TextView)convertView.findViewById(R.id.textViewTitle);
        //TextView textContent = (TextView)convertView.findViewById(R.id.textViewContent);
        TextView textPlace = (TextView)convertView.findViewById(R.id.textViewPlace);


        textDate.setText(myData.get(pos).getDate());
        textTitle.setText(myData.get(pos).getTitle());
        //textContent.setText(myData.get(pos).getContent());
        textPlace.setText(myData.get(pos).getPlace());

        return convertView;
    }

}
