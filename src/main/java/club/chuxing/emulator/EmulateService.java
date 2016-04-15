package club.chuxing.emulator;

import com.mysql.jdbc.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmulateService {
    private static final Logger logger = LoggerFactory.getLogger(EmulateService.class);
    private String filename;
    private EmulateInfo emulatorInfo = new EmulateInfo();
    public EmulateService(String filename) {
        this.filename = filename;
        init();
    }

    private void init() {
        try {
            List<TableInfo> tableInfoList = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(this.filename));
            String line;
            TableInfo tableInfo = null;
            List<FieldInfo> fieldInfoList = null;
            while ((line = reader.readLine()) != null) {
                if (StringUtils.isNullOrEmpty(line)) {
                    continue;
                }
                if (StringUtils.startsWithIgnoreCase(line, 0, "table")) {
                    if (tableInfo != null) {
                        tableInfo.fieldInfos = fieldInfoList;
                        tableInfoList.add(tableInfo);
                    }
                    tableInfo = new TableInfo();
                    fieldInfoList = new ArrayList<>();
                    String[] strs = line.split(":");
                    if (strs.length != 2) {
                        throw new Exception("table name line is error, line:" + line);
                    } else {
                        String[] nameAndLine = strs[1].split(" ");
                        tableInfo.setTablename(nameAndLine[0].trim());
                        if (nameAndLine.length == 2) {
                            tableInfo.rows = NumberUtils.toInt(nameAndLine[1].trim());
                        }
                    }
                } else {
                    FieldInfo fieldInfo = FieldInfo.of(line);
                    if (fieldInfoList == null) {
                        logger.info("请先输入表名,再输入字段信息");
                        throw new Exception("表名信息缺失");
                    } else {
                        fieldInfoList.add(fieldInfo);
                    }
                }
            }
            if (tableInfo != null) {
                tableInfo.fieldInfos = fieldInfoList;
                tableInfoList.add(tableInfo);
            }
            emulatorInfo.tableInfos = tableInfoList;

        } catch (FileNotFoundException ex) {
            logger.info("FileNotFoundException, ex:{}", ex);
        } catch (IOException ex) {
            logger.info("IOException, ex:{}", ex);
        } catch (Exception ex) {
            logger.info("Exception, ex:{}", ex);
        }
    }

    public String emulate() throws Exception {
        return emulatorInfo.produce();
    }

    public String emulate(int rows) throws Exception {
        return emulatorInfo.produce(rows);
    }
}
