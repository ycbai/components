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
package org.talend.components.api.properties.fullexample;

import org.talend.components.api.Constants;
import org.talend.components.api.component.AbstractComponentDefinition;
import org.talend.components.api.component.ComponentDefinition;
import org.talend.components.api.component.ComponentImageType;
import org.talend.components.api.component.Trigger;
import org.talend.components.api.component.Trigger.TriggerType;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.api.service.testcomponent.TestComponentDefinition;

import aQute.bnd.annotation.component.Component;

@Component(name = Constants.COMPONENT_BEAN_PREFIX + TestComponentDefinition.COMPONENT_NAME, provide = ComponentDefinition.class)
public class FullExampleComponentDefinition extends AbstractComponentDefinition implements ComponentDefinition {

    public static final String COMPONENT_NAME = "FullExample"; //$NON-NLS-1$

    public FullExampleComponentDefinition() {
        super(COMPONENT_NAME);
        setTriggers(new Trigger(TriggerType.ITERATE, 1, 0), new Trigger(TriggerType.SUBJOB_OK, 1, 0),
                new Trigger(TriggerType.SUBJOB_ERROR, 1, 0));
    }

    protected ComponentPropertiesFullExample properties;

    @Override
    public String[] getFamilies() {
        return new String[] { "myFamily" };
    }

    @Override
    public String getPngImagePath(ComponentImageType imageType) {
        return "fullExampleIcon_32x32.png";
    }

    @Override
    public Class<? extends ComponentProperties> getPropertyClass() {
        return ComponentPropertiesFullExample.class;
    }

    @Override
    public String getMavenGroupId() {
        return "org.talend.components.api.test";
    }

    @Override
    public String getMavenArtifactId() {
        return "test-components";
    }

}
