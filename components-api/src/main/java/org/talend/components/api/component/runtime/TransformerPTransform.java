package org.talend.components.api.component.runtime;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.talend.components.api.container.PInput;
import org.talend.components.api.container.POutput;

/**
 */
public interface TransformerPTransform  {

    /**
     * Applies this {@code PTransform} on the given {@code InputT}, and returns its {@code Output}.
     *
     * <p>
     * Composite transforms, which are defined in terms of other transforms, should return the output of one of the
     * composed transforms. Non-composite transforms, which do not apply any transforms internally, should return a new
     * unbound output and register evaluators (via backend-specific registration methods).
     *
     * <p>
     * The default implementation throws an exception. A derived class must either implement apply, or else each runner
     * must supply a custom implementation via {@link com.google.cloud.dataflow.sdk.runners.PipelineRunner#apply}.
     */
    public POutput apply(PInput object);


    /**
     * Called before invoking apply (which may be intercepted by the runner) to verify this transform is fully specified
     * and applicable to the specified input.
     *
     * <p>
     * By default, does nothing.
     */
    public void validate(PInput input);


}
