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
package com.github.shyiko.mysql.binlog.deserialization;

import com.github.shyiko.mysql.binlog.model.data.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.model.data.TableMapEventData;
import com.github.shyiko.mysql.binlog.io.ByteArrayInputStream;

import java.io.IOException;
import java.io.Serializable;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* 解析delete事件 */
public class DeleteRowsEventDataDeserializer extends AbstractRowsEventDataDeserializer<DeleteRowsEventData> {
    private boolean mayContainExtraInformation;

    public DeleteRowsEventDataDeserializer(Map<Long, TableMapEventData> tableMapEventByTableId) {
        super(tableMapEventByTableId);
    }

    public DeleteRowsEventDataDeserializer setMayContainExtraInformation(boolean mayContainExtraInformation) {
        this.mayContainExtraInformation = mayContainExtraInformation;
        return this;
    }

    @Override
    public DeleteRowsEventData deserialize(ByteArrayInputStream inputStream) throws IOException {
        DeleteRowsEventData eventData = new DeleteRowsEventData();
        eventData.setTableId(inputStream.readLong(6));          // tableId  6 byte
        inputStream.readInteger(2);                             // reserved 2 byte
        if (mayContainExtraInformation) {
            int extraInfoLength = inputStream.readInteger(2);   // extraLen     2 byte
            inputStream.skip(extraInfoLength - 2);              // extraInfo    extraLen-2 byte
        }
        int numberOfColumns = inputStream.readPackedInteger();
        eventData.setIncludedColumns(inputStream.readBitSet(numberOfColumns, true));
        eventData.setRows(deserializeRows(eventData.getTableId(), eventData.getIncludedColumns(), inputStream));
        return eventData;
    }

    /* 解析各行数据 */
    private List<Serializable[]> deserializeRows(long tableId, BitSet includedColumns, ByteArrayInputStream inputStream) throws IOException {
        List<Serializable[]> result = new LinkedList<Serializable[]>();
        while (inputStream.available() > 0) {
            result.add(deserializeRow(tableId, includedColumns, inputStream));
        }
        return result;
    }
}
