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
package org.talend.components.mdm.runtime;

import java.io.IOException;
import java.util.List;

import org.talend.components.api.component.runtime.SourceOrSink;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.schema.Schema;

public class MDMSourceOrSink implements SourceOrSink {

    private static final long serialVersionUID = -981018739360953480L;

    @Override
    public void initialize(RuntimeContainer adaptor, ComponentProperties properties) {
        // TODO Auto-generated method stub

    }

    @Override
    public ValidationResult validate(RuntimeContainer adaptor) {
        // TODO Auto-generated method stub
        return null;
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

}
