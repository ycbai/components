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

import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.mdm.runtime.MDMReader;

public class TMDMRestInputReader extends MDMReader {

    protected TMDMRestInputSource source;

    protected TMDMRestInputProperties properties;

    protected RuntimeContainer container;

    public TMDMRestInputReader(RuntimeContainer container, TMDMRestInputSource source, TMDMRestInputProperties properties) {
        this.container = container;
        this.source = source;
        this.properties = properties;
    }
}
