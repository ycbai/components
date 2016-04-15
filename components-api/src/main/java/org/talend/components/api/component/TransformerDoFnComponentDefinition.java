package org.talend.components.api.component;

import org.talend.components.api.component.runtime.Source;
import org.talend.components.api.component.runtime.SourceOrSink;
import org.talend.components.api.component.runtime.TransformerDoFn;

/**
 * Definition of a transformer component that uses {@link TransformerDoFn}
 */
public interface TransformerDoFnComponentDefinition {

    /**
     * Returns the runtime {@link TransformerDoFn} for transformer components.
     */
    public TransformerDoFn getRuntime();

}
