package com.xiaoxianben.watergenerators.recipe;

import com.google.gson.*;
import com.xiaoxianben.watergenerators.event.ConfigLoader;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class recipeList {

    public static List<recipe<FluidStack, FluidStack>> recipeVaporization;

    public static void init() {
        recipeVaporization = new ArrayList<>();
        recipeVaporization.add(
                addRecipeIngredient(
                        recipeTypes.FLUID,
                        recipeTypes.FLUID,
                        new FluidStack(FluidRegistry.WATER, 1),
                        new FluidStack(FluidRegistry.getFluid("steam"), 1)
                )
        );

        //读取配置文件
        String recipeDir = ConfigLoader.modConfigurationDirectory + "/" + ModInformation.MOD_ID + "/recipe";
        recipeVaporization = readJson(recipeDir + "/vaporization.json", recipeVaporization);

    }

    public static <T, Out> recipe<T, Out> addRecipeIngredient(recipeType<T> inputType, recipeType<Out> outputType, T input, Out output) {
        return new recipe<>(inputType, outputType, input, output);
    }

    /**
     * [
     * {
     * "input": {
     * "name": "fluid:water",
     * "count": 1
     * },
     * "output": {
     * "name": "fluid:steam",
     * "count": 1
     * }
     * }
     * ]
     */
    @Nonnull
    public static <Input, Output> List<recipe<Input, Output>> readJson(String path, List<recipe<Input, Output>> defaultRecipe) {
        JsonArray jsonArray = readFileToJsonArray(path);
        if (jsonArray == null || !jsonArray.isJsonArray() || jsonArray.size() == 0) {
            saveRecipe(path, defaultRecipe);
            return defaultRecipe;
        }
        List<recipe<Input, Output>> newRecipes = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement var = jsonArray.get(i);
            if (!var.isJsonObject()) {
                continue;
            }
            JsonObject input = JsonUtils.getJsonObject(var.getAsJsonObject(), "input");
            JsonObject output = JsonUtils.getJsonObject(var.getAsJsonObject(), "output");

            Input input1 = null;
            Output output1 = null;
            try {
                String outputName = output.get("name").getAsString();
                String inputName = input.get("name").getAsString();

                int outputCount = output.get("count").getAsInt();
                int inputCount = input.get("count").getAsInt();

                input1 = getT(defaultRecipe.get(0).getInputClass(), input, inputName, inputCount);
                output1 = getT(defaultRecipe.get(0).getOutputClass(), output, outputName, outputCount);
            } catch (Exception e) {
                ConfigLoader.logger().throwing(e);
            }
            if (input1 == null || output1 == null) {
                continue;
            }
            recipeType<Input> inputType = defaultRecipe.get(0).inputType;
            recipeType<Output> outputType = defaultRecipe.get(0).outputType;
            newRecipes.add(new recipe<>(inputType, outputType, input1, output1));
        }
        return newRecipes;
    }

    public static <Input, Output> void saveRecipe(String path, List<recipe<Input, Output>> defaultRecipe) {
        JsonArray jsonArray = new JsonArray();
        for (recipe<Input, Output> recipe : defaultRecipe) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("input", recipe.inputToJson());
            jsonObject.add("output", recipe.outputToJson());

            jsonArray.add(jsonObject);
        }
        saveJson(jsonArray, path);
    }

    public static void saveJson(JsonArray jsonArray, String path) {
        saveJson(jsonArray, new File(path));
    }

    public static void saveJson(JsonArray jsonArray, File file) {
        try {
            // 创建文件所在的目录（如果不存在）
            if (!file.getParentFile().exists() || !file.getParentFile().isDirectory()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile(); // 创建文件（如果不存在）
            }
            FileWriter writer = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonArray, writer);
            writer.close();
        } catch (IOException e) {
            ConfigLoader.logger().throwing(e);
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private static <T> T getT(Class<T> tClass, JsonObject json, String name, int count) {
        T input1 = null;
        String[] names = name.split(":");

        if (!Objects.equals(names[0], "item") && !Objects.equals(names[0], "fluid")) {
            name = "item:" + name;
            names = name.split(":");
        }

        if (tClass == ItemStack.class &&
                Objects.equals(names[0], "item") &&
                Item.getByNameOrId(names[1] + ":" + names[2]) != null
        ) {
            input1 = (T) new ItemStack(Objects.requireNonNull(Item.getByNameOrId(names[1] + ":" + names[2])), count, JsonUtils.getInt(json, "meta", 0));

        } else if (tClass == FluidStack.class &&
                Objects.equals(names[0], "fluid") &&
                FluidRegistry.getFluid(names[1]) != null
        ) {
            input1 = (T) new FluidStack(FluidRegistry.getFluid(names[1]), count);
        }

        return input1;
    }

    @Nullable
    private static JsonObject getJsonObject(JsonObject jsonObject, String key) {
        return jsonObject.has(key) && jsonObject.get(key).isJsonObject() ? jsonObject.get(key).getAsJsonObject() : null;
    }

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
