package com.example.itime.ui.color;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.itime.ColorPickerView;
import com.example.itime.R;


public class SlideshowFragment extends Fragment {

    private ColorPickerView colorPickerView;
    private ColorDrawable colorDrawable;
    private static final int INIT_COLOR = 0xFF0000FF;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_slideshow,container,false);
        View dialogView = inflater.inflate(R.layout.dialog_layout,container,false);

        colorPickerView = dialogView.findViewById(R.id.color_picker);
        colorPickerView.setColorPickerListener(new ColorPickerView.ColorPickerListener() {
            @Override
            public void onColorChanging(int color) {
                if (colorDrawable == null) {
                    colorDrawable = new ColorDrawable(color);
                } else {
                    colorDrawable.setColor(color);
                }
            }

            @Override
            public void onColorPicked(int color) {

            }
        });

        colorPickerView.setCurrentColor(INIT_COLOR);
        colorDrawable = new ColorDrawable(INIT_COLOR);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("颜色选择");     //设置对话框标题
        builder.setIcon(android.R.drawable.btn_star);      //设置对话框标题前的图标
        builder.setView(dialogView);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(DialogInterface dialog, int which) {

                getActivity().findViewById(R.id.toolbar).setBackgroundColor(colorDrawable.getColor());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getActivity().getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(colorDrawable.getColor());
                }

                Toast.makeText(getActivity(), "OK" , Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(true);   //设置按钮是否可以按返回键取消,false则不可以取消
        AlertDialog dialog = builder.create();  //创建对话框
        dialog.setCanceledOnTouchOutside(true);      //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width =1000;
        params.height = 700 ;
        dialog.getWindow().setAttributes(params);

        return view;
    }



}