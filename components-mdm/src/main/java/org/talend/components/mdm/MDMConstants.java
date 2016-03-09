// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.components.mdm;

public class MDMConstants {

    public static final String DEFAULT_MDM_ROOT = "http://localhost:8180/talendmdm"; //$NON-NLS-1$

    public static final String DATA_SERVICE_BASE = "/services/rest/data"; //$NON-NLS-1$

    public static final String DEFAULT_DATA_SERVICE_ROOT = DEFAULT_MDM_ROOT + DATA_SERVICE_BASE;

    public static final String STRIP_CHARS = "\""; //$NON-NLS-1$

    public static final String QUERY = "query"; //$NON-NLS-1$

    public static final String CONTAINER = "container"; //$NON-NLS-1$

    public static final String DEFAULT_TYPE = "Type1"; //$NON-NLS-1$
    
    public static final String DEFAULT_USER = "userName"; //$NON-NLS-1$

    public static final String DEFAULT_QUERY = "{\"select\": {\"from\": [\"Type1\"]}}"; //$NON-NLS-1$
       
    public static final String MASTER = "Master"; //$NON-NLS-1$

    public static final String STAGING = "Staging"; //$NON-NLS-1$

    public static final String SYSTEM = "System"; //$NON-NLS-1$

    // BASIC
    public static final String SELECT = "select"; //$NON-NLS-1$

    public static final String FROM = "from"; //$NON-NLS-1$

    public static final String FIELDS = "fields"; //$NON-NLS-1$

    public static final String FIELD = "field"; //$NON-NLS-1$

    // METADATA
    public static final String METADATA = "metadata"; //$NON-NLS-1$

    public static final String TIMESTAMP = "timestamp"; //$NON-NLS-1$

    public static final String TASK_ID = "task_id"; //$NON-NLS-1$

    public static final String GROUP_SIZE = "group_size"; //$NON-NLS-1$

    public static final String STAGING_ERROR = "staging_error"; //$NON-NLS-1$

    public static final String STAGING_SOURCE = "staging_source"; //$NON-NLS-1$

    public static final String STAGING_STATUS = "staging_status"; //$NON-NLS-1$

    public static final String STAGING_BLOCKKEY = "staging_blockkey"; //$NON-NLS-1$

    // ALIAS
    public static final String ALIAS = "alias"; //$NON-NLS-1$

    public static final String NAME = "name"; //$NON-NLS-1$

    // distinct
    public static final String DISTINCT = "distinct"; //$NON-NLS-1$

    // max/min
    public static final String MAX = "max"; //$NON-NLS-1$

    public static final String MIN = "min"; //$NON-NLS-1$

    // count
    public static final String COUNT = "count"; //$NON-NLS-1$

    // join
    public static final String JOINS = "joins"; //$NON-NLS-1$

    public static final String JOIN_FROM = "from"; //$NON-NLS-1$

    public static final String JOIN_ON = "on"; //$NON-NLS-1$

    // WHERE
    public static final String WHERE = "where"; //$NON-NLS-1$

    public static final String AND = "and"; //$NON-NLS-1$

    public static final String OR = "or"; //$NON-NLS-1$

    public static final String NOT = "not"; //$NON-NLS-1$

    public static final String VALUE = "value"; //$NON-NLS-1$

    // WHERE - operand
    public static final String STARTS_WITH = "startsWith"; //$NON-NLS-1$

    public static final String LT = "lt"; //$NON-NLS-1$

    public static final String LTE = "lte"; //$NON-NLS-1$

    public static final String GT = "gt"; //$NON-NLS-1$

    public static final String GTE = "gte"; //$NON-NLS-1$

    public static final String EQ = "eq"; //$NON-NLS-1$

    public static final String CONTAINS = "contains"; //$NON-NLS-1$

    // WHERE - full text
    public static final String FULL_TEXT = "full_text"; //$NON-NLS-1$

    // order by
    public static final String ORDER_BYS = "order_bys"; //$NON-NLS-1$

    public static final String ORDER_BY = "order_by"; //$NON-NLS-1$

    public static final String DIRECTION = "direction"; //$NON-NLS-1$

    public static final String DESC = "DESC"; //$NON-NLS-1$

    public static final String ASC = "ASC"; //$NON-NLS-1$

    // paging
    public static final String START = "start"; //$NON-NLS-1$

    public static final String LIMIT = "limit"; //$NON-NLS-1$

    // history
    public static final String AS_OF = "as_of"; //$NON-NLS-1$

    public static final String DATE = "date"; //$NON-NLS-1$

    public static final String YESTERDAY = "yesterday"; //$NON-NLS-1$

    public static final String CREATION = "creation"; //$NON-NLS-1$

    public static final String NOW = "now"; //$NON-NLS-1$

    // cache
    public static final String CACHE = "cache"; //$NON-NLS-1$

    // index
    public static final String INDEX = "index"; //$NON-NLS-1$

}
