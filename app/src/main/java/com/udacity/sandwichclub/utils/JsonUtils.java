package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public JsonUtils() throws JSONException {


    }

    public static Sandwich parseSandwichJson(String json) {

        try {

            //Create JSONObject of whole json string
            JSONObject rootObject = new JSONObject(json);

            //Get name JsonObject
            JSONObject subObj = rootObject.getJSONObject("name");

            //Get mainName and alsoKnownAs values
            String mainName = subObj.getString("mainName");
            JSONArray aknownas = subObj.getJSONArray("alsoKnownAs");

            //Get PlaceOfOrigin
            String placeOfOrigin = rootObject.getString("placeOfOrigin");

            //Get Description
            String description = rootObject.getString("description");

            //GET image path
            String imagePath = rootObject.getString("image");


            Log.v("my_tag", "mainName is: " + mainName);
            Log.v("my_tag", "placeOfOrigin is: " + placeOfOrigin);
            Log.v("my_tag", "image is: " + imagePath);
            Log.v("my_tag", "description is: " + description);

            //Get ingredient array
            JSONArray ingredientArray = rootObject.getJSONArray("ingredients");
            List<String> alsoKnownAsList = new ArrayList<>();

            //Iterate through the array of aka and add it to list
            for (int i = 0; i < aknownas.length(); i++) {
                String alsoKnownAs = aknownas.getString(i);
                alsoKnownAsList.add(alsoKnownAs);
            }

            //Iterate through the array of ingredients and add it to list

            List<String> ingredientList = new ArrayList<>();
            for (int i = 0; i < ingredientArray.length(); i++) {
                ingredientList.add(ingredientArray.getString(i));
            }

            Sandwich sandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, imagePath, ingredientList);
            return sandwich;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}