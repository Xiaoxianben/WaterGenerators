package com.xiaoxianben.watergenerators.recipe;

public interface recipeType<T> {
    Class<? extends T> getIngredientClass();

}
