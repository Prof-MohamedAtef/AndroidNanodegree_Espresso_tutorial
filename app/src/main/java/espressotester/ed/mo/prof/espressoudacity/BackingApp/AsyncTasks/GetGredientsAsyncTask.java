package espressotester.ed.mo.prof.espressoudacity.BackingApp.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.AbstractList;
import java.util.ArrayList;

import espressotester.ed.mo.prof.espressoudacity.BackingApp.Data.OptionsEntity;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Util;


/**
 * Created by Prof-Mohamed Atef on 10/9/2018.
 */

public class GetGredientsAsyncTask extends AsyncTask<String ,Void,ArrayList<OptionsEntity>> {

    private final String LOG_TAG = GetGredientsAsyncTask.class.getSimpleName();
    public OnTaskCompleted onTaskCompleted;
    private String ingredientName;
    private double quantity;
    private String measure;
    private AbstractList<String> ingredientsOnlyList;
    private ArrayList<OptionsEntity> ingredientList;
    private String iD="id";
    private String ValOf_i="value of i is ";
    private String IngredientsStr="ingredients";
    private String IngredientStr="ingredient";
    private String Quantity="quantity";
    private String Measure="measure";
    private String DataRetrieved="data retrieved is ingredientName :";
    private String IngredientsJson="IngredientsList JSON String: " ;
    private String ErrorHereExactly="Error here Exactly ";
    private java.lang.String ErrorClosingStream="Error closing stream";
    private String DidnotgetIngredientsError="didn't got ingredients Data from getJsonData method";

    public GetGredientsAsyncTask(OnTaskCompleted onTaskCompleted) {
        this.onTaskCompleted=onTaskCompleted;
        IngredientsList=new ArrayList<>();
    }

    ArrayList<OptionsEntity> IngredientsList;
    OptionsEntity optionsEntity;
    private ArrayList<OptionsEntity> getIngredientsList(String ingredients_images_jsonSTR)
            throws JSONException {
        JSONArray recipeArray = new JSONArray(ingredients_images_jsonSTR);
        for(int i = 0; i < recipeArray.length() ; i++)
        {
            JSONObject recipeObject = recipeArray.getJSONObject(i);
            String id = recipeObject.getString(iD);
            if(id == Util.RecipeID)
            {
                Log.v(LOG_TAG , ValOf_i+i);

                JSONArray ingredientArray = recipeObject.getJSONArray(IngredientsStr);

                for(int j = 0 ; j < ingredientArray.length() ; j++)
                {
                    JSONObject ingredientObject = ingredientArray.getJSONObject(j);

                    ingredientName = ingredientObject.getString(IngredientStr);
                    quantity = ingredientObject.getDouble(Quantity);
                    measure = ingredientObject.getString(Measure);

//                    ingredientsOnlyList.add(quantity +" "+measure+" of "+ingredientName+"/");

                    Log.v(LOG_TAG, DataRetrieved+ingredientName);
                    OptionsEntity ingredientData =  new OptionsEntity(ingredientName , measure , quantity,"");
                    IngredientsList.add(ingredientData);
                }
            }
        }
        return IngredientsList;
    }

    @Override
    protected ArrayList<OptionsEntity> doInBackground(String... params) {

        String IngredientsList_images_JsonSTR = null;

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        if (params.length == 0) {
            return null;
        }

        try {

            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                IngredientsList_images_JsonSTR = null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }

            IngredientsList_images_JsonSTR  = buffer.toString();

            Log.v(LOG_TAG, IngredientsJson+ IngredientsList_images_JsonSTR );
        } catch (IOException e) {
            Log.e(LOG_TAG, ErrorHereExactly, e);

            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, ErrorClosingStream, e);
                }
            }
        }
        try {
            return getIngredientsList(IngredientsList_images_JsonSTR  );
        }catch (JSONException e) {
            Log.e(LOG_TAG, DidnotgetIngredientsError, e);
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(ArrayList<OptionsEntity> result) {
        super.onPostExecute(result);
        if (result != null) {
            onTaskCompleted.onTaskCompleted(result);
        }
    }


    public interface OnTaskCompleted{
        void onTaskCompleted(ArrayList<OptionsEntity> result);
    }
}