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
import java.util.ArrayList;

import espressotester.ed.mo.prof.espressoudacity.BackingApp.Data.OptionsEntity;
import espressotester.ed.mo.prof.espressoudacity.R;


/**
 * Created by Prof-Mohamed Atef on 10/9/2018.
 */

public class GetRecipesAsyncTask extends AsyncTask<String ,Void,ArrayList<OptionsEntity>> {

    private final String LOG_TAG = GetRecipesAsyncTask.class.getSimpleName();
    public OnTaskCompleted onTaskCompleted;
    private String Name="name";
    private String iD="id";
    private String Image="image";
    private String RecipesJsonString="Recipes JSON String: ";
    private String ErrorHereExactly="Error here Exactly ";
    private String ErrorClosingStream="Error closing stream";
    private String DidnotGetRecipes="didn't got Recipes Data from getJsonData method";

    public GetRecipesAsyncTask(OnTaskCompleted onTaskCompleted) {
        this.onTaskCompleted=onTaskCompleted;
        recipesList=new ArrayList<>();
    }

    ArrayList<OptionsEntity> recipesList;
    OptionsEntity optionsEntity;
    private ArrayList<OptionsEntity> getRecipes(String recipes_images_jsonSTR)
            throws JSONException {
        String recipeName, recipeID, recipeImage;
        JSONArray recipeArrays=new JSONArray(recipes_images_jsonSTR);
        for (int i=0; i < recipeArrays.length(); i++){
            JSONObject recipeObject=recipeArrays.getJSONObject(i);
            recipeName=recipeObject.getString(Name);
            recipeID=recipeObject.getString(iD);
            recipeImage=recipeObject.getString(Image);
            if(recipeImage.equals(""))
            {
                recipeImage= String.valueOf(R.string.empty);
            }
            optionsEntity=new OptionsEntity(recipeName,recipeImage,recipeID);
            recipesList.add(optionsEntity);
        }
        return recipesList;
    }

    @Override
    protected ArrayList<OptionsEntity> doInBackground(String... params) {

        String Recipes_images_JsonSTR = null;

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
                Recipes_images_JsonSTR = null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }

            Recipes_images_JsonSTR  = buffer.toString();

            Log.v(LOG_TAG, RecipesJsonString + Recipes_images_JsonSTR );
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
            return getRecipes(Recipes_images_JsonSTR  );
        }catch (JSONException e) {
            Log.e(LOG_TAG, DidnotGetRecipes, e);
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