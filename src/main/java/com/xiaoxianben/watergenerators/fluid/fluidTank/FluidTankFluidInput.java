package com.xiaoxianben.watergenerators.fluid.fluidTank;

import com.xiaoxianben.watergenerators.recipe.recipes;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class FluidTankFluidInput<Input extends FluidStack, Output extends FluidStack> extends FluidTankBase {
    public recipes<Input, Output> recipeList;
    public List<Fluid> canInputFluids;
    public List<Output> canOutputFluids;

    public FluidTankFluidInput(int capacity, recipes<Input, Output> recipeList) {
        super(capacity);
        this.recipeList = recipeList;
        this.canInputFluids = this.getCanInputFluids(recipeList);
        this.canOutputFluids = this.getCanOutputFluids(recipeList);
    }

    public boolean canFillFluidType(FluidStack fluid) {
        return this.canInputFluids.contains(fluid.getFluid());
    }

    private List<Fluid> getCanInputFluids(recipes<Input, Output> recipeList) {
        List<Fluid> fluids = new ArrayList<>();

        for (Input recipe : recipeList.getInput()) {
            Fluid fluidss = recipe.getFluid();
            fluids.add(fluidss);
        }

        return fluids;
    }

    private List<Output> getCanOutputFluids(recipes<Input, Output> recipeList) {
        return new ArrayList<>(recipeList.getOutput());
    }

    public int getInputCount() {
        if (this.getFluid() == null) {
            return 0;
        }
        Fluid inputRecipe = this.canInputFluids.stream()
                .filter(fluid -> fluid == this.getFluid().getFluid())
                .findFirst()
                .orElse(null);
        if (inputRecipe != null) {
            return this.recipeList
                    .getInput()
                    .get(this.canInputFluids.indexOf(inputRecipe))
                    .amount;
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

        if (var == -1) return null;
        return this.canOutputFluids.get(var);
    }
}
