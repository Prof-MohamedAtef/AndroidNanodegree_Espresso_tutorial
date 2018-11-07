package espressotester.ed.mo.prof.espressoudacity.BackingApp.Data;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Prof-Mohamed Atef on 11/2/2018.
 */

public class SharedPref {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    public static final String PREFS_Options = "OptionsFile";
    public static final String Key_IngredientNames="IngredientNames";

    public SharedPref(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFS_Options, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createIngredientsIntoPrefs(String ingredients){
        editor.putString(Key_IngredientNames, ingredients);
        editor.apply();
    }

    public HashMap<String, String> getIngredientsFromPrefs(){
        HashMap<String, String> Ingredients = new HashMap<>();
        Ingredients.put(Key_IngredientNames, pref.getString(Key_IngredientNames, null));
        return Ingredients;
    }
}