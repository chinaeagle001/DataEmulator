package club.chuxing.emulator;

import java.util.List;

public class EmulateInfo {
    public List<TableInfo> tableInfos;

    public String produce() throws Exception {
        StringBuffer sb = new StringBuffer();
        for (TableInfo tableInfo : tableInfos) {
            sb.append(tableInfo.produce());
            sb.append("\n\n");
        }
        return sb.toString();
    }

    public String produce(int rows) throws Exception {
        StringBuffer sb = new StringBuffer();
        for (TableInfo tableInfo : tableInfos) {
            sb.append(tableInfo.produce(rows));
            sb.append("\n\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "EmulateInfo{" +
                "tableInfos=" + tableInfos +
                '}';
    }
}
