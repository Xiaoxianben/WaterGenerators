package com.xiaoxianben.watergenerators.fluid;

import com.xiaoxianben.watergenerators.recipe.recipeIngredient;
import com.xiaoxianben.watergenerators.recipe.recipeTypes;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FluidTankRecipeInt extends FluidTankBase {
    public List<recipeIngredient> recipeList;
    public List<Fluid> canInputFluids;

    public FluidTankRecipeInt(int capacity, List<recipeIngredient> recipeList) {
        super(capacity);
        this.recipeList = recipeList;
        this.canInputFluids = this.getCanInputFluids(recipeList);
    }

    public boolean canFillFluidType(FluidStack fluid) {
        return this.canInputFluids.contains(fluid.getFluid());
    }

    private List<Fluid> getCanInputFluids(List<recipeIngredient> recipeList) {
        List<Fluid> fluids = new ArrayList<>();

        for (recipeIngredient recipeIngredient : recipeList) {
            List<Fluid> fluidss = recipeIngredient.getInputs(recipeTypes.FLUID).stream().map(FluidStack::getFluid).collect(Collectors.toList());
            fluids.addAll(fluidss);
        }

        return fluids;
    }

    @Nullable
    public Fluid getRecipeOutput() {
        FluidStack input = this.getFluid();

        Fluid output = null;
        if (input != null) {
            for (recipeIngredient recipeIngredient : this.recipeList) {
                if (recipeIngredient.getInputs(recipeTypes.FLUID).get(0).getFluid() == input.getFluid()) {
                    // 这里假设所有的recipeIngredient都只有一个输出
                    // 实际情况可能需要根据具体recipeIngredient的输出类型进行处理
                    // 例如，如果recipeIngredient有多个输出，则需要遍历所有输出并选择第一个非null的输出
                    // 如果recipeIngredient没有输出，则需要进行其他处理
                    output = recipeIngredient.getOutputs(recipeTypes.FLUID).get(0).getFluid();
                    break;
                }
            }
        }

        return output;
    }
}
