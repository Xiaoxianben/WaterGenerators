package com.xiaoxianben.watergenerators.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xiaoxianben.watergenerators.event.ConfigLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class recipes<input, output> {

    public List<input> input = new ArrayList<>();
    public List<output> output = new ArrayList<>();

    public recipeType<input> recipeTypeInput;
    public recipeType<output> recipeTypeOutput;

    public recipes(recipeType<input> recipeTypeInput, recipeType<output> recipeTypeOutput, List<input> input, List<output> output) {
        this(recipeTypeInput, recipeTypeOutput);
        this.input.addAll(input);
        this.output.addAll(output);
    }

    public recipes(recipeType<input> recipeTypeInput, recipeType<output> recipeTypeOutput) {
        this.recipeTypeInput = recipeTypeInput;
        this.recipeTypeOutput = recipeTypeOutput;
    }

    public int size() {
        return this.input.size();
    }

    public List<input> getInput() {
        return this.input;
    }

    public input getInput(int index) {
        return this.input.get(index);
    }

    public List<output> getOutput() {
        return this.output;
    }

    public output getOutput(int index) {
        return this.output.get(index);
    }

    public output getOutput(input inputR) {
        return this.output.get(this.input.indexOf(inputR));
    }

    public void JsonToRecipe(JsonArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            JsonObject input = jsonObject.get("input").getAsJsonObject();
            JsonObject output = jsonObject.get("output").getAsJsonObject();
            this.input.add(this.JsonTo(this.recipeTypeInput, input));
            this.output.add(this.JsonTo(this.recipeTypeOutput, output));
        }
    }

    public JsonArray recipeToJson() {
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < this.input.size(); i++) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("input", this.toJson(this.recipeTypeInput, this.input.get(i)));
            jsonObject.add("output", this.toJson(this.recipeTypeOutput, this.output.get(i)));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @SuppressWarnings("unchecked")
    protected <S> S JsonTo(recipeType<S> recipeType, JsonObject jsonObject) {
        // 判断输入类型
        String inputName = jsonObject.get("name").getAsString().substring(recipeType.getType().length() + 1);
        int count = jsonObject.get("count").getAsInt();

        if (Objects.equals(recipeType.getType(), "fluid")) {
            Fluid input = FluidRegistry.getFluid(inputName);
            return (S) new FluidStack(input, count);

        } else if (Objects.equals(recipeType.getType(), "item")) {
            Item input = Item.getByNameOrId(inputName);
            if (input != null) {
                return (S) new ItemStack(input, count);
            }
            ConfigLoader.logger().error("Item:{} not found", inputName);
            throw new RuntimeException(this + "\n the recipe input item not found, this is a bug. You should check the recipe that you modified.");

        } else if (Objects.equals(recipeType.getType(), "int")) {
            return (S) (Integer) count;

        } else if (Objects.equals(recipeType.getType(), "float")) {
            return (S) (Float) jsonObject.get("count").getAsFloat();
        }
        return null;
    }

    protected <S> JsonObject toJson(recipeType<S> recipeType, S input) {
        JsonObject json = new JsonObject();
        String inputName = "";
        int count = 1;
        float floatCount = 0.0f;

        // 判断输入类型
        if (Objects.equals(recipeType.getType(), "fluid")) {
            inputName = inputName + "fluid:" + ((FluidStack) input).getFluid().getName();
            count = ((FluidStack) input).amount;

        } else if (Objects.equals(recipeType.getType(), "item")) {
            inputName = inputName + "item:" + Objects.requireNonNull(((ItemStack) input).getItem().getRegistryName());
            count = ((ItemStack) input).getCount();

        } else if (Objects.equals(recipeType.getType(), "int")) {
            inputName = inputName + "int:" + input.toString();
            count = (Integer) input;

        } else if (Objects.equals(recipeType.getType(), "float")) {
            inputName = inputName + "float:" + input.toString();
            count = -1;
            floatCount = (Float) input;
        }

        json.addProperty("name", inputName);
        if (count != -1) {
            json.addProperty("count", count);
        } else {
            json.addProperty("count", floatCount);
        }

        return json;
    }
}
