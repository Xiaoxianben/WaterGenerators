package com.xiaoxianben.watergenerators.event;

import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;

public class ConfigLoader {
    public static String categoryGenerator = "generator";
    public static String modConfigurationDirectory;

    /**
     * 默认为 1b/1000mb
     */
    public static int basicAmountOfFluidToProduceEnergy;
    /**
     * 默认为 1b/1000mb
     */
    public static int basicAmountOfWaterToProduceEnergy;
    /**
     * 默认为 1mb
     */
    public static int basicAmountOfSteamToProduceEnergy;
    public static int energyBasic;
    public static float waterMagnification;
    public static float steamMagnification;

    private static Configuration config;
    private static Logger logger;

    public static void preInitConfigLoader(@Nonnull FMLPreInitializationEvent event) {
        logger = event.getModLog();
        modConfigurationDirectory = event.getModConfigurationDirectory().getAbsolutePath();
        config = new Configuration(new File(modConfigurationDirectory + "/" + ModInformation.MOD_ID + "/" + ModInformation.MOD_ID + ".cfg"));

        //实例化了一个Configuration类,括号中填的是Forge推荐的配置文件位置,这个位置在游戏根目录的config文件夹下，
        //名为<modid>.cfg，这里就是bm.cfg。

        config.load();//读取配置
        load();
    }

    public static int addInt(String name, String category, int defaultValue) {
        return addInt(name, category, defaultValue, "config." + name + ".comment");
    }

    public static int addInt(String name, int defaultValue) {
        return addInt(name, defaultValue, "config." + name + ".comment");
    }

    public static int addInt(String name, int defaultValue, String common) {
        return addInt(name, Configuration.CATEGORY_GENERAL, defaultValue, common);
    }

    public static int addInt(String name, String category, int defaultValue, String common) {
        int tempInt = config.getInt(name, category, defaultValue, 1, Integer.MAX_VALUE, I18n.format(common));
        config.save();
        return tempInt;
    }

    public static long addLong(String name, String category, long defaultValue) {
        return addLong(name, category, defaultValue, I18n.format("config." + name + ".comment"));
    }

    public static long addLong(String name, String category, long defaultValue, String common) {
        Property tempProperty = config.get(category, name, Long.toString(defaultValue), common, Property.Type.INTEGER);
        tempProperty.setMinValue(0);
        tempProperty.setMaxValue(Long.MAX_VALUE);
        tempProperty.setComment(tempProperty.getComment() + " [range: 0 ~ " + Long.MAX_VALUE + ", default: " + defaultValue + "]");

        long returnLong = tempProperty.getLong();
        config.save();
        return returnLong;
    }

    public static float addFloat(String name, String category, float defaultValue) {
        return addFloat(name, category, defaultValue, I18n.format("config." + name + ".comment"));
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
        config.setCategoryComment(categoryGenerator, I18n.format("category.generator.comment"));

        basicAmountOfFluidToProduceEnergy = addInt("basicAmountOfFluidToProduceEnergy", categoryGenerator, 1000);
        basicAmountOfWaterToProduceEnergy = addInt("basicAmountOfWaterToProduceEnergy", categoryGenerator, 100);
        basicAmountOfSteamToProduceEnergy = addInt("basicAmountOfSteamToProduceEnergy", categoryGenerator, 10);

        waterMagnification = addFloat("waterMagnification", categoryGenerator, 1.5f);
        steamMagnification = addFloat("steamMagnification", categoryGenerator, 2.5f);

        energyBasic = addInt("energyBasic", 10);


        config.save(); //保存配置
        //至于为什么要保存配置呢？这是因为当配置缺失（最常见的原因就是配置文件没有创建，
        //这常常发生在你第一次使用Mod的时候）的时候，这一句会将默认的配置保存下来。
        logger.info("Finished loading config."); //输出完成加载配置文件
    }

    public static Logger logger() {
        return logger;
    }
}
