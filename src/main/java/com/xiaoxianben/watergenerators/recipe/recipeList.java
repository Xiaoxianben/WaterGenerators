package com.xiaoxianben.watergenerators.recipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.xiaoxianben.watergenerators.event.ConfigLoader;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;

public class recipeList {

    public static recipes<FluidStack, FluidStack> recipeVaporization;
    public static recipes<FluidStack, Float> recipeFluidGenerator;

    public static void init() {
        recipeVaporization = new recipes<>(
                recipeType.FLUID,
                recipeType.FLUID,
                Collections.singletonList(new FluidStack(FluidRegistry.WATER, 1)),
                Collections.singletonList(new FluidStack(FluidRegistry.getFluid("steam"), 1))
        );

        recipeFluidGenerator = new recipes<>(
                recipeType.FLUID,
                recipeType.FLOAT,
                Arrays.asList(
                        new FluidStack(FluidRegistry.WATER, 1),
                        new FluidStack(FluidRegistry.getFluid("steam"), 1)
                ),
                Arrays.asList(1.0f, 1.5f)
        );

        //读取配置文件
        String recipeDir = ConfigLoader.modConfigurationDirectory + "/" + ModInformation.MOD_ID + "/recipe";
        recipeVaporization = readJson(recipeDir + "/vaporization.json", recipeVaporization);
        recipeFluidGenerator = readJson(recipeDir + "/fluidGenerator.json", recipeFluidGenerator);
    }


    /**
     * 读取JSON文件，并返回一个recipes对象。
     * <p>如果文件不存在，则返回默认值，并且保存默认值到文件中。
     * <p>如果文件存在，则将文件转化为配方<tt>recipes</tt>。
     */
    @Nonnull
    public static <Input, Output> recipes<Input, Output> readJson(String path, recipes<Input, Output> defaultRecipe) {
        JsonArray jsonArray = readFileToJsonArray(path);
        // 如果文件不存在，则返回默认值
        if (jsonArray == null) {
            saveRecipe(path, defaultRecipe);
            return defaultRecipe;
        }
        recipes<Input, Output> newRecipes = new recipes<>(defaultRecipe.recipeTypeInput, defaultRecipe.recipeTypeOutput);
        newRecipes.JsonToRecipe(jsonArray);
        return newRecipes;
    }

    /**
     * 保存一个recipes对象到JSON文件中。
     *
     * @param path          文件的路径。
     * @param defaultRecipe 默认的配方。
     */
    public static <Input, Output> void saveRecipe(String path, @Nonnull recipes<Input, Output> defaultRecipe) {
        JsonArray jsonArray = defaultRecipe.recipeToJson();
        saveJson(jsonArray, path);
    }

    /**
     * 保存json到文件中。
     *
     * @param jsonArray 要保存的json数组。
     * @param path      保存json数组的文件路径。
     */
    public static void saveJson(JsonArray jsonArray, String path) {
        saveJson(jsonArray, new File(path));
    }

    /**
     * 保存json到文件中。
     *
     * @param jsonArray 要保存的json数组。
     * @param file      保存json数组的文件。
     */
    public static void saveJson(JsonArray jsonArray, @Nonnull File file) {
        try {
            // 创建文件所在的目录（如果不存在）
            if (!file.getParentFile().exists() || !file.getParentFile().isDirectory()) {
                file.getParentFile().mkdirs();
            }
            // 创建文件（如果不存在）
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonArray, writer);
            writer.close();
        } catch (IOException e) {
            ConfigLoader.logger().throwing(e);
        }
    }

    /**
     * 读取JSON文件，并返回一个JsonArray。
     * <p>如果文件不存在或文件无法解析为<tt>JsonArray</tt>，则返回null。
     * <p>如果文件存在，则将文件转化为<tt>JsonArray</tt>。
     */
    @Nullable
    private static JsonArray readFileToJsonArray(String filePath) {
        // 读取文件内容
        String content;
        JsonArray jsonArray = null;
        InputStream in;
        try {
            in = FileUtils.openInputStream(new File(filePath));
            content = IOUtils.toString(in, Charsets.toCharset("UTF-8"));
            jsonArray = new JsonParser().parse(content).getAsJsonArray();
        } catch (FileNotFoundException ignored) {
        } catch (Exception e) {
            ConfigLoader.logger().throwing(e);
        }
        // 解析JSON数组
        return jsonArray;
    }

}
