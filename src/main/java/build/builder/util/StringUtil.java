package build.builder.util;

/**
 * String字符串工具类
 *
 * @author peng_fu_lin
 * 2022-09-08 16:23
 */
public class StringUtil {


    /**循环拼接字符
     * 2022/9/8 0008-17:22
     * @author pengfulin
     * @param str 待拼接的字符
     * @param  cycleNum 循环次数
    */
    public static String cycleAppend(String str,int cycleNum){
        StringBuilder builder = new StringBuilder();
        cycleNum= Math.max(cycleNum, 1);
        for (int i = 0; i < cycleNum; i++)
            builder.append(str);
        return builder.toString();
    }


    /**清除指定字符
     * 2022/9/9 0009-15:47
     * @author pengfulin
     * @param value 待清除字符的字符串
     * @param charValue 待清除的字符
     * @param clearCharType 清除字符方式
     * @param clearCount 待清除字符的个数
     * <p>如果小于1，则会将字符串中所有的指定字符进行清除</p>
     * @return 返回清除指定字符后的字符串
    */
    public static String clearChar(String value, char charValue, ClearCharType clearCharType, int clearCount){
        StringBuilder builder = new StringBuilder();
        clearCount=clearCount<1? -1:clearCount;
        int count=0;
        if(clearCharType == ClearCharType.ALL){
            for (int i = 0; i < value.length(); i++) {
                if(value.charAt(i)==charValue&&(count<clearCount||clearCount==-1)){
                    count++;
                    continue;
                }
                builder.append(value.charAt(i));
            }
            return builder.toString();
        }
        int index=0;
        if(clearCharType == ClearCharType.START|| clearCharType == ClearCharType.NO_MIDDLE) {
            for (int i = 0; i < value.length(); i++) {
                index=i;
                if(value.charAt(i)==charValue&&(count<clearCount||clearCount==-1)){
                    count++;
                }else if(value.charAt(i)==charValue)
                    builder.append(value.charAt(i));
                else break;
            }
        }
        int length = value.length();
        if(clearCharType == ClearCharType.END|| clearCharType == ClearCharType.NO_MIDDLE) {
            for (int i = value.length()-1; i > 0; i--) {
                if(value.charAt(i)==charValue&&(count<clearCount||clearCount==-1)){
                    count++;
                    length--;
                } else
                    break;
            }
        }
        for (int i = index; i < length; i++) {
            builder.append(value.charAt(i));
        }
        return builder.toString();
    }

    public enum ClearCharType {
        ALL,
        START,
        END,
        NO_MIDDLE;
    }
}