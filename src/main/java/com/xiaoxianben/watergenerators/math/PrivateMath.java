package com.xiaoxianben.watergenerators.math;

import java.util.Arrays;

public class PrivateMath {
    /**
     * 如果 flag 为 0，则返回 Arrays.toString(strings) <p>
     * 如果 flag 为 1，则返回 "{数值.2f} {数值的单位}" <p>
     * 如果 flag 为 2，则返回 "{数值的大小}"
     *
     * @return 获取粗略值
     */
    public static String getRoughData(long I, int flag) {
        String[] strings = getRoughData(I);

        if (flag == 0) return Arrays.toString(strings);
        else if (flag == 1) return strings[0] + strings[1];
        else return strings[2];
    }

    public static String[] getRoughData(long I) {
        String[] strings;

        // 如果最终接收能量大于800000000，则将最终接收能量转换为G
        if (I > 800000000) strings = new String[]{String.format("%.2f", I / 1000000000.0), "G", "9"};
            // 如果最终接收能量大于800000，则将最终接收能量转换为M
        else if (I > 800000) strings = new String[]{String.format("%.2f", I / 1000000.0), "M", "6"};
            // 如果最终接收能量大于800，则将最终接收能量转换为K
        else if (I > 800) strings = new String[]{String.format("%.2f", I / 1000.0), "K", "3"};
            // 否则，将最终接收能量转换为0
        else strings = new String[]{String.valueOf(I), "", "0"};

        return strings;
    }
}
