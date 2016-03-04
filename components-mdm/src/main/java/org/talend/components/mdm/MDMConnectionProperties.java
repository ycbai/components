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

import static org.talend.daikon.properties.PropertyFactory.newProperty;

import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.properties.Property;
import org.talend.daikon.properties.presentation.Form;

public class MDMConnectionProperties extends ComponentProperties {

    public Property url = (Property) newProperty("url").setRequired(); //$NON-NLS-1$

    public MDMUserPasswordProperties userPassword = new MDMUserPasswordProperties("userPassword"); //$NON-NLS-1$

    public MDMConnectionProperties(String name, String defaultUrl) {
        super(name);
        url.setValue(defaultUrl);
    }

    @Override
    public void setupLayout() {
        super.setupLayout();
        Form form = Form.create(this, Form.MAIN, "MDM Connection"); //$NON-NLS-1$
        form.addRow(url);
        form.addRow(userPassword.getForm(Form.MAIN));
    }

}
