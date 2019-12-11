package com.example.itime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class TimeAdapter extends ArrayAdapter<TimeItem> {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM  dd,yyyy", Locale.ENGLISH);

    SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("MMM  dd,yyyy HH:mm:ss EEE", Locale.ENGLISH);
    // HH:mm:ss//获取当前时间

    int count;


    public TimeAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TimeItem> objects) {
        super(context, resource, objects);
    }
    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength)
    {
        if(null == bitmap || edgeLength <= 0)
        {
            return  null;
        }

        Bitmap result = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();

        if(widthOrg > edgeLength && heightOrg > edgeLength)
        {
            //压缩到一个最小长度是edgeLength的bitmap
            int longerEdge = (int)(edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
            int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
            int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
            Bitmap scaledBitmap;

            try{
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            }
            catch(Exception e){
                return null;
            }

            //从图中截取正中间的正方形部分。
            int xTopLeft = (scaledWidth - edgeLength) / 2;
            int yTopLeft = (scaledHeight - edgeLength) / 2;

            try{
                result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
                scaledBitmap.recycle();
            }
            catch(Exception e){
                return null;
            }
        }

        return result;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        Date date = new Date(System.currentTimeMillis());
        final TimeItem timeItem = getItem(position);//获取当前项的实例
        View oneTimeAllView = LayoutInflater.from(getContext()).inflate(R.layout.time_item, parent, false);
        ((ImageView) oneTimeAllView.findViewById(R.id.image)).setImageBitmap(centerSquareScaleBitmap(timeItem.getBm(),90));
        ((TextView) oneTimeAllView.findViewById(R.id.title)).setText(timeItem.getTitle());
        ((TextView) oneTimeAllView.findViewById(R.id.date)).setText((CharSequence) simpleDateFormat.format(timeItem.getDate()));
        ((TextView) oneTimeAllView.findViewById(R.id.description)).setText(timeItem.getDescription());
        count = timeItem.getGapCount(timeItem.getDate(), date);

        if ((count) >= 0) {
            ((TextView) oneTimeAllView.findViewById(R.id.count_text_view)).setText(count/(60*60*24) + " DAYS");
        } else {
            ((TextView) oneTimeAllView.findViewById(R.id.count_text_view)).setText(count/(60*60*24)+ " DAYS AGO");
        }

        oneTimeAllView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), TimeAllActivity.class);
                intent.putExtra("Title", timeItem.getTitle());
                intent.putExtra("Date", simpleDateFormat3.format(timeItem.getDate()));
                intent.putExtra("Description", timeItem.getDescription());
                intent.putExtra("position", position);
                Bundle bundle = new Bundle();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    bundle.putBinder("bitmap", new ImageBinder(timeItem.getBm()));
                }
                intent.putExtras(bundle);
                ((Activity) getContext()).startActivityForResult(intent, 2);
            }
        });

        return oneTimeAllView;
    }
}