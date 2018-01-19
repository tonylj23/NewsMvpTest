package com.lijunc.myapplication.utils;

import android.content.Context;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lijunc on 2017/12/25.
 */

public class AssetsHelper {
    private AssetsHelper() {
        throw new AssertionError();
    }

    public static String readData(Context context,String fileName){
        InputStream inputStream=null;
        String data=null;
        try {
            inputStream=context.getAssets().open(fileName);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();
            data=new String(bytes,"utf-8");
        } catch (IOException e) {
            Logger.e(e.toString());
            e.printStackTrace();
        }
        return data;

    }
}
