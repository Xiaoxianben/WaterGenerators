package com.xiaoxianben.watergenerators.jsonRecipe;

import com.google.gson.JsonObject;
import com.xiaoxianben.watergenerators.jsonRecipe.recipeType.IRecipeType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

@SuppressWarnings("unused")
public class Recipes<input, output> {

    protected final IRecipeType<input> inputRecipeType;
    protected final IRecipeType<output> outputRecipeType;

    protected LinkedHashMap<input, output> recipeMap = new LinkedHashMap<>();
    protected ArrayList<Integer> energyDepleteList = new ArrayList<>();
    protected ArrayList<String> recipeNames = new ArrayList<>();


    public Recipes(IRecipeType<input> inputRecipeType, IRecipeType<output> outputRecipeType) {
        this.inputRecipeType = inputRecipeType;
        this.outputRecipeType = outputRecipeType;
    }


    public void addRecipe(input input, output output, int energyDeplete) {
        recipeMap.put(input, output);
        this.energyDepleteList.add(energyDeplete);
    }


    public int size() {
        return Math.min(recipeMap.size(), energyDepleteList.size());
    }

    public boolean containsKay(input in) {
        return this.recipeMap.containsKey(in);
    }

    public Set<input> getInputs() {
        return this.recipeMap.keySet();
    }

    public input getInput(int i) {
        return (input) getInputs().toArray()[i];
    }

    public output getOutput(input input) {
        return this.recipeMap.get(input);
    }

    public output getOutput(int i) {
        return (output) this.recipeMap.values().toArray()[i];
    }

    public int getEnergyDeplete(input input) {
        return this.getEnergyDeplete(new ArrayList<>(this.recipeMap.keySet()).indexOf(input));
    }

    public int getEnergyDeplete(int i) {
        return this.energyDepleteList.get(i);
    }

    public ArrayList<Integer> getEnergyDepletes() {
        return this.energyDepleteList;
    }


    public JsonObject getRecipeJson() {

        JsonObject json = new JsonObject();

        for (int i = 0; i < this.recipeMap.size(); i++) {

            JsonObject recipe = new JsonObject();

            recipe.add("input", this.inputRecipeType.getRecipeJson(this.getInput(i)));
            recipe.add("output", this.outputRecipeType.getRecipeJson(this.getOutput(i)));
            recipe.addProperty("energyDeplete", this.energyDepleteList.get(i));

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
                        this.outputRecipeType.getRecipe(recipe.get("output").getAsJsonObject()),
                        recipe.get("energyDeplete").getAsInt());
            } catch (Exception e) {
                throw new RuntimeException("读取配方时发生错误, 配方id：" + entry.getKey(), e);
            }
        });

    }
}
