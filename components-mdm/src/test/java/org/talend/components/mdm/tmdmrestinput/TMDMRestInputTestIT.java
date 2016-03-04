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

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.talend.components.api.service.ComponentService;
import org.talend.components.api.service.internal.ComponentServiceImpl;
import org.talend.components.api.test.SimpleComponentRegistry;
import org.talend.components.mdm.tmdmrestinput.TMDMRestInputDefinition;

public class TMDMRestInputTestIT {
    /*
     * 
     * @Rule public ErrorCollector errorCollector = new ErrorCollector();
     * 
     * private ComponentServiceImpl componentService;
     * 
     * @Before public void initializeComponentRegistryAnsService() { // reset the component service componentService =
     * null; }
     * 
     * // default implementation for pure java test. Shall be overriden of Spring or OSGI tests public ComponentService
     * getComponentService() { if (componentService == null) { SimpleComponentRegistry testComponentRegistry = new
     * SimpleComponentRegistry(); testComponentRegistry.addComponent(TMDMRestInputDefinition.COMPONENT_NAME, new
     * TMDMRestInputDefinition()); componentService = new ComponentServiceImpl(testComponentRegistry); } return
     * componentService; }
     * 
     * @Test // this is an integration test to check that the pom is porperly copied into the built artifact. public
     * void testDependencies() { TMDMRestInputDefinition def = (TMDMRestInputDefinition)
     * getComponentService().getComponentDefinition("tMDMRestInput"); assertNotNull(def.getMavenPom()); }
     */
}