/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.analytics.jsservice.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * This class contains the details about category drill down. This class is used  as an input to get
 * the subcategories of a facet field when using drillDownCategories API
 */
@XmlRootElement(name = "categoryDrillDownRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryDrillDownRequestBean {

    @XmlElement(name = "fieldName")
    private String fieldName;
    @XmlElement(name = "categoryPath", required = false)
    private List<String> categoryPath;
    @XmlElement(name = "query", required = false)
    private String query;
    @XmlElement(name = "scoreFunction", required = false)
    private String scoreFunction;
    @XmlElement(name = "start", required = false)
    private int start;
    @XmlElement(name = "count", required = false)
    private int count;

    public String getFieldName() {
        return fieldName;
    }

    public String getQuery() {
        return query;
    }

    public String getScoreFunction() {
        return scoreFunction;
    }

    public List<String> getCategoryPath() {
        return categoryPath;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
