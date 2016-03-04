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

import org.talend.components.api.component.runtime.BoundedReader;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.mdm.runtime.MDMSource;

public class TMDMRestInputSource extends MDMSource {

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
}
