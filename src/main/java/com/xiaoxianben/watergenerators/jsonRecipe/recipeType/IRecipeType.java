package com.xiaoxianben.watergenerators.jsonRecipe.recipeType;

import com.google.gson.JsonObject;

@SuppressWarnings("unused")
public interface IRecipeType<T> {

    Class<T> getClassType();

    JsonObject getRecipeJson(T recipe);

    T getRecipe(JsonObject json);

}
