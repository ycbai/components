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

import org.talend.components.api.component.runtime.Sink;
import org.talend.components.api.component.runtime.WriteOperation;

public class MDMSink extends MDMSourceOrSink implements Sink {

    private static final long serialVersionUID = 3873489192548822978L;

    @Override
    public WriteOperation<?> createWriteOperation() {
        // TODO Auto-generated method stub
        return null;
    }

}
