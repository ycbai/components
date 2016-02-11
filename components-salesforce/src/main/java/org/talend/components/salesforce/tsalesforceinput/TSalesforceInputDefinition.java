// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.components.salesforce.tsalesforceinput;

import org.talend.components.api.Constants;
import org.talend.components.api.component.Connector;
import org.talend.components.api.component.ComponentDefinition;
import org.talend.components.api.component.InputComponentDefinition;
import org.talend.components.api.runtime.input.Source;
import org.talend.components.api.component.Trigger;
import org.talend.components.api.component.Trigger.TriggerType;
import org.talend.components.salesforce.SalesforceDefinition;

import aQute.bnd.annotation.component.Component;
import org.talend.components.salesforce.type.SalesforceTalendTypesRegistry;
import org.talend.daikon.schema.type.TypesRegistry;

/**
 * Component that can connect to a salesforce system and get some data out of it.
 */

@Component(name = Constants.COMPONENT_BEAN_PREFIX + TSalesforceInputDefinition.COMPONENT_NAME, provide = ComponentDefinition.class)
public class TSalesforceInputDefinition extends SalesforceDefinition implements InputComponentDefinition {

    public static final String COMPONENT_NAME = "tSalesforceInputNew"; //$NON-NLS-1$

    public TSalesforceInputDefinition() {
        super(COMPONENT_NAME);

        setConnectors(new Connector(Connector.ConnectorType.FLOW, 0, 1));
        setTriggers(new Trigger(TriggerType.ITERATE, 1, 1), new Trigger(TriggerType.SUBJOB_OK, 1, 0), new Trigger(
                TriggerType.SUBJOB_ERROR, 1, 0));
    }

    @Override
    public boolean isStartable() {
        return true;
    }

    @Override
    public Class<?> getPropertyClass() {
        return TSalesforceInputProperties.class;
    }

    @Override
    public Source getInputRuntime() {
        return new SalesforceSource();
    }

    @Override
    public TypesRegistry getTypesRegistry() {
        return new SalesforceTalendTypesRegistry();
    }
}
