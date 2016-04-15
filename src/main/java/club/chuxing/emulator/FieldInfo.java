package club.chuxing.emulator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FieldInfo<T> {
    private static final Logger logger = LoggerFactory.getLogger(FieldInfo.class);
    private static final String PRIMARY_KEY = "pk";
    private static final String NOT_NULL = "nn";

    private String fieldName;
    private Class<?> fieldType;
    private Number min;
    private Number max;
    private Set<T> valueSet;
    private Constraint constraint;

    private Set<T> generatedValues = new HashSet<>(); //已生成的数据

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class<?> fieldType) {
        this.fieldType = fieldType;
    }

    public Number getMax() {
        return max;
    }

    public void setMax(Number max) {
        this.max = max;
    }

    public Number getMin() {
        return min;
    }

    public void setMin(Number min) {
        this.min = min;
    }

    public Set<T> getValueSet() {
        return valueSet;
    }

    public void setValueSet(Set<T> valueSet) {
        this.valueSet = valueSet;
    }

    public Constraint getConstraint() {
        return constraint;
    }

    public void setConstraint(Constraint constraint) {
        this.constraint = constraint;
    }

    public Set<T> getGeneratedValues() {
        return generatedValues;
    }

    public void setGeneratedValues(Set<T> generatedValues) {
        this.generatedValues = generatedValues;
    }

    public boolean reachMaxRows() {
        if (!isPrimaryKey()) {
            return false;
        }

        int rows = generatedValues != null ? generatedValues.size() : 0;
        if (!hasValueRanges()) {
            if (valueSet.size() == rows) {
                return true;
            }
        }

        if (fieldType == Long.class) {
            if (hasValueRanges () && (long)max - (long)min == rows) {
                return true;
            }
        } else if (fieldType == Integer.class) {
            if (hasValueRanges () && (int)max - (int)min == rows) {
                return true;
            }
        }
        return false;
    }

    public boolean hasValueRanges() {
        return min != null && max != null;
    }

    public boolean hasValueSet() {
        return !CollectionUtils.isEmpty(valueSet);
    }

    public boolean isValid() {
        return !StringUtils.isEmpty(fieldName) && fieldType != null;
    }

    public boolean isPrimaryKey() {
        return constraint != null && constraint.isPrimaryKey();
    }

    public boolean isNotNUll() {
        return constraint != null && constraint.isNotNull();
    }

    public String getNextString() throws Exception {
        String str;
        if (isValid() &&  fieldType == String.class) {
            if (isPrimaryKey() && !hasValueSet() && !hasValueRanges()) {
                logger.info("字段{}为主键,但是缺失取值范围和取值集合信息", fieldName);
                throw new Exception("字段" + fieldName + "为主键,但是缺失取值范围和取值集合信息");
            }
            if (hasValueSet()) {
                str =  (String)RandomHelper.nextValue(valueSet);
            } else {
                str = "";
            }
        } else {
            throw new Exception("字段类型不是String, 不可获取字符串值");
        }

        if (isPrimaryKey()) {
            if (generatedValues.contains(str) && !reachMaxRows()) {
                str = getNextString();
            } else {
                generatedValues.add((T)str);
            }
        }
        return "'" + str + "'";
    }

    public Number getNextNumber() throws Exception {
        if (!isValid()) {
            logger.info("字段{}为无效字段",fieldName);
            throw new Exception("字段" + fieldName + "为无效字段");
        }
        Number num;
        if (hasValueSet()) {
            if (fieldType == Float.class || fieldType == Double.class) {
                num = NumberUtils.toDouble((String)RandomHelper.nextValue(valueSet));
            } else {
                num = NumberUtils.toLong((String)RandomHelper.nextValue(valueSet));
            }
        } else if (hasValueRanges()) {
            num = getRandomRangeNumber();
        } else {
            num = getRandomNumber();
        }

        if (isPrimaryKey()) {
            if (generatedValues.contains(num)) {
                if (reachMaxRows()) {
                    logger.info("主键字段{}已达到最大字段数生成的上限", fieldName);
                    throw new Exception("主键字段" + fieldName + "已达到最大字段数生成的上限");
                } else {
                    num = getNextNumber();
                }
            } else {
                generatedValues.add((T)num);
            }
        }

        if (fieldType == Integer.TYPE || fieldType == Long.TYPE) {
            num = Math.round((double)num);
        }

        return num;
    }

    private Number getRandomRangeNumber() throws Exception {
        Number num;
        if (fieldType == Integer.class) {
            num = RandomHelper.nextInt((int) min, (int) max);
        } else if (fieldType == Long.class) {
            num = RandomHelper.nextLong((long)min, (long)max);
        } else if (fieldType == Float.class) {
            num = RandomHelper.nextFloat((float)min, (float)max);
        } else if (fieldType == Double.class) {
            num = RandomHelper.nextDouble((double)min, (double)max);
        } else {
            num = null;
        }
        return num;
    }

    private Number getRandomNumber() {
        Number num;
        if (fieldType == Integer.class) {
            num = RandomHelper.random.nextInt();
        } else if (fieldType == Long.class) {
            num = RandomHelper.random.nextLong();
        } else if (fieldType == Float.class) {
            num = RandomHelper.random.nextFloat();
        } else if (fieldType == Double.class) {
            num = RandomHelper.random.nextDouble();
        } else {
            return null;
        }
        return num;
    }


    public static FieldInfo of(final String line) throws Exception {
        String[] strs = line.split(" ");
        if (strs.length < 2) {
            throw new Exception("field line is error, line:" + line);
        } else {
            logger.info("开始处理字段信息, 字段信息:{}", line);
            String fieldName = strs[0]; // 字段名
            String fieldType = strs[1]; // 字段类型
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setFieldName(fieldName);
            Class<?> type = ConvertHelper.getFieldType(fieldType);
            if (type != null) {
                fieldInfo.setFieldType(type);
            } else {
                logger.info("错误的字段类型信息,字段类型:{}", fieldType);
                throw new Exception("错误的字段类型信息");
            }

            if (strs.length > 2) {
                // 设置了取值范围、取值集合或约束信息
                String thirdWord = strs[2];
                if (thirdWord.startsWith("[") && thirdWord.endsWith(")")) {
                    // 设置了取值范围
                    logger.info("取值范围信息:{}", thirdWord);
                    thirdWord = thirdWord.substring(1, thirdWord.length() - 1);
                    String[] nums = thirdWord.split(",");
                    if (nums.length != 2) {
                        throw new Exception("field value range is error, ranges:" + thirdWord);
                    } else {
                        fieldInfo.setMin(ConvertHelper.str2Number(nums[0], type));
                        fieldInfo.setMax(ConvertHelper.str2Number(nums[1], type));
                    }
                } else if (thirdWord.startsWith("{") && thirdWord.endsWith("}")) {
                    // 设置了取值集合
                    logger.info("取值集合信息:{}", thirdWord);
                    thirdWord = thirdWord.substring(1, thirdWord.length() - 1);
                    String[] values = thirdWord.split(",");
                    Set<String> stringSet = new HashSet<>(Arrays.asList(values));
                    fieldInfo.setValueSet(stringSet);
                } else if (StringUtils.equals(thirdWord, PRIMARY_KEY) || StringUtils.equals(thirdWord, NOT_NULL)) {
                    Constraint constraint = new Constraint();
                    if (StringUtils.equals(thirdWord, PRIMARY_KEY)) {
                        constraint.setPrimaryKey(true);
                    } else if (StringUtils.equals(thirdWord, NOT_NULL)) {
                        constraint.setNotNull(true);
                    }
                    fieldInfo.constraint = constraint;
                }

                // 设置约束
                int constraintStartIndex = line.indexOf(thirdWord) + thirdWord.length() + 2;
                String constraintStr = null;
                if (constraintStartIndex < line.length()) {
                    constraintStr = line.substring(constraintStartIndex);
                }
                if (!StringUtils.isEmpty(constraintStr)) {
                    logger.info("约束信息:{}", constraintStr);

                    Constraint constraint;
                    if (fieldInfo.constraint != null) {
                        constraint = fieldInfo.constraint;
                    } else {
                        constraint = new Constraint();
                    }

                    if (constraintStr.contains(PRIMARY_KEY)) {
                        constraint.setPrimaryKey(true);
                    }
                    if (constraintStr.contains(NOT_NULL)) {
                        constraint.setNotNull(true);
                    }

                    if (fieldInfo.constraint == null) {
                        fieldInfo.constraint = constraint;
                    }
                }
            }

            logger.info("处理字段信息完成, FieldInfo:{}", fieldInfo);
            return fieldInfo;
        }
    }

    @Override
    public String toString() {
        return "FieldInfo{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldType=" + fieldType +
                ", min=" + min +
                ", max=" + max +
                ", valueSet=" + valueSet +
                ", constraint=" + constraint +
                '}';
    }

    static class Constraint {
        private boolean primaryKey;
        private boolean notNull;

        public boolean isPrimaryKey() {
            return primaryKey;
        }

        public void setPrimaryKey(boolean primaryKey) {
            this.primaryKey = primaryKey;
        }

        public boolean isNotNull() {
            return notNull;
        }

        public void setNotNull(boolean notNull) {
            this.notNull = notNull;
        }

        @Override
        public String toString() {
            return "Constraint{" +
                    "primaryKey=" + primaryKey +
                    ", notNull=" + notNull +
                    '}';
        }
    }
}
