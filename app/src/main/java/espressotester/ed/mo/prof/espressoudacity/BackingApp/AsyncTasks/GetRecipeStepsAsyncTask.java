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
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Util;


/**
 * Created by Prof-Mohamed Atef on 10/9/2018.
 */

public class GetRecipeStepsAsyncTask extends AsyncTask<String ,Void,ArrayList<OptionsEntity>> {

    private final String LOG_TAG = GetRecipeStepsAsyncTask.class.getSimpleName();
    public OnStepsTaskCompleted OnStepsTaskCompleted;
    private String shortDescription;
    private String Description;
    private String VideoUrl;
    private String ThumbUrl;
    private String iD="id";
    private String ValOf_i="value of i is ";
    private String Steps="steps";
    private String shortDesc="shortDescription";
    private String Desc="description";
    private String VidUrl="videoURL";
    private String ThumUrl="thumbnailURL";
    private String RecipesStepsJson="RecipeSteps JSON String: " ;
    private String ErrorHereExactly="Error here Exactly ";
    private String ErrorClosingStream="Error closing stream";
    private String JsonExceptionForRecipeSteps="didn't got RecipesSteps Data from getJsonData method";

    public GetRecipeStepsAsyncTask(OnStepsTaskCompleted OnStepsTaskCompleted) {
        this.OnStepsTaskCompleted=OnStepsTaskCompleted;
        RecipesStepsList=new ArrayList<>();
    }

    ArrayList<OptionsEntity> RecipesStepsList;
    OptionsEntity optionsEntity;
    private ArrayList<OptionsEntity> getRecipesStepsList(String RecipesSteps_images_jsonSTR)
            throws JSONException {
        JSONArray recipeStepsArray = new JSONArray(RecipesSteps_images_jsonSTR);
        for(int i = 0; i < recipeStepsArray.length() ; i++)
        {
            JSONObject recipeStepObject = recipeStepsArray.getJSONObject(i);
            String id = recipeStepObject .getString(iD);
            if(id == Util.RecipeID)
            {
                Log.v(LOG_TAG , ValOf_i+i);
                JSONArray RecipesStepsArray = recipeStepObject.getJSONArray(Steps);
                for(int j = 0 ; j < RecipesStepsArray.length() ; j++)
                {
                    JSONObject RecipeObject = RecipesStepsArray.getJSONObject(j);
                    shortDescription = RecipeObject.getString(shortDesc);
                    Description= RecipeObject.getString(Desc);
                    VideoUrl= RecipeObject.getString(VidUrl);
                    ThumbUrl= RecipeObject.getString(ThumUrl);
                    OptionsEntity ingredientData =  new OptionsEntity(shortDescription,Description, VideoUrl,ThumbUrl,"");
                    RecipesStepsList.add(ingredientData);
                }
            }
        }
        return RecipesStepsList;
    }

    @Override
    protected ArrayList<OptionsEntity> doInBackground(String... params) {

        String RecipesList_DESCRIPTION_JsonSTR = null;

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
                RecipesList_DESCRIPTION_JsonSTR = null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }

            RecipesList_DESCRIPTION_JsonSTR  = buffer.toString();

            Log.v(LOG_TAG, RecipesStepsJson+ RecipesList_DESCRIPTION_JsonSTR );
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
            return getRecipesStepsList(RecipesList_DESCRIPTION_JsonSTR);
        }catch (JSONException e) {
            Log.e(LOG_TAG, JsonExceptionForRecipeSteps, e);
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(ArrayList<OptionsEntity> result) {
        super.onPostExecute(result);
        if (result != null) {
            OnStepsTaskCompleted.OnStepsTaskCompleted(result);
        }
    }


    public interface OnStepsTaskCompleted{
        void OnStepsTaskCompleted(ArrayList<OptionsEntity> result);
    }
}