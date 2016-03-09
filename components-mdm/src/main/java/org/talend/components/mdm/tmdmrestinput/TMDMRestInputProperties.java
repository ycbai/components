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

import static org.talend.components.mdm.MDMConstants.DEFAULT_DATA_SERVICE_ROOT;
import static org.talend.daikon.properties.PropertyFactory.newBoolean;
import static org.talend.daikon.properties.PropertyFactory.newEnum;
import static org.talend.daikon.properties.PropertyFactory.newInteger;
import static org.talend.daikon.properties.PropertyFactory.newProperty;
import static org.talend.daikon.properties.presentation.Widget.widget;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.talend.components.api.component.Connector.ConnectorType;
import org.talend.components.api.component.StudioConstants;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.common.SchemaProperties;
import org.talend.components.mdm.MDMCommonUtils;
import org.talend.components.mdm.MDMConnectionProperties;
import org.talend.components.mdm.MDMContainerProperties;
import org.talend.components.mdm.tmdmrestconnection.TMDMRestConnectionDefinition;
import org.talend.daikon.properties.PresentationItem;
import org.talend.daikon.properties.Property;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.presentation.Widget.WidgetType;
import org.talend.daikon.schema.Schema;
import org.talend.daikon.schema.SchemaElement;
import org.talend.daikon.schema.SchemaFactory;

/**
 * Define properties and layout for the tMDMRestInput component
 */
public class TMDMRestInputProperties extends ComponentProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(TMDMRestInputProperties.class);

    /**
     * Main Form
     */
    public Property useExistingConnection = newBoolean("useExistingConnection", false); //$NON-NLS-1$

    public Property referencedComponentId = newEnum("referencedComponentId"); //$NON-NLS-1$

    public MDMConnectionProperties connection = new MDMConnectionProperties("connection", DEFAULT_DATA_SERVICE_ROOT); //$NON-NLS-1$

    public MDMContainerProperties container = new MDMContainerProperties("container"); //$NON-NLS-1$

    public SchemaProperties schema = new SchemaProperties("schema"); //$NON-NLS-1$

    public PresentationItem guessQuery = new PresentationItem("guessQuery", "Guess Query"); //$NON-NLS-1$ //$NON-NLS-2$

    public PresentationItem guessSchema = new PresentationItem("guessSchema", "Guess Schema"); //$NON-NLS-1$ //$NON-NLS-2$

    public Property queryText = (Property) newProperty("queryText").setRequired(); //$NON-NLS-1$
    
    /**
     * Advanced Form
     */
    public Property batchSize = newInteger("batchSize", 50); //$NON-NLS-1$
    

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

        form.addRow(useExistingConnection);
        Widget compListWidget = widget(referencedComponentId).setWidgetType(WidgetType.COMPONENT_REFERENCE);
        compListWidget.setReferencedComponentName(TMDMRestConnectionDefinition.COMPONENT_NAME); //$NON-NLS-1$
        form.addRow(compListWidget);
        form.addRow(connection.getForm(Form.MAIN));

        form.addRow(container.getForm(Form.MAIN));
        form.addRow((widget(guessQuery).setWidgetType(WidgetType.BUTTON)));
        form.addColumn((widget(guessSchema).setWidgetType(WidgetType.BUTTON)));
        form.addRow(schema.getForm(Form.REFERENCE));
        form.addRow(queryText);
        
        Form advancedForm = new Form(this, Form.ADVANCED);
        advancedForm.addRow(batchSize);
    }

    public void afterUseExistingConnection() {
        refreshLayout(getForm(Form.MAIN));
    }

    public void afterGuessQuery() {
        Schema s = (Schema) schema.schema.getValue();
        List<SchemaElement> children = s.getRoot().getChildren();
        if (!children.isEmpty()) {
            Set<String> fields = new LinkedHashSet<String>();
            for (SchemaElement child : children) {
                fields.add(child.getName());
            }
            try {
                queryText.setValue(MDMCommonUtils.guessQueryFromFields(fields));
            } catch (JSONException e) {
                LOGGER.error("Guess query from schema failed.", e); //$NON-NLS-1$
            }
        }
    }

    public void afterGuessSchema() {
        try {
            Collection<String> fields = MDMCommonUtils.guessFieldsFromQuery(queryText.getStringValue());
            if (!fields.isEmpty()) {
                Schema s = SchemaFactory.newSchema();
                SchemaElement root = SchemaFactory.newSchemaElement("Root"); //$NON-NLS-1$
                s.setRoot(root);
                for (String field : fields) {
                    SchemaElement child = SchemaFactory.newSchemaElement(field);
                    root.addChild(child);
                }
            }
        } catch (JSONException e) {
            LOGGER.error("Guess schema from query failed.", e); //$NON-NLS-1$
        }
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
