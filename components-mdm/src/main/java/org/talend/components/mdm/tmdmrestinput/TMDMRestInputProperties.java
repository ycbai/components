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

import static org.talend.components.mdm.MDMConstants.DATA_SERVICE_ROOT;
import static org.talend.daikon.properties.PropertyFactory.newBoolean;
import static org.talend.daikon.properties.PropertyFactory.newEnum;
import static org.talend.daikon.properties.presentation.Widget.widget;

import org.talend.components.api.component.Connector.ConnectorType;
import org.talend.components.api.component.StudioConstants;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.common.SchemaProperties;
import org.talend.components.mdm.MDMConnectionProperties;
import org.talend.components.mdm.MDMContainerProperties;
import org.talend.components.mdm.MDMQueryProperties;
import org.talend.daikon.properties.Property;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.presentation.Widget.WidgetType;

/**
 * Define properties and layout for the tMDMRestInput component
 */
public class TMDMRestInputProperties extends ComponentProperties {

    public SchemaProperties schema = new SchemaProperties("schema"); //$NON-NLS-1$

    public Property useExistingConnection = newBoolean("useExistingConnection", false); //$NON-NLS-1$

    public Property referencedComponentId = newEnum("referencedComponentId"); //$NON-NLS-1$

    public MDMConnectionProperties connection = new MDMConnectionProperties("connection", DATA_SERVICE_ROOT); //$NON-NLS-1$

    public MDMContainerProperties container = new MDMContainerProperties("container"); //$NON-NLS-1$

    public MDMQueryProperties query = new MDMQueryProperties("query"); //$NON-NLS-1$

    public TMDMRestInputProperties(String name) {
        super(name);
    }

    @Override
    public void setupProperties() {
        super.setupProperties();
        schema.schema.setTaggedValue(StudioConstants.CONNECTOR_TYPE_SCHEMA_KEY, ConnectorType.FLOW);
    }

    @Override
    public void setupLayout() {
        super.setupLayout();
        Form form = Form.create(this, Form.MAIN, "MDM Rest Input"); //$NON-NLS-1$
        form.addRow(schema.getForm(Form.REFERENCE));

        form.addRow(useExistingConnection);
        Widget compListWidget = widget(referencedComponentId).setWidgetType(WidgetType.COMPONENT_REFERENCE);
        compListWidget.setReferencedComponentName("tMDMConnection"); //$NON-NLS-1$
        form.addRow(compListWidget);
        form.addRow(connection.getForm(Form.MAIN));

        form.addRow(container.getForm(Form.MAIN));
        form.addRow(query.getForm(Form.MAIN));
    }

    public void afterUseExistingConnection() {
        refreshLayout(getForm(Form.MAIN));
    }

    @Override
    public void refreshLayout(Form form) {
        super.refreshLayout(form);
        if (Form.MAIN.equals(form.getName())) {
            if (useExistingConnection.getBooleanValue()) {
                form.getWidget(referencedComponentId.getName()).setVisible(true);
                form.getWidget(connection.getName()).setVisible(false);
            } else {
                form.getWidget(referencedComponentId.getName()).setVisible(false);
                form.getWidget(connection.getName()).setVisible(true);
            }
        }
    }

}
