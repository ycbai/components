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

import static org.talend.components.mdm.query.MDMQueryConstants.DEFAULT_QUERY;
import static org.talend.daikon.properties.PropertyFactory.newEnum;
import static org.talend.daikon.properties.PropertyFactory.newProperty;
import static org.talend.daikon.properties.presentation.Widget.widget;

import org.talend.components.api.properties.ComponentProperties;

import org.talend.daikon.properties.PresentationItem;
import org.talend.daikon.properties.Property;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget.WidgetType;

public class MDMQueryProperties extends ComponentProperties {

    public Property queryType = (Property) newEnum("queryType", "Build-In", "Repository"); //$NON-NLS-1$

    public PresentationItem guessQuery = new PresentationItem("guessQuery", "Guess Query"); //$NON-NLS-1$ //$NON-NLS-2$

    public PresentationItem guessSchema = new PresentationItem("guessSchema", "Guess Schema"); //$NON-NLS-1$ //$NON-NLS-2$

    public Property queryText = (Property) newProperty("queryText").setRequired(); //$NON-NLS-1$

    public MDMQueryProperties(String name) {
        super(name);
        queryText.setValue(DEFAULT_QUERY);
    }

    @Override
    public void setupLayout() {
        super.setupLayout();
        Form form = Form.create(this, Form.MAIN, "MDM Query"); //$NON-NLS-1$
        form.addRow((widget(guessQuery).setWidgetType(WidgetType.BUTTON)));
        form.addColumn((widget(guessSchema).setWidgetType(WidgetType.BUTTON)));
        form.addRow(queryType);
        form.addRow(queryText);
    }

}
