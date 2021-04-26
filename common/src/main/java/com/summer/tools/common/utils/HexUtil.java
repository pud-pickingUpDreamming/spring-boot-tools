package com.summer.tools.common.utils;

/**
 * @author john
 */
public class HexUtil {

    private static final char[] HEX_CHAR_ARRAY = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

    private static final String HEX_STR = "0123456789abcdef";

    public static String toString(byte[] btArr) {

        if (btArr == null) {
            return null;
        }

        // 一个字节对应8位,转换成2个16进制数
        char[] strArr = new char[btArr.length * 2];
        int i = 0;
        for (byte bt : btArr) {
            // bt的高4位转换成16进制
            strArr[i++] = HEX_CHAR_ARRAY[bt>>>4 & 0xf];
            // bt的低四位转换成16进制
            strArr[i++] = HEX_CHAR_ARRAY[bt & 0xf];
        }
        return new String(strArr);
    }

    /**
     *
     * @param hexStr 字符串
     * @return 字节数组
     */
    public static byte[] toByteArr(String hexStr) {

        if (hexStr == null) {
            return null;
        }

        char[] charArr = hexStr.toCharArray();
        byte[] btArr = new byte[charArr.length / 2];
        int index = 0;
        for (int i = 0; i < charArr.length; i++) {
            int highBit = HEX_STR.indexOf(charArr[i]);
            int lowBit = HEX_STR.indexOf(charArr[++i]);
            btArr[index] = (byte) (highBit << 4 | lowBit);
            index++;
        }
        return btArr;
    }
}
