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
package org.talend.components.api.test.transformer;

import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.avro.generic.IndexedRecord;
import org.talend.components.api.Constants;
import org.talend.components.api.component.AbstractComponentDefinition;
import org.talend.components.api.component.ComponentDefinition;
import org.talend.components.api.component.ComponentImageType;
import org.talend.components.api.component.Connector;
import org.talend.components.api.component.Connector.ConnectorType;
import org.talend.components.api.component.TransformerDoFnComponentDefinition;
import org.talend.components.api.component.Trigger;
import org.talend.components.api.component.Trigger.TriggerType;
import org.talend.components.api.component.runtime.AbstractDoFunTransformer;
import org.talend.components.api.component.runtime.TransformerDoFn;
import org.talend.components.api.container.ContainerTransformerProcessContext;
import org.talend.components.api.properties.ComponentProperties;

import aQute.bnd.annotation.component.Component;

@Component(name = Constants.COMPONENT_BEAN_PREFIX + SimpleDoFnDefinition.COMPONENT_NAME, provide = ComponentDefinition.class)
public class SimpleDoFnDefinition extends AbstractComponentDefinition implements TransformerDoFnComponentDefinition {

    public static final String COMPONENT_NAME = "SimpleDoFn"; //$NON-NLS-1$

    public SimpleDoFnDefinition() {
        super();
        setConnectors(new Connector(ConnectorType.FLOW, 1, 1));
        setTriggers(new Trigger(TriggerType.ITERATE, 1, 0), new Trigger(TriggerType.SUBJOB_OK, 1, 0),
                new Trigger(TriggerType.SUBJOB_ERROR, 1, 0));
    }

    @Override
    public Class<? extends ComponentProperties> getPropertyClass() {
        return null;
    }

    @Override
    public String getPngImagePath(ComponentImageType imageType) {
        return null;
    }

    @Override
    public String getMavenGroupId() {
        return "org.talend.components";
    }

    @Override
    public String getMavenArtifactId() {
        return "components-api";
    }

    @Override
    public String getName() {
        return COMPONENT_NAME;
    }

    @Override
    public TransformerDoFn getRuntime() {
        return new AbstractDoFunTransformer() {

            @Override
            public void processElement(ContainerTransformerProcessContext c) throws Exception {
                IndexedRecord input = c.element();

                GenericRecordBuilder builder = new GenericRecordBuilder(input.getSchema());
                IndexedRecord output = builder.build();
                if (input.get(0) instanceof String)
                    output.put(0, ((String)input.get(0)) + "-transformed");
                c.output(output);
            }
        };
    }
}
