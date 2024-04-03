package com.xiaoxianben.watergenerators.recipe;

import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class ListFluidStack {
    public final List<FluidStack> fluidStackList = new ArrayList<>();

    public void add(FluidStack FluidStack) {
        this.fluidStackList.add(FluidStack);
    }

    public void add(List<FluidStack> fluidStackList) {
        this.fluidStackList.addAll(fluidStackList);
    }

    public void remove(FluidStack FluidStack) {
        this.fluidStackList.remove(FluidStack);
    }

    public void remove(List<FluidStack> fluidStackList) {
        this.fluidStackList.removeAll(fluidStackList);
    }
}
