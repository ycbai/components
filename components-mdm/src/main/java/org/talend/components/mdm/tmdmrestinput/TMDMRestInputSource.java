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
package org.talend.components.mdm.tmdmrestinput;

import static org.talend.components.mdm.MDMConstants.STRIP_CHARS;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.talend.components.api.component.runtime.BoundedReader;
import org.talend.components.api.component.runtime.BoundedSource;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.mdm.MDMConstants;
import org.talend.daikon.NamedThing;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.schema.Schema;

public class TMDMRestInputSource implements BoundedSource {

    private static final long serialVersionUID = -735231508709552541L;

    private TMDMRestInputProperties properties;

    @Override
    public void initialize(RuntimeContainer adaptor, ComponentProperties properties) {
        this.properties = (TMDMRestInputProperties) properties;
    }

    @Override
    public BoundedReader createReader(RuntimeContainer adaptor) {
        return new TMDMRestInputReader(adaptor, this, properties);
    }

    @Override
    public ValidationResult validate(RuntimeContainer adaptor) {
        String url = StringUtils.strip(properties.connection.url.getStringValue(), STRIP_CHARS);
        String mdmRoot = StringUtils.substringBefore(url, MDMConstants.DATA_SERVICE_BASE);
        Response response = ClientBuilder.newClient().target(mdmRoot).request().get();
        if (response.getStatus() == 200) {
            return ValidationResult.OK;
        } else {
            return new ValidationResult().setMessage(response.toString()).setStatus(ValidationResult.Result.ERROR);
        }
    }

    @Override
    public List<NamedThing> getSchemaNames(RuntimeContainer adaptor) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Schema getSchema(RuntimeContainer adaptor, String schemaName) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<? extends BoundedSource> splitIntoBundles(long desiredBundleSizeBytes, RuntimeContainer adaptor)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getEstimatedSizeBytes(RuntimeContainer adaptor) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean producesSortedKeys(RuntimeContainer adaptor) {
        // TODO Auto-generated method stub
        return false;
    }
}
