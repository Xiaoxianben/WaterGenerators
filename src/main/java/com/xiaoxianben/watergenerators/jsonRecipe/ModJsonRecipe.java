package com.xiaoxianben.watergenerators.jsonRecipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xiaoxianben.watergenerators.config.ConfigLoader;
import com.xiaoxianben.watergenerators.jsonRecipe.ingredients.FluidStackAndEnergy;
import com.xiaoxianben.watergenerators.jsonRecipe.recipeType.RecipeTypes;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;

public class ModJsonRecipe {

    public static Recipes<FluidStack, FluidStackAndEnergy> recipeVaporization = new Recipes<>(RecipeTypes.recipe_fluidStack, RecipeTypes.recipe_fluidStackAndEnergy);
    public static Recipes<FluidStack, FluidStackAndEnergy> recipeConcentration = new Recipes<>(RecipeTypes.recipe_fluidStack, RecipeTypes.recipe_fluidStackAndEnergy);
    public static Recipes<Fluid, Float> recipeFluidGenerator = new Recipes<>(RecipeTypes.recipe_fluid, RecipeTypes.recipe_float);

    public void init() {
        recipeVaporization.addRecipe(
                new FluidStack(FluidRegistry.WATER, 1),
                new FluidStackAndEnergy(new FluidStack(FluidRegistry.getFluid("steam"), 1), -1)
        );
        recipeConcentration.addRecipe(
                new FluidStack(FluidRegistry.WATER, 100),
                new FluidStackAndEnergy(new FluidStack(FluidRegistry.getFluid("watercompressed"), 1), -1000)
        );

        recipeFluidGenerator.addRecipe(
                FluidRegistry.WATER,
                1.0f
        );
        recipeFluidGenerator.addRecipe(
                FluidRegistry.getFluid("watercompressed"),
                8f
        );
        recipeFluidGenerator.addRecipe(
                FluidRegistry.getFluid("steam"),
                2f
        );

        // 读取配置文件
        String recipeDir = ConfigLoader.WGConfigDirectory + "/recipe";
        recipeVaporization = readJson(recipeDir + "/vaporization.json", recipeVaporization);
        recipeFluidGenerator = readJson(recipeDir + "/fluidGenerator.json", recipeFluidGenerator);
        recipeConcentration = readJson(recipeDir + "/recipeConcentration.json", recipeConcentration);
    }


    /**
     * 读取JSON文件，并返回一个recipes对象。
     * <p>如果文件不存在，则返回默认值，并且保存默认值到文件中。
     * <p>如果文件存在，则将文件转化为配方<tt>recipes</tt>。
     */
    @Nonnull
    public <Input, Output> Recipes<Input, Output> readJson(String path, Recipes<Input, Output> defaultRecipe) {
        JsonObject jsonObject = readFileToJsonObject(path);
        // 如果文件不存在，则返回默认值
        if (jsonObject == null) {
            saveRecipe(path, defaultRecipe);
            return defaultRecipe;
        }
        Recipes<Input, Output> newRecipes;
        try {
            newRecipes = new Recipes<>(defaultRecipe.inputRecipeType, defaultRecipe.outputRecipeType);
            newRecipes.readRecipeJson(jsonObject);
        } catch (Exception e) {
            ConfigLoader.logger().error("无法读取文件：{}", path, e);
            throw new RuntimeException("无法读取文件：" + path, e);
        }

        return newRecipes;
    }

    /**
     * 保存一个recipes对象到JSON文件中。
     *
     * @param path          文件的路径。
     * @param defaultRecipe 默认的配方。
     */
    public <Input, Output> void saveRecipe(String path, @Nonnull Recipes<Input, Output> defaultRecipe) {
        JsonObject jsonObject = defaultRecipe.getRecipeJson();
        saveJson(jsonObject, new File(path));
    }

    /**
     * 保存json到文件中。
     *
     * @param jsonObject 要保存的json数组。
     * @param file       保存json数组的文件。
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveJson(JsonObject jsonObject, @Nonnull File file) {
        try {
            // 创建文件（如果不存在）
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonObject, writer);
            writer.close();
        } catch (IOException e) {
            ConfigLoader.logger().throwing(e);
        }
    }

    /**
     * 读取JSON文件，并返回一个 JsonObject。
     * <p>如果文件不存在或文件无法解析为 JsonObject，则返回null。
     * <p>如果文件存在，则将文件转化为 JsonObject.
     */
    @Nullable
    private JsonObject readFileToJsonObject(String filePath) {
        // 读取文件内容
        String content;
        JsonObject jsonObject;
        try (InputStream in = FileUtils.openInputStream(new File(filePath))) {
            content = IOUtils.toString(in, Charsets.toCharset("UTF-8"));
            jsonObject = new JsonParser().parse(content).getAsJsonObject();
        } catch (FileNotFoundException e) {
            jsonObject = null;
        } catch (Exception e) {
            ConfigLoader.logger().error("{} 文件读取出错", filePath, e);
            throw new RuntimeException(filePath + " 文件读取出错", e);
        }
        // 解析JSON数组
        return jsonObject;
    }

}
