package club.chuxing.emulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class TableInfo {
    private static final Logger logger = LoggerFactory.getLogger(TableInfo.class);
    public String tablename;
    public int rows;
    public List<FieldInfo> fieldInfos;

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public boolean isValid() throws Exception {
        return !CollectionUtils.isEmpty(fieldInfos);
    }

    public String produce() throws Exception {
        return produce(rows);
    }

    public String produce(int rows) throws Exception {
        if (rows == 0) {
            return "";
        }
        if (!isValid()) {
            logger.info("表[表名:{}]字段信息缺失");
            throw new Exception("表" + tablename + "字段信息缺失");
        }
        StringBuffer tableRecords = getPrefix();
        for (int i = 0; i < rows; i++) {
            StringBuffer record = new StringBuffer();
            record.append("(");
            for (FieldInfo fieldInfo : fieldInfos) {
                if (record.length() != 1) {
                    record.append(",");
                }
                if (fieldInfo.getFieldType() == String.class) {
                    record.append(fieldInfo.getNextString());
                } else {
                    record.append(fieldInfo.getNextNumber());
                }
            }
            record.append(")");
            record.append(",");
            tableRecords.append(record);
            tableRecords.append("\n");
        }
        tableRecords.setLength(tableRecords.length() - 2);
        return tableRecords.toString();
    }

    private StringBuffer getPrefix() {
        StringBuffer sb = new StringBuffer();
        sb.append("insert into ").append(tablename).append("(").append(getFieldNames()).append(")").append(" values").append("\n");
        return sb;
    }

    private StringBuffer getFieldNames() {
        StringBuffer sb = new StringBuffer();
        for (FieldInfo fieldInfo : fieldInfos) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(fieldInfo.getFieldName());
        }
        return sb;
    }

    @Override
    public String toString() {
        return "TableInfo{" +
                "tablename='" + tablename + '\'' +
                ", rows=" + rows +
                ", fieldInfos=" + fieldInfos +
                '}';
    }
}
