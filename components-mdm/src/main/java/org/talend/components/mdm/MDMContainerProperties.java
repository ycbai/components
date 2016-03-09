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

import static org.talend.components.mdm.MDMConstants.DEFAULT_TYPE;
import static org.talend.components.mdm.MDMConstants.MASTER;
import static org.talend.components.mdm.MDMConstants.STAGING;
import static org.talend.components.mdm.MDMConstants.SYSTEM;
import static org.talend.daikon.properties.PropertyFactory.newEnum;
import static org.talend.daikon.properties.PropertyFactory.newProperty;

import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.properties.Property;
import org.talend.daikon.properties.presentation.Form;

public class MDMContainerProperties extends ComponentProperties {

    public Property containerName = (Property) newProperty("containerName").setRequired(); //$NON-NLS-1$

    public Property containerType = (Property) newEnum("containerType", MASTER, STAGING, SYSTEM).setRequired(); //$NON-NLS-1$

    public MDMContainerProperties(String name) {
        super(name);
        containerName.setDefaultValue(DEFAULT_TYPE);
        containerType.setDefaultValue(MASTER);
    }

    @Override
    public void setupLayout() {
        super.setupLayout();
        Form form = Form.create(this, Form.MAIN, "MDM Container"); //$NON-NLS-1$
        form.addRow(containerName);
        form.addColumn(containerType);
    }

}
