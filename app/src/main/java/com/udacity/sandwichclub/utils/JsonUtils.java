package com.udacity.sandwichclub.utils;

import android.support.annotation.Nullable;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // prevent instantiating util class
    private JsonUtils() {
    }

    @Nullable
    public static Sandwich parseSandwichJson(String json) {
        final Sandwich sandwich = new Sandwich();
        final JSONObject root;

        try {
            root = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JsonUtils", "Invalid Json input for Sandwich", e);
            return null;
        }

        final JSONObject name = root.optJSONObject("name");

        sandwich.setMainName(name.optString("mainName"));

        // parsing "alsoKnownAs"
        final JSONArray alsoKnownAs = name.optJSONArray("alsoKnownAs");
        final List<String> alsoKnownAsList = new ArrayList<>();

        if (alsoKnownAs != null) {
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                alsoKnownAsList.add(alsoKnownAs.optString(i));
            }
        }

        sandwich.setAlsoKnownAs(alsoKnownAsList);

        sandwich.setImage(root.optString("image"));
        sandwich.setPlaceOfOrigin(root.optString("placeOfOrigin"));
        sandwich.setDescription(root.optString("description"));

        // parsing "ingredients"
        final JSONArray ingredients = root.optJSONArray("ingredients");
        final List<String> ingredientsList = new ArrayList<>();

        if (ingredients != null) {
            for (int i = 0; i < ingredients.length(); i++) {
                ingredientsList.add(ingredients.optString(i));
            }
        }

        sandwich.setIngredients(ingredientsList);

        return sandwich;
    }
}
