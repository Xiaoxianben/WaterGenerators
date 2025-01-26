package com.xiaoxianben.watergenerators.config;

import com.xiaoxianben.watergenerators.WaterGenerators;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;

public class ConfigLoader {
    public static final String categoryGenerator = "generator";
    public static String modConfigurationDirectory;
    public static String WGConfigDirectory;

    private static Configuration config;
    private static Logger logger;

    public static void preInitConfigLoader(@Nonnull FMLPreInitializationEvent event) {
        logger = event.getModLog();
        modConfigurationDirectory = event.getModConfigurationDirectory().getAbsolutePath();
        WGConfigDirectory = modConfigurationDirectory + "/" + WaterGenerators.MOD_ID;
        config = new Configuration(new File(WGConfigDirectory + "/" + WaterGenerators.MOD_ID + ".cfg"));

        //实例化了一个Configuration类,括号中填的是Forge推荐的配置文件位置,这个位置在游戏根目录的config文件夹下，
        //名为<modid>.cfg，这里就是bm.cfg。

        config.load();//读取配置
        load();
    }


    public static int addInt(String name, String category, int defaultValue) {
        int tempInt = config.getInt(name, category, defaultValue, 1, Integer.MAX_VALUE, I18nOwn.translateToLocal("config." + name + ".comment"));
        config.save();
        return tempInt;
    }

    public static float addFloat(String name, String category, float defaultValue) {
        return addFloat(name, category, defaultValue, I18nOwn.translateToLocal("config." + name + ".comment"));
    }

    public static float addFloat(String name, String category, float defaultValue, String common) {
        Property tempProperty = config.get(category, name, Float.toString(defaultValue), common, Property.Type.INTEGER);
        tempProperty.setMinValue(0);
        tempProperty.setMaxValue(Float.MAX_VALUE);
        tempProperty.setComment(tempProperty.getComment() + " [range: 0 ~ " + Float.MAX_VALUE + ", default: " + defaultValue + "]");

        float returnLong = (float) tempProperty.getDouble();
        config.save();
        return returnLong;
    }


    public static void load() {
        logger.info("Started loading config.");
        config.setCategoryComment(categoryGenerator, I18nOwn.translateToLocal("category.generator.comment"));

        ConfigValue.basicAmountOfFluidToProduceEnergy = addInt("basicAmountOfFluidToProduceEnergy", categoryGenerator, 1000);
//        ConfigValue.basicAmountOfWaterToProduceEnergy = addInt("basicAmountOfWaterToProduceEnergy", categoryGenerator, 500);
//        ConfigValue.basicAmountOfSteamToProduceEnergy = addInt("basicAmountOfSteamToProduceEnergy", categoryGenerator, 500);

        ConfigValue.waterMagnification = addFloat("waterMagnification", categoryGenerator, 1.5f);
        ConfigValue.steamMagnification = addFloat("steamMagnification", categoryGenerator, 4.0f);

        ConfigValue.energyBasic = addInt("energyBasic", Configuration.CATEGORY_GENERAL, 10);


        config.save(); //保存配置
        //至于为什么要保存配置呢？这是因为当配置缺失（最常见的原因就是配置文件没有创建，
        //这常常发生在你第一次使用Mod的时候）的时候，这一句会将默认的配置保存下来。
        logger.info("Finished loading config."); //输出完成加载配置文件
    }

    public static Logger logger() {
        return logger;
    }
}
