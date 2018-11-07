package espressotester.ed.mo.prof.espressoudacity.BackingApp.Data;

import java.io.Serializable;

/**
 * Created by Prof-Mohamed Atef on 10/9/2018.
 */

public class OptionsEntity implements Serializable {

    String recipeName, recipeImage,recipeID, ingredientsName, ingredientsMeasure; Double ingredientsQuantity;

    String shortDescription, Description, VideoUrl, ThumbUrl;

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public String getThumbUrl() {
        return ThumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        ThumbUrl = thumbUrl;
    }

    public OptionsEntity(String shortDescription, String description, String videoUrl, String thumbnailUrl, String s) {
        this.shortDescription=shortDescription;
        this.Description=description;
        this.VideoUrl=videoUrl;
        this.ThumbUrl=thumbnailUrl;
    }

    public String getIngredientsName() {
        return ingredientsName;
    }

    public void setIngredientsName(String ingredientsName) {
        this.ingredientsName = ingredientsName;
    }

    public String getIngredientsMeasure() {
        return ingredientsMeasure;
    }

    public void setIngredientsMeasure(String ingredientsMeasure) {
        this.ingredientsMeasure = ingredientsMeasure;
    }

    public Double getIngredientsQuantity() {
        return ingredientsQuantity;
    }

    public void setIngredientsQuantity(Double ingredientsQuantity) {
        this.ingredientsQuantity = ingredientsQuantity;
    }

    public OptionsEntity() {
    }

    public OptionsEntity(String recipeName, String recipeImage, String recipeID) {
        this.recipeName = recipeName;
        this.recipeImage = recipeImage;
        this.recipeID = recipeID;
    }

    public OptionsEntity(String ingredientName, String measure, double quantity, String s) {
        this.ingredientsName=ingredientName;
        this.ingredientsMeasure=measure;
        this.ingredientsQuantity=quantity;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }
}