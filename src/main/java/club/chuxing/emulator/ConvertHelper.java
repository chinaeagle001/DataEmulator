package club.chuxing.emulator;

import org.apache.commons.lang3.math.NumberUtils;

public class ConvertHelper {
    public static Number str2Number(String str, Class<?> type) throws Exception {
        if (type == Integer.class) {
            return NumberUtils.toInt(str);
        } else if (type == Long.class) {
            return NumberUtils.toLong(str);
        } else if (type == Float.class) {
            return NumberUtils.toFloat(str);
        } else if (type == Double.class) {
            return NumberUtils.toDouble(str);
        } else {
            throw new Exception("number convert exception, number:" + str + ", Class<?>:" + type);
        }
    }

    public static Class<?> getFieldType(String type) {
        type = type.toLowerCase();
        switch (type) {
            case "int":
                return Integer.class;
            case "long":
                return Long.class;
            case "float":
                return Float.class;
            case "double":
                return Double.class;
            case "string":
                return String.class;
            case "varchar":
                return String.class;
            default:
                return null;
        }
    }
}
