package com.xiaoxianben.watergenerators.recipe.recipeType;

import com.google.gson.JsonObject;

public class RecipeInt implements IRecipeType<Integer> {

    public String name;

    public RecipeInt(String name) {
        this.name = name;
    }

    @Override
    public Class<Integer> getClassType() {
        return Integer.class;
    }

    @Override
    public JsonObject getRecipeJson(Integer integer) {
        JsonObject json = new JsonObject();

        json.addProperty("name", this.name);
        json.addProperty("count", integer);

        return json;
    }

    @Override
    public Integer getRecipe(JsonObject json) {
        return json.get("count").getAsInt();
    }
}
