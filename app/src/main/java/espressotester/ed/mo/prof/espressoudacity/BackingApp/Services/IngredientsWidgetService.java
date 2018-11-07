package espressotester.ed.mo.prof.espressoudacity.BackingApp.Services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.HashMap;

import espressotester.ed.mo.prof.espressoudacity.BackingApp.Data.SharedPref;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Widget.IngredientsWidgetProvider;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Util;


/**
 * Created by Prof-Mohamed Atef on 11/2/2018.
 */

public class IngredientsWidgetService extends IntentService {

    private static final String DisplayIngredientService="DisplayIngredientsService";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    SharedPref sharedPref;
    HashMap<String, String> Ingredients;


    public IngredientsWidgetService() {
        super(DisplayIngredientService);
    }

    public static final String ACTION_Ingredients =
            "backingapp.ed.mo.prof.backingapp.action.ShowIngredients";

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent!=null){
            final String action=intent.getAction();
            if (ACTION_Ingredients.equals(action)){
                handleActionIngredient();
            }
        }
    }

    private void handleActionIngredient() {
        sharedPref=new SharedPref(Util.mContext);
        Ingredients=sharedPref.getIngredientsFromPrefs();
        if (Ingredients!=null){
            String IngredientsStr=  Ingredients.get(SharedPref.Key_IngredientNames);
            AppWidgetManager appWidgetManager= AppWidgetManager.getInstance(this);
            int[] appWidgetIds=appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));
            IngredientsWidgetProvider.updateAppWidget(this,IngredientsStr,appWidgetManager,appWidgetIds);
//                    for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
//        }
        }
    }

    public static void startActionFillWidget(Context context){
        Intent intent = new Intent(context, IngredientsWidgetService.class);
        Util.mContext=context;
        intent.setAction(ACTION_Ingredients);
        context.startService(intent);
    }
}