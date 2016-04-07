package org.talend.components.api.component.runtime;

import org.talend.components.api.container.ContainerTransformerContext;
import org.talend.components.api.container.ContainerTransformerProcessContext;

public interface TransformerDoFn {

    public void startBundle(ContainerTransformerContext c) throws Exception;

    public void processElement(ContainerTransformerProcessContext processContext) throws Exception;

    public void finishBundle(ContainerTransformerContext c) throws Exception;

}
