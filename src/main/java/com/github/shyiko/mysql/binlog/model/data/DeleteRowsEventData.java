/*
 * Copyright 2013 Stanley Shyiko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.shyiko.mysql.binlog.model.data;

import com.github.shyiko.mysql.binlog.model.event.EventData;

import java.io.Serializable;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

/* delete操作对应的binlog */
public class DeleteRowsEventData implements EventData {
    private long tableId;
    private BitSet includedColumns;     // 涉及到的column
    private List<Serializable[]> rows;  // 删除的记录数据

    public long getTableId() { return tableId; }
    public void setTableId(long tableId) { this.tableId = tableId; }
    public BitSet getIncludedColumns() { return includedColumns; }
    public void setIncludedColumns(BitSet includedColumns) { this.includedColumns = includedColumns; }
    public List<Serializable[]> getRows() { return rows; }
    public void setRows(List<Serializable[]> rows) { this.rows = rows; }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DeleteRowsEventData");
        sb.append("{tableId=").append(tableId);
        sb.append(", includedColumns=").append(includedColumns);
        sb.append(", rows=[");
        for (Object[] row : rows) {
            sb.append("\n    ").append(Arrays.toString(row)).append(",");
        }
        if (!rows.isEmpty()) {
            sb.replace(sb.length() - 1, sb.length(), "\n");
        }
        sb.append("]}");
        return sb.toString();
    }
}
