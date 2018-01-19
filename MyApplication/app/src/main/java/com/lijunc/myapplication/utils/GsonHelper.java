package com.lijunc.myapplication.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijunc on 2017/12/25.
 */

public final class GsonHelper {
    private static Gson mGson=new Gson();
    private static JsonParser mJsonParser=new JsonParser();

    private GsonHelper() {
    }

    public static <T> List<T> convertEntity(String jsonData, Class<T> clazz){
        List<T> list = new ArrayList<>();

        try{
            JsonArray jsonArray = mJsonParser.parse(jsonData).getAsJsonArray();
            for(JsonElement element:jsonArray){
                list.add(mGson.fromJson(element,clazz));
            }
        }catch (JsonSyntaxException e){
            e.printStackTrace();
        }
        return list;
    }
}
