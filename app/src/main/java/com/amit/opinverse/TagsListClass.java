package com.amit.opinverse;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class TagsListClass {
    static Context context;
    static SharedPreferences sharedPreferences;
    static List<String> tagList;
    static void setContext(Context context){
        TagsListClass.context = context;
        sharedPreferences = context.getSharedPreferences("Tags", Context.MODE_PRIVATE);
        tagList = new ArrayList<>();
    }
    static void save(){
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String tags = gson.toJson(tagList);
        editor.putString("tags", tags);
        editor.commit();
    }

    static void load(){
        tagList.clear();
        Gson gson = new Gson();
        List<String> loadedList = gson.fromJson(sharedPreferences.getString("tags", ""), new TypeToken<List<String>>(){}.getType());
        if(loadedList != null){
            tagList.addAll(loadedList);
        }
    }

    static void add(String tag){
        load();
        tagList.add(tag);
        save();
    }

    static void delete(int i){
        load();
        tagList.remove(i);
        save();
    }

    static void appendList(List<String> tags){
        load();
        tagList.addAll(tags);
        save();
    }

    public static List<String> getTagList() {
        load();
        return tagList;
    }
}
