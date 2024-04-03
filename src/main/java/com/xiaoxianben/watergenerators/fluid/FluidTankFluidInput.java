package com.xiaoxianben.watergenerators.fluid;

import com.xiaoxianben.watergenerators.recipe.recipe;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class FluidTankFluidInput<Input extends FluidStack, Output> extends FluidTankBase {
    public List<recipe<Input, Output>> recipeList;
    public List<Fluid> canInputFluids;
    public List<Output> canOutputFluids;

    public FluidTankFluidInput(int capacity, List<recipe<Input, Output>> recipeList) {
        super(capacity);
        this.recipeList = recipeList;
        this.canInputFluids = this.getCanInputFluids(recipeList);
        this.canOutputFluids = this.getCanOutputFluids(recipeList);
    }

    public boolean canFillFluidType(FluidStack fluid) {
        return this.canInputFluids.contains(fluid.getFluid());
    }

    private List<Fluid> getCanInputFluids(List<recipe<Input, Output>> recipeList) {
        List<Fluid> fluids = new ArrayList<>();

        for (recipe<Input, Output> recipe : recipeList) {
            Fluid fluidss = recipe.getInput().getFluid();
            fluids.add(fluidss);
        }

        return fluids;
    }

    private List<Output> getCanOutputFluids(List<recipe<Input, Output>> recipeList) {
        List<Output> output = new ArrayList<>();

        for (recipe<Input, Output> recipe : recipeList) {
            Output fluidss = recipe.getOutput();
            output.add(fluidss);
        }

        return output;
    }

    public int getInputCount() {
        if (this.getFluid() == null) {
            return 0;
        }
        for (recipe<Input, Output> inputOutputrecipe : this.recipeList) {
            if(this.getFluid().getFluid() == inputOutputrecipe.getInput().getFluid()) {
                return inputOutputrecipe.getInput().amount;
            }
        }
        return 0;
    }
    @Nullable
    public Output getRecipeOutput() {
        FluidStack input = this.getFluid();

        int var = -1;
        if (input != null) {
            var = this.canInputFluids.indexOf(input.getFluid());
        }

        if(var == -1) return null;
        return this.canOutputFluids.get(var);
    }
}
