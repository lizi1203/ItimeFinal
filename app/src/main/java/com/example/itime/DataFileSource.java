package com.example.itime;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataFileSource {
    Context context;

    public DataFileSource(Context context) {
        this.context = context;
    }

    public ArrayList<TimeItem> getItems() {
        return items;
    }

    private ArrayList<TimeItem> items = new ArrayList<>();
    private ArrayList<TimeItem2> items2=new ArrayList<>();

    public byte[] getBytes(Bitmap bitmap){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);

        return baos.toByteArray();

    }



    public  Bitmap getBitmap(byte[] data){

        return BitmapFactory.decodeByteArray(data, 0, data.length);

    }


    public void save() {
        try {
            for(int i=items.size();i>0;i--) {
                TimeItem timeItem=items.get(i-1);
                items2.add(0,new TimeItem2(timeItem.getTitle(),timeItem.getDate(),timeItem.getDescription(),getBytes(timeItem.getBm())));
            }
            ObjectOutputStream outputStream = new ObjectOutputStream
                    (context.openFileOutput("serializable.txt", Context.MODE_PRIVATE));
            outputStream.writeObject(items2);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<TimeItem> load() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream
                    (context.openFileInput("serializable.txt"));
            items2 = (ArrayList<TimeItem2>) inputStream.readObject();
            for(int i=items2.size();i>0;i--) {
                TimeItem2 timeItem=items2.get(i-1);
                items.add(0,new TimeItem(timeItem.getTitle(),timeItem.getDate(),timeItem.getDescription(),getBitmap(timeItem.getBm())));
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
}
