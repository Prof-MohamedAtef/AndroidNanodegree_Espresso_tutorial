package espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import espressotester.ed.mo.prof.espressoudacity.BackingApp.Services.IngredientsWidgetService;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Activities.MainActivity;
import espressotester.ed.mo.prof.espressoudacity.R;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    public static void updateAppWidget(Context context, String widgetText , AppWidgetManager appWidgetManager,
                                       int []appWidgetId) {
        Intent intent=new Intent(context, MainActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(context,0,intent,0);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
//        Intent ingredientsIntent = new Intent(context, IngredientsWidgetService.class);
//        ingredientsIntent.setAction(IngredientsWidgetService.ACTION_Ingredients);
//        PendingIntent ingredientsPendingIntent = PendingIntent.getService(context, 0, ingredientsIntent , PendingIntent.FLAG_UPDATE_CURRENT);
        views.setTextViewText(R.id.appwidget_text, widgetText);
//        views.setImageViewResource(R.id.imageWidget, R.drawable.pizza);
        views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
//        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context,"", appWidgetManager, appWidgetIds);
//        }
        IngredientsWidgetService.startActionFillWidget(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}