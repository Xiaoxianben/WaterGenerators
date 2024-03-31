package com.xiaoxianben.watergenerators.recipe;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class recipeIngredient {
    private final Map<recipeType, List> recipeInput = new HashMap<>();
    private final Map<recipeType, List> recipeOutput = new HashMap<>();

    public <T> void setInput(recipeType<T> type, T input) {
        setInputs(type, Collections.singletonList(input));
    }
    public <T> void setInputs(recipeType<T> type, List<T> input) {
        recipeInput.put(type, input);
    }
    public <T> void setOutput(recipeType<T> type, T input) {
        setInputs(type, Collections.singletonList(input));
    }
    public <T> void setOutputs(recipeType<T> type, List<T> input) {
        recipeOutput.put(type, input);
    }
    public <T> List<T> getInputs(recipeType<T> type) {
        return recipeInput.get(type);
    }
    public <T> List<T> getOutputs(recipeType<T> type) {
        return recipeOutput.get(type);
    }
}
