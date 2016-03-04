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

import static org.junit.Assert.fail;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.talend.components.api.exception.error.ComponentsErrorCode;
import org.talend.components.api.service.ComponentService;
import org.talend.components.api.service.internal.ComponentServiceImpl;
import org.talend.components.api.test.ComponentTestUtils;
import org.talend.components.api.test.SimpleComponentRegistry;
import org.talend.daikon.exception.TalendRuntimeException;
import org.talend.daikon.schema.SchemaElement;
import org.talend.daikon.schema.SchemaElement.Type;
import org.talend.daikon.schema.SchemaFactory;

public class TMDMRestInputTest {
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
     * @Test public void testTMDMRestInputRuntime() throws Exception { TMDMRestInputDefinition def =
     * (TMDMRestInputDefinition) getComponentService().getComponentDefinition("TMDMRestInput"); TMDMRestInputProperties
     * props = (TMDMRestInputProperties) getComponentService().getComponentProperties("TMDMRestInput");
     * 
     * }
     * 
     * @Test public void testTMDMRestInputRuntimeException() { TMDMRestInputDefinition def = (TMDMRestInputDefinition)
     * getComponentService().getComponentDefinition("TMDMRestInput"); TMDMRestInputProperties props =
     * (TMDMRestInputProperties) getComponentService().getComponentProperties("TMDMRestInput"); // check empty schema
     * exception props.schema.schema.setChildren(Collections.EMPTY_LIST); TMDMRestInputSource source; source =
     * (TMDMRestInputSource) def.getRuntime(); source.initialize(null, props); TMDMRestInputReader reader; reader =
     * (TMDMRestInputReader) source.createReader(null); try { reader.start(); fail("Should have thrown an exception"); }
     * catch (Exception e) { if (!(e instanceof TalendRuntimeException && ((TalendRuntimeException) e).getCode() ==
     * ComponentsErrorCode.SCHEMA_MISSING)) { StringWriter stack = new StringWriter(); e.printStackTrace(new
     * PrintWriter(stack)); fail("wrong exception caught :" + stack.toString()); } } // check wrong schema exception
     * props.schema.schema.setChildren(new ArrayList<SchemaElement>());
     * props.schema.schema.addChild(SchemaFactory.newSchemaElement(Type.INT, "line"));
     * 
     * source = (TMDMRestInputSource) def.getRuntime(); source.initialize(null, props); reader = (TMDMRestInputReader)
     * source.createReader(null);
     * 
     * try { reader.start(); fail("Should have thrown an exception"); } catch (Exception e) { if (!(e instanceof
     * TalendRuntimeException && ((TalendRuntimeException) e).getCode() == ComponentsErrorCode.SCHEMA_TYPE_MISMATCH)) {
     * StringWriter stack = new StringWriter(); e.printStackTrace(new PrintWriter(stack)); fail(
     * "wrong exception caught :" + stack.toString()); } }
     * 
     * }
     * 
     * 
     * @Test public void testAlli18n() { ComponentTestUtils.checkAllI18N(new TMDMRestInputProperties(null).init(),
     * errorCollector); }
     * 
     * @Test public void testAllImagePath() { ComponentTestUtils.testAllImages(getComponentService()); }
     * 
     * @Test public void testAllRuntimes() { ComponentTestUtils.testAllRuntimeAvaialble(getComponentService()); }
     */

}