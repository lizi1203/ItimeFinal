package com.example.itime;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;

class ImageBinder extends Binder {
    private Bitmap bitmap;

    public ImageBinder(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    Bitmap getBitmap() {
        return bitmap;
    }
}

