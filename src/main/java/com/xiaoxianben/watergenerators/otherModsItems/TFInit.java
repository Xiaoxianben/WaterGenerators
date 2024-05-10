package com.xiaoxianben.watergenerators.otherModsItems;

public class TFInit implements IOtherModInit {


    @Override
    public void initGeneratorWater() {
        TFBlocks.initGeneratorWater();
    }

    @Override
    public void initGeneratorTurbine() {
        TFBlocks.initGeneratorTurbine();
    }

    @Override
    public void initGeneratorFluid() {
        TFBlocks.initGeneratorFluid();
    }

    @Override
    public void initGeneratorSteam() {
        TFBlocks.initGeneratorSteam();
    }

    @Override
    public void initMachineShell() {
        TFBlocks.initMachineShell();
    }

    @Override
    public void initMachineVaporization() {
        TFBlocks.initMachineVaporization();
    }

    @Override
    public void initGear() {
    }

    @Override
    public void initTurbineRotor() {
        TFItems.initTurbineRotor();
    }

    @Override
    public void initOre() {

    }

    @Override
    public void initRecipes() {
        TFItems.initRecipes();
        TFBlocks.initRecipes();
    }
}
