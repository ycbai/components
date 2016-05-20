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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.api.service.ComponentService;
import org.talend.components.api.test.AbstractComponentTest;
import org.talend.components.api.test.SimpleComponentRegistry;
import org.talend.components.api.test.SimpleComponentService;
import org.talend.daikon.properties.presentation.Form;

public class ComponentPropertiesFullExampleTest extends AbstractComponentTest {

    ComponentPropertiesFullExample cp;

    private SimpleComponentService simpleComponentService;

    @Before
    public void init() {
        cp = (ComponentPropertiesFullExample) getComponentService()
                .getComponentProperties(FullExampleComponentDefinition.COMPONENT_NAME);

    }

    @Test
    public void testPropertyCallBacks() throws Throwable {
        assertEquals("initialValue", cp.stringProp.getValue());
        cp.stringProp.setValue("1");
        ComponentProperties resultCP = getComponentService().beforePropertyActivate(cp.stringProp.getName(), cp);
        assertEquals("1", resultCP.getValidationResult().getMessage());
        cp.stringProp.setValue("2");
        resultCP = getComponentService().beforePropertyPresent(cp.stringProp.getName(), cp);
        assertEquals("2", resultCP.getValidationResult().getMessage());
        cp.stringProp.setValue("3");
        resultCP = getComponentService().afterProperty(cp.stringProp.getName(), cp);
        assertEquals("3", resultCP.getValidationResult().getMessage());
        cp.stringProp.setValue("4");
        resultCP = getComponentService().validateProperty(cp.stringProp.getName(), cp);
        assertEquals("4", resultCP.getValidationResult().getMessage());
    }

    @Test
    public void testHiddingAField() throws Throwable {
        assertFalse(cp.getForm(Form.MAIN).getWidget(cp.stringProp.getName()).isHidden());
        cp.hideStringPropProp.setValue(true);
        ComponentProperties resultCP = getComponentService().afterProperty(cp.hideStringPropProp.getName(), cp);
        assertTrue(resultCP.getForm(Form.MAIN).getWidget(cp.stringProp.getName()).isHidden());
    }

    @Override
    public ComponentService getComponentService() {
        if (simpleComponentService == null) {
            SimpleComponentRegistry componentRegistry = new SimpleComponentRegistry();
            componentRegistry.addComponent(FullExampleComponentDefinition.COMPONENT_NAME, new FullExampleComponentDefinition());
            simpleComponentService = new SimpleComponentService(componentRegistry);
        }
        return simpleComponentService;
    }
}
