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

import static org.talend.components.mdm.MDMConstants.ALIAS;
import static org.talend.components.mdm.MDMConstants.COUNT;
import static org.talend.components.mdm.MDMConstants.DEFAULT_TYPE;
import static org.talend.components.mdm.MDMConstants.DISTINCT;
import static org.talend.components.mdm.MDMConstants.FIELD;
import static org.talend.components.mdm.MDMConstants.FIELDS;
import static org.talend.components.mdm.MDMConstants.FROM;
import static org.talend.components.mdm.MDMConstants.MAX;
import static org.talend.components.mdm.MDMConstants.METADATA;
import static org.talend.components.mdm.MDMConstants.MIN;
import static org.talend.components.mdm.MDMConstants.SELECT;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class MDMCommonUtils {

    /**
     * Get field's name from field json object
     * 
     * @param fieldObject
     * @return
     * @throws JSONException
     */
    private static String getFieldName(JSONObject fieldObject) throws JSONException {
        if (fieldObject.has(FIELD)) {// {"field" : "Type1/Field1"}
            String xpath = fieldObject.getString(FIELD);
            return StringUtils.substringAfterLast(xpath, "/"); //$NON-NLS-1$
        } else { // {"metadata" : "task_id"}
            return fieldObject.getString(METADATA);
        }
    }

    /**
     * Guess the set of fields' name from query json string
     * 
     * @param query
     * @return
     */
    public static Collection<String> guessFieldsFromQuery(String query) throws JSONException {
        Set<String> fields = new LinkedHashSet<String>();
        JSONObject queryObject = new JSONObject(query);
        JSONArray fieldsArray = queryObject.getJSONObject(SELECT).getJSONArray(FIELDS);
        for (int i = 0; i < fieldsArray.length(); i++) {
            JSONObject fieldObject = fieldsArray.getJSONObject(i);
            if (fieldObject.has(ALIAS)) { // {"alias": [ {"name": "v1"}, {"distinct" : {"field":"Type1/Feild1"}} ]}
                fieldObject = fieldObject.getJSONArray(ALIAS).getJSONObject(1);
            }
            if (fieldObject.has(COUNT)) { // {"count" : {}}
                continue;
            } else {
                if (fieldObject.has(DISTINCT)) {
                    fieldObject = fieldObject.getJSONObject(DISTINCT);
                } else if (fieldObject.has(MAX)) {
                    fieldObject = fieldObject.getJSONObject(MAX);
                } else if (fieldObject.has(MIN)) {
                    fieldObject = fieldObject.getJSONObject(MIN);
                }
                if (fieldObject.has(FIELD) || fieldObject.has(METADATA)) {
                    fields.add(getFieldName(fieldObject));
                }
            }
        }
        return fields;
    }

    /**
     * Guess query from fields' name
     * 
     * @param fields
     * @return
     * @throws JSONException
     */
    public static String guessQueryFromFields(Collection<String> fields) throws JSONException {
        JSONArray fieldsArray = new JSONArray();
        for (String field : fields) {
            JSONObject fieldObject = new JSONObject();
            fieldObject.put(FIELD, DEFAULT_TYPE + '/' + field);
            fieldsArray.put(fieldObject);
        }
        JSONArray fromArray = new JSONArray().put(DEFAULT_TYPE);
        JSONObject selectObject = new JSONObject().put(FROM, fromArray).put(FIELDS, fieldsArray);
        JSONObject queryObject = new JSONObject().put(SELECT, selectObject);
        return queryObject.toString();
    }

    /**
     * Format json string
     * 
     * @param jsonStr
     * @return
     */
    public static String formatJson(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr))
            return ""; //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
            case '{':
            case '[':
                sb.append(current);
                sb.append('\n');
                indent++;
                addIndentBlank(sb, indent);
                break;
            case '}':
            case ']':
                sb.append('\n');
                indent--;
                addIndentBlank(sb, indent);
                sb.append(current);
                break;
            case ',':
                sb.append(current);
                if (last != '\\') {
                    sb.append('\n');
                    addIndentBlank(sb, indent);
                }
                break;
            default:
                sb.append(current);
            }
        }

        return sb.toString();
    }

    /**
     * Add space
     * 
     * @param sb
     * @param indent
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append("  "); //$NON-NLS-1$
        }
    }

}