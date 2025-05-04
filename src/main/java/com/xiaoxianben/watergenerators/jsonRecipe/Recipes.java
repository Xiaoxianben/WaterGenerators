package com.xiaoxianben.watergenerators.jsonRecipe;

import com.google.gson.JsonObject;
import com.xiaoxianben.watergenerators.jsonRecipe.recipeType.IRecipeType;

import java.util.ArrayList;

//@SuppressWarnings("unused")
public class Recipes<input, output> {

    protected final IRecipeType<input> inputRecipeType;
    protected final IRecipeType<output> outputRecipeType;

    protected ArrayList<input> inputs = new ArrayList<>();
    protected ArrayList<output> outputs = new ArrayList<>();
    protected ArrayList<String> recipeNames = new ArrayList<>();


    public Recipes(IRecipeType<input> inputRecipeType, IRecipeType<output> outputRecipeType) {
        this.inputRecipeType = inputRecipeType;
        this.outputRecipeType = outputRecipeType;
    }


    public void addRecipe(input input, output output) {
//        recipeMap.put(input, output);
        this.inputs.add(input);
        this.outputs.add(output);
    }


    public int size() {
        return inputs.size();
    }

    public int indexOfKey(input in) {
        return this.inputs.indexOf(in);
    }

    public boolean containsKay(input in) {
        return this.indexOfKey(in) >= 0;
    }

    public ArrayList<input> getInputs() {
        return this.inputs;
    }

    public input getInput(int i) {
        return this.inputs.get(i);
    }

    public output getOutput(input input) {
        return this.outputs.get(this.inputs.indexOf(input));
    }

    public output getOutput(int i) {
        return this.outputs.get(i);
    }


    public JsonObject getRecipeJson() {

        JsonObject json = new JsonObject();

        for (int i = 0; i < size(); i++) {

            JsonObject recipe = new JsonObject();

            recipe.add("input", this.inputRecipeType.getRecipeJson(this.getInput(i)));
            recipe.add("output", this.outputRecipeType.getRecipeJson(this.getOutput(i)));
//            recipe.addProperty("energyDeplete", this.energyDepleteList.get(i));

            json.add(i >= recipeNames.size() ? String.valueOf(i) : recipeNames.get(i), recipe);
        }

        return json;
    }

    /**
     * 通过JsonObject读取配方
     */
    public void readRecipeJson(JsonObject json) {

        json.entrySet().forEach(entry -> {
            this.recipeNames.add(entry.getKey());
            JsonObject recipe = entry.getValue().getAsJsonObject();

            try {
                this.addRecipe(this.inputRecipeType.getRecipe(recipe.get("input").getAsJsonObject()),
                        this.outputRecipeType.getRecipe(recipe.get("output").getAsJsonObject()));
            } catch (Exception e) {
                throw new RuntimeException("读取配方时发生错误, 配方id：" + entry.getKey(), e);
            }
        });

    }
}
