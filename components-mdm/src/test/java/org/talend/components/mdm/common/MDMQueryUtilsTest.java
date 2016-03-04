package org.talend.components.mdm.common;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import org.talend.components.mdm.query.MDMQueryUtils;

public class MDMQueryUtilsTest {

    @Test
    public void testGuessQueryFromSchema() throws Exception {
        List<String> fields = Arrays.asList("Field_1", "Field_2", "Field_3");
        String query = MDMQueryUtils.guessQueryFromSchema(fields);
        JSONObject queryJson = new JSONObject(query);
        String excepted = "{\"select\":{\"from\":[\"Type1\"],\"fields\":[{\"field\":\"Type1\\/Field_1\"},{\"field\":\"Type1\\/Field_2\"},{\"field\":\"Type1\\/Field_3\"}]}}";
        JSONObject exceptedJson = new JSONObject(excepted);
        assertEquals(queryJson, exceptedJson);
    }

    @Test
    public void testGuessSchemaFromQuery() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("   \"select\": {");
        sb.append("      \"from\": [\"Type1\"],");
        sb.append("      \"fields\":[");
        sb.append("         {\"alias\": [ {\"name\": \"v1\"}, {\"distinct\" : {\"field\":\"Type1/Field_1\"}} ] },");
        sb.append("         {\"alias\": [ {\"name\": \"v2\"}, {\"min\" : {\"field\":\"Type1/Field_2\"}} ] },");
        sb.append("         {\"alias\": [ {\"name\": \"v3\"}, {\"max\" : {\"field\":\"Type1/Field_3\"}} ] },");
        sb.append("         {\"field\":\"Type1/Field_4\"},");
        sb.append("         {\"metadata\":\"Field_5\"}");
        sb.append("      ]");
        sb.append("    }");
        sb.append("}");

        Collection<String> fields = MDMQueryUtils.guessSchemaFromQuery(sb.toString());
        assertEquals(fields.size(), 5);
        String[] exptectd = { "Field_1", "Field_2", "Field_3", "Field_4", "Field_5" };
        assertArrayEquals(exptectd, fields.toArray(new String[5]));

        sb = new StringBuilder();
        sb.append("{");
        sb.append("   \"select\": {");
        sb.append("      \"from\": [\"Type1\"],");
        sb.append("      \"fields\":[");
        sb.append("         {\"alias\": [ {\"name\": \"v1\"}, {\"count\" : {}} ] }");
        sb.append("      ]");
        sb.append("    }");
        sb.append("}");
        fields = MDMQueryUtils.guessSchemaFromQuery(sb.toString());
        assertEquals(fields.size(), 0);

        sb = new StringBuilder();
        sb.append("{");
        sb.append("   \"select\": {");
        sb.append("      \"from\": [\"Type1\"],");
        sb.append("      \"fields\":[");
        sb.append("         {\"count\" : {}}");
        sb.append("      ]");
        sb.append("    }");
        sb.append("}");
        fields = MDMQueryUtils.guessSchemaFromQuery(sb.toString());
        assertEquals(fields.size(), 0);
    }
}
