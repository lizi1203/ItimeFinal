package com.example.itime;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class CreatNewActivity extends AppCompatActivity {
    ListView listView;
    private ArrayList<ChooseItem> chooseItems;
    Button buttonReturn;
    Button buttonOk;
    EditText editTitle;
    EditText editDescription;
    ChooseAdapter chooseAdapter;
    TextView textView;
    ImageView imageView;
    ImageView img;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
               if (requestCode == 4 && resultCode == Activity.RESULT_OK
                       && data != null){
                   Toast.makeText(this, "图片已选择", Toast.LENGTH_SHORT).show();
                   requestWritePermission();
                   Uri selectedImage = data.getData();

                   String[] filePathColumns = {MediaStore.Images.Media.DATA};

                   Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);

                   c.moveToFirst();

                   int columnIndex = c.getColumnIndex(filePathColumns[0]);

                   String imagePath = c.getString(columnIndex);

                   showImage(imagePath);

                   c.close();

               }
    }

    private void requestWritePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

    }

    private void showImage(String imagePath) {
        Bitmap bm = BitmapFactory.decodeFile(imagePath);

        ((ImageView)findViewById(R.id.choose_img)).setImageBitmap(bm);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_new);
        buttonReturn=findViewById(R.id.button_return);
        buttonOk=findViewById(R.id.button_ok);
        editTitle=findViewById(R.id.edit_text_title);
        editDescription=findViewById(R.id.edit_text_description);
        listView=findViewById(R.id.list_view_choose);
        editTitle=findViewById(R.id.edit_text_title);
        editDescription=findViewById(R.id.edit_text_description);
        imageView=findViewById(R.id.image2);
        textView=findViewById(R.id.description2);


        Init();
        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        String description=intent.getStringExtra("description");
        String date=intent.getStringExtra("date");
        final int position=intent.getIntExtra("position",0);
        if(title!=null) {
            editTitle.setText(title);
        }
        if(description!=null) {
            editDescription.setText(description);
        }
        if(date!=null) {
            chooseItems.get(0).setDescription(date);
        }

        chooseAdapter = new ChooseAdapter(CreatNewActivity.this, R.layout.choose_item, chooseItems);
        listView.setAdapter(chooseAdapter);

        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("title", editTitle.getText().toString().trim());
                intent.putExtra("description", editDescription.getText().toString().trim());
                intent.putExtra("position", position);
                intent.putExtra("date",
                        chooseAdapter.year+"-"+chooseAdapter.month+"-"+chooseAdapter.day+" "
                                +chooseAdapter.hour+":"+chooseAdapter.minute+":"+chooseAdapter.second);
                //intent.putExtra("imagePath", imagePath);
                setResult(RESULT_OK, intent);
                Log.d("bookTitle", editTitle.getText().toString());
                CreatNewActivity.this.finish();
            }
        });
    }


    private void Init()
    {
        chooseItems=new ArrayList<>();
        chooseItems.add(new ChooseItem("Date  ", "Long press to use calculator",
                R.drawable.calendar_icon));
        chooseItems.add(new ChooseItem("Period","None                      ",R.drawable.period_icon));
        chooseItems.add(new ChooseItem("Image ","                          ",R.drawable.image_icon));
        chooseItems.add(new ChooseItem("Stick ","                          ",R.drawable.stick_icon));
    }
}