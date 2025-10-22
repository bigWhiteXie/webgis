package com.ruoyi.common.enums;

/**
 * 水质等级枚举
 * 从I类到劣V类，等级逐渐变差
 */
public enum WaterQualityLevel {
    /**
     * I类水质 - 最好
     */
    CLASS_I("I类"),

    /**
     * II类水质
     */
    CLASS_II("II类"),

    /**
     * III类水质
     */
    CLASS_III("III类"),

    /**
     * IV类水质
     */
    CLASS_IV( "IV类"),

    /**
     * V类水质
     */
    CLASS_V( "V类"),

    /**
     * 劣V类水质 - 最差
     */
    CLASS_INFERIOR_V("劣V类");

    private final String label;

    WaterQualityLevel( String label) {
        this.label = label;
    }


    public String getLabel() {
        return label;
    }

    /**
     * 根据code获取对应的枚举值
     *
     * @param label 等级
     * @return 对应的枚举值，未找到返回null
     */
    public static WaterQualityLevel getByLabel(String label) {
        for (WaterQualityLevel level : values()) {
            if (level.getLabel().equals(label)) {
                return level;
            }
        }
        return null;
    }

    /**
     * 比较两个水质等级的优劣
     * 
     * @param level1 水质等级1
     * @param level2 水等级2
     * @return 如果level1优于level2返回负数，相等返回0，劣于返回正数
     */
    public static int compareLevel(WaterQualityLevel level1, WaterQualityLevel level2) {
        if (level1 == null && level2 == null) {
            return 0;
        }
        if (level1 == null) {
            return 1;
        }
        if (level2 == null) {
            return -1;
        }
        
        return level1.ordinal() - level2.ordinal();
    }
    
    /**
     * 比较两个水质等级字符串的优劣
     * 
     * @param level1 水质等级字符串1
     * @param level2 水质等级字符串2
     * @return 如果level1优于level2返回负数，相等返回0，劣于返回正数
     */
    public static int compareLevel(String level1, String level2) {
        WaterQualityLevel wl1 = getByLabel(level1);
        WaterQualityLevel wl2 = getByLabel(level2);
        return compareLevel(wl1, wl2);
    }
}