package org.talend.components.api.container;

import java.time.Instant;

import org.talend.components.api.component.runtime.transformer.BoundedWindow;
import org.talend.components.api.component.runtime.transformer.PaneInfo;

/**
 * Information accessible when running {@link DoFn#processElement}.
 */
public interface ContainerTransformerProcessContext {

    /**
     * Returns the input element to be processed.
     *
     * <p>
     * The element should be considered immutable. The Dataflow runtime will not mutate the element, so it is safe to
     * cache, etc. The element should not be mutated by any of the {@link DoFn} methods, because it may be cached
     * elsewhere, retained by the Dataflow runtime, or used in other unspecified ways.
     */
    public abstract Object element();

    /**
     * Returns the value of the side input for the window corresponding to the window of the main input element.
     *
     * <p>
     * See {@link com.google.cloud.dataflow.sdk.transforms.windowing.WindowFn#getSideInputWindow} for how this
     * corresponding window is determined.
     *
     * @throws IllegalArgumentException if this is not a side input
     * @see ParDo#withSideInputs
     */
    public abstract <T> T sideInput(PCollectionView<T> view);

    /**
     * Returns the timestamp of the input element.
     *
     * <p>
     * See {@link com.google.cloud.dataflow.sdk.transforms.windowing.Window} for more information.
     */
    public abstract Instant timestamp();

    /**
     * Returns the window into which the input element has been assigned.
     *
     * <p>
     * See {@link com.google.cloud.dataflow.sdk.transforms.windowing.Window} for more information.
     *
     * @throws UnsupportedOperationException if this {@link DoFn} does not implement {@link RequiresWindowAccess}.
     */
    public abstract BoundedWindow window();

    /**
     * Returns information about the pane within this window into which the input element has been assigned.
     *
     * <p>
     * Generally all data is in a single, uninteresting pane unless custom triggering and/or late data has been
     * explicitly requested. See {@link com.google.cloud.dataflow.sdk.transforms.windowing.Window} for more information.
     */
    public abstract PaneInfo pane();

}
