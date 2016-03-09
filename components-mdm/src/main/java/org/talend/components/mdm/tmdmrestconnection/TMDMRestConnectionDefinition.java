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
package org.talend.components.mdm.tmdmrestconnection;

import org.talend.components.api.Constants;
import org.talend.components.api.component.ComponentDefinition;
import org.talend.components.api.component.Connector;
import org.talend.components.api.component.Connector.ConnectorType;
import org.talend.components.api.component.Trigger;
import org.talend.components.api.component.Trigger.TriggerType;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.mdm.MDMConnectionProperties;
import org.talend.components.mdm.MDMDefinition;
import org.talend.components.mdm.MDMUserPasswordProperties;

import aQute.bnd.annotation.component.Component;

@Component(name = Constants.COMPONENT_BEAN_PREFIX
        + TMDMRestConnectionDefinition.COMPONENT_NAME, provide = ComponentDefinition.class)
public class TMDMRestConnectionDefinition extends MDMDefinition {

    public static final String COMPONENT_NAME = "tMDMRestConnection"; //$NON-NLS-1$

    public TMDMRestConnectionDefinition() {
        super(COMPONENT_NAME);
        setConnectors(new Connector(ConnectorType.MAIN, 0, 1), new Connector(ConnectorType.REJECT, 0, 1));
        setTriggers(new Trigger(TriggerType.ITERATE, 1, 1), new Trigger(TriggerType.SUBJOB_OK, 1, 0),
                new Trigger(TriggerType.SUBJOB_ERROR, 1, 0), new Trigger(TriggerType.RUN_IF, 1, 0),
                new Trigger(TriggerType.COMPONENT_OK, 1, 0), new Trigger(TriggerType.COMPONENT_ERROR, 1, 0));
    }

    @Override
    public Class<MDMConnectionProperties> getPropertyClass() {
        return MDMConnectionProperties.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends ComponentProperties>[] getNestedCompatibleComponentPropertiesClass() {
        return new Class[] { MDMUserPasswordProperties.class };
    }

}