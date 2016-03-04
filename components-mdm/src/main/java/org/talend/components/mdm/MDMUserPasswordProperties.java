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

import static org.talend.daikon.properties.presentation.Widget.widget;

import org.talend.components.common.UserPasswordProperties;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget.WidgetType;

public class MDMUserPasswordProperties extends UserPasswordProperties {

    public MDMUserPasswordProperties(String name) {
        super(name);
        userId.setValue("userName"); //$NON-NLS-1$
    }

    @Override
    public void setupLayout() {
        Form form = Form.create(this, Form.MAIN, "User Password"); //$NON-NLS-1$
        form.addRow(userId);
        form.addColumn(widget(password).setWidgetType(WidgetType.HIDDEN_TEXT));
    }

}
