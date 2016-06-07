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
package org.talend.components.jira;

import static org.talend.components.jira.Resource.ISSUE;

import org.talend.components.api.component.Connector;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.common.FixedConnectorsComponentProperties;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;

/**
 * Common Jira components {@link Properties}
 */
public abstract class JiraProperties extends FixedConnectorsComponentProperties {

    /**
     * Return properties names
     */
    public static final String ERROR_MESSAGE_NAME = "ERROR_MESSAGE";
    
    public static final String NB_LINE_NAME = "NB_LINE";
    
    public static final String NB_SUCCESS_NAME = "NB_SUCCESS";

    public static final String NB_REJECT_NAME = "NB_REJECT";
    
    /**
     * Property path connector
     */
    protected transient PropertyPathConnector MAIN_CONNECTOR = new PropertyPathConnector(Connector.MAIN_NAME, "schema");

    /**
     * {@link JiraConnectionProperties}, which describe connection to Jira server
     */
    public JiraConnectionProperties connection = new JiraConnectionProperties("connection");

    /**
     * Jira resource. This may be issue, project etc.
     */
    public Property<Resource> resource = PropertyFactory.newEnum("resource", Resource.class);

    /**
     * Schema property to define required fields of Jira resource
     */
    public SchemaProperties schema = new SchemaProperties("schema");

    /**
     * Constructor sets {@link Properties} name
     * 
     * @param name {@link Properties} name
     */
    public JiraProperties(String name) {
        super(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupProperties() {
        super.setupProperties();
        resource.setValue(ISSUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupLayout() {
        super.setupLayout();
        Form mainForm = new Form(this, Form.MAIN);
        mainForm.addRow(connection.getForm(Form.MAIN));
        mainForm.addRow(resource);
        mainForm.addRow(schema.getForm(Form.REFERENCE));
    }

    /**
     * Refreshes form layout after resource is changed
     */
    public void afterResource() {
        refreshLayout(getForm(Form.MAIN));
        refreshLayout(getForm(Form.ADVANCED));
    }

}
