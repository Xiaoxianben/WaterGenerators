package com.xiaoxianben.watergenerators.config;

import net.minecraft.util.text.translation.I18n;

public class I18nOwn {

    public static String translateToLocal(String key) {
        if (I18n.canTranslate(key)) {
            return I18n.translateToLocal(key);
        } else {
            return I18n.translateToFallback(key);
        }
    }

}
