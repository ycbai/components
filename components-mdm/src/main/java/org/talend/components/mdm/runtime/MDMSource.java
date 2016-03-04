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

import org.talend.components.api.component.runtime.BoundedReader;
import org.talend.components.api.component.runtime.BoundedSource;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.schema.Schema;

public class MDMSource extends MDMSourceOrSink implements BoundedSource {

    private static final long serialVersionUID = 2338033452442072180L;

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

    @Override
    public BoundedReader createReader(RuntimeContainer adaptor) {
        // TODO Auto-generated method stub
        return null;
    }

}
