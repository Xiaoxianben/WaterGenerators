package com.xiaoxianben.watergenerators.recipe;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.Objects;

public class recipe<T, G> {
    public T input;
    public recipeType<T> inputType;
    public G output;
    public recipeType<G> outputType;

    public recipe(recipeType<T> inputType, recipeType<G> outputType, T input, G output) {
        this.input = input;
        this.output = output;
        this.inputType = inputType;
        this.outputType = outputType;
    }

    public T getInput() {
        return this.input;
    }

    public void setInput(T input) {
        this.input = input;
    }

    public JsonObject inputToJson() {
        return this.toJson(this.inputType, this.input);
    }

    public JsonObject outputToJson() {
        return this.toJson(this.outputType, this.output);
    }

    protected <S> JsonObject toJson(recipeType<S> recipeType, S input) {
        JsonObject json = new JsonObject();
        String inputName = "";
        int count = 1;
        if (recipeType == recipeTypes.FLUID) {
            inputName = inputName + "fluid:" + ((FluidStack) input).getFluid().getName();
            count = ((FluidStack) input).amount;
        } else if (recipeType == recipeTypes.ITEM) {
            inputName = inputName + "item:" + Objects.requireNonNull(((ItemStack) input).getItem().getRegistryName()).toString();
            count = ((ItemStack) input).getCount();
        }
        json.addProperty("name", inputName);
        json.addProperty("count", count);

        return json;
    }

    public G getOutput() {
        return this.output;
    }

    public void setOutput(G output) {
        this.output = output;
    }

    @Nullable
    public Class<? extends T> getInputClass() {
        if (this.input == null)
            return null;
        // 强制类型转换
        // 因为泛型擦除，所以这里需要强制类型转换
        // 获取输入的Class类型
        // 这里使用反射来获取Class类型
        return this.inputType.getIngredientClass();
    }

    @Nullable
    public Class<? extends G> getOutputClass() {
        if (this.output == null)
            return null;
        // 强制类型转换
        // 因为泛型擦除，所以这里需要强制类型转换
        // 获取输出的Class类型
        // 这里使用反射来获取Class类型
        return this.outputType.getIngredientClass();
    }

}
