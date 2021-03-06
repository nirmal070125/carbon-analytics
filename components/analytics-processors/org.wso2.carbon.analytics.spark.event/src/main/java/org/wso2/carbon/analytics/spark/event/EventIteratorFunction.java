/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.analytics.spark.event;

import org.apache.spark.sql.Row;
import org.wso2.carbon.analytics.dataservice.core.Constants;
import org.wso2.carbon.analytics.datasource.commons.exception.AnalyticsException;
import scala.collection.Iterator;
import scala.runtime.AbstractFunction1;
import scala.runtime.BoxedUnit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Iterates over a resultset from the select query, builds events from them and
 * publishes to the event stream.
 */
public class EventIteratorFunction extends AbstractFunction1<Iterator<Row>, BoxedUnit> implements Serializable {
    
    private static final long serialVersionUID = 4048303072566432397L;

    private int tenantId;
    
    private String streamId;

    public EventIteratorFunction(int tenantId,String streamId) {
        this.tenantId = tenantId;
        this.streamId = streamId;
    }

    @Override
    public BoxedUnit apply(Iterator<Row> iterator) {
        List<List<Object>> storeEntries = new ArrayList<>(Constants.RECORDS_BATCH_SIZE);
        try {
            while (iterator.hasNext()) {
                storeEntries.add(this.createStoreEntryFromRow(iterator.next()));
                if (storeEntries.size() % Constants.RECORDS_BATCH_SIZE == 0) {
                    EventStreamDataStore.addToStore(this.tenantId, this.streamId, storeEntries);
                    storeEntries.clear();
                }
            }
            EventStreamDataStore.addToStore(this.tenantId, this.streamId, storeEntries);
        } catch (AnalyticsException e) {
            throw new RuntimeException("Error in writing event store entires: " + e.getMessage(), e);
        }
        return BoxedUnit.UNIT;
    }

    private List<Object> createStoreEntryFromRow(Row row) {
        List<Object> result = new ArrayList<Object>(row.length());
        for (int i = 0; i < row.length(); i++) {
            result.add(row.get(i));
        }
        return result;
    }
    
}
