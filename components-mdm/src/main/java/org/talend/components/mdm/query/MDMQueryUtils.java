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
package org.talend.components.mdm.query;

import static org.talend.components.mdm.query.MDMQueryConstants.ALIAS;
import static org.talend.components.mdm.query.MDMQueryConstants.COUNT;
import static org.talend.components.mdm.query.MDMQueryConstants.DISTINCT;
import static org.talend.components.mdm.query.MDMQueryConstants.FIELD;
import static org.talend.components.mdm.query.MDMQueryConstants.FIELDS;
import static org.talend.components.mdm.query.MDMQueryConstants.FROM;
import static org.talend.components.mdm.query.MDMQueryConstants.MAX;
import static org.talend.components.mdm.query.MDMQueryConstants.METADATA;
import static org.talend.components.mdm.query.MDMQueryConstants.MIN;
import static org.talend.components.mdm.query.MDMQueryConstants.SELECT;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MDMQueryUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(MDMQueryUtils.class);

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
            return StringUtils.substringAfterLast(xpath, "/");
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
    public static Collection<String> guessSchemaFromQuery(String query) throws JSONException {
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
    public static String guessQueryFromSchema(Collection<String> fields) throws JSONException {
        JSONArray fieldsArray = new JSONArray();
        for (String field : fields) {
            JSONObject fieldObject = new JSONObject();
            fieldObject.put(FIELD, "Type1/" + field);
            fieldsArray.put(fieldObject);
        }
        JSONArray fromArray = new JSONArray().put("Type1");
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
        if (null == jsonStr || "".equals(jsonStr))
            return "";
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
            sb.append("  ");
        }
    }

    /**
     * Call REST API to get xml results
     * 
     * @param url
     * @param username
     * @param password
     * @param containerName
     * @param containerType
     * @param queryText
     * @return
     */
    public static String executeQuery(String url, String username, String password, String containerName, String containerType,
            String queryText) {
        String results = null;
        HttpAuthenticationFeature authFeature = HttpAuthenticationFeature.basic(username, password);
        Client client = ClientBuilder.newClient().register(authFeature);
        Response response = client.target(url).path(containerName).path("query").queryParam("container", containerType).request()
                .accept(MediaType.APPLICATION_XML).put(Entity.json(queryText));
        if (response.getStatus() == 200) {
            results = response.readEntity(String.class);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Get result: \n" + results);
            }
        } else {
            LOGGER.error(response.toString());
            throw new RuntimeException(response.toString());
        }
        return results;
    }

}