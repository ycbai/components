package org.talend.components.api.component.runtime;

import org.talend.components.api.container.ContainerTransformerContext;
import org.talend.components.api.container.ContainerTransformerProcessContext;

/**
 * Abstract implementation of {@link TransformerDoFn}.
 */
public abstract class AbstractDoFunTransformer implements TransformerDoFn {

    @Override
    public void startBundle(ContainerTransformerContext c) throws Exception {
    }

    @Override
    public abstract void processElement(ContainerTransformerProcessContext processContext) throws Exception;

    @Override
    public void finishBundle(ContainerTransformerContext c) throws Exception {
    }
}
