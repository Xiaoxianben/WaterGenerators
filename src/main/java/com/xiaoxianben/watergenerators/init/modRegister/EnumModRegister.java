package com.xiaoxianben.watergenerators.init.modRegister;

import com.xiaoxianben.watergenerators.config.ConfigLoader;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EnumModRegister {
    MINECRAFT("minecraft", MinecraftRegister.class),
    TF("thermalfoundation;thermaldynamics", TFRegister.class),
    EnderIO("enderio", EnderIORegister.class),
    MEKANISM("mekanism", MekanismRegister.class);


    private final String[] modIds;
    private boolean isEnable = false;
    private Object register;

    EnumModRegister(String modId, Class<? extends IModRegister> register) {
        this.modIds = modId.split(";");
        this.register = register;
    }

    /** 激活 */
    public void enable() {
        List<Boolean> b = new ArrayList<>();
        for (String modId : modIds) {
            b.add(Loader.isModLoaded(modId));
        }
        isEnable = !b.contains(false);
        if (isEnable) {
            ConfigLoader.logger().info("the {} extension is loading.", modIds[0]);
            if (this.register instanceof Class) {
                try {
                    this.register = ((Class<?>) this.register).newInstance();
                } catch (Exception e) {
                    ConfigLoader.logger().error("the {} extension is not loaded.", modIds[0]);
                }
            }
        }
    }

    private boolean isEnable() {
        return isEnable;
    }

    @Nullable
    public IModRegister getRegister() {
        try {
            return isEnable ? (IModRegister) this.register : null;
        } catch (Exception e) {
            return null;
        }
    }

    public void preInit() {
        IModRegister register1 = getRegister();
        if (register1 != null) {
            register1.preInit();
        }
    }
    public void init() {
        IModRegister register1 = getRegister();
        if (register1 != null) {
            register1.init();
        }
    }
    public void posInit() {
        IModRegister register1 = getRegister();
        if (register1 != null) {
            register1.posInit();
            ConfigLoader.logger().info("the {} extension is loaded.", modIds[0]);
        }
        register = null;
    }

}
