package org.talend.components.api.container;

import org.apache.avro.generic.IndexedRecord;

import java.time.Instant;

/**
 * Information accessible to all methods in this {@code DoFn}. Used primarily to output elements.
 */
public interface ContainerTransformerContext {

    /**
     * Adds the given element to the main output {@code PCollection}.
     *
     * <p>
     * Once passed to {@code output} the element should be considered immutable and not be modified in any way. It may
     * be cached or retained by the Dataflow runtime or later steps in the pipeline, or used in other unspecified ways.
     *
     * <p>
     * If invoked from {@link DoFn#processElement processElement}, the output element will have the same timestamp and
     * be in the same windows as the input element passed to {@link DoFn#processElement processElement}.
     *
     * <p>
     * If invoked from {@link #startBundle startBundle} or {@link #finishBundle finishBundle}, this will attempt to use
     * the {@link com.google.cloud.dataflow.sdk.transforms.windowing.WindowFn} of the input {@code PCollection} to
     * determine what windows the element should be in, throwing an exception if the {@code WindowFn} attempts to access
     * any information about the input element. The output element will have a timestamp of negative infinity.
     */
    public abstract void output(IndexedRecord output);

    /**
     * Adds the given element to the main output {@code PCollection}, with the given timestamp.
     *
     * <p>
     * Once passed to {@code outputWithTimestamp} the element should not be modified in any way.
     *
     * <p>
     * If invoked from {@link DoFn#processElement processElement}, the timestamp must not be older than the input
     * element's timestamp minus {@link DoFn#getAllowedTimestampSkew getAllowedTimestampSkew}. The output element will
     * be in the same windows as the input element.
     *
     * <p>
     * If invoked from {@link #startBundle startBundle} or {@link #finishBundle finishBundle}, this will attempt to use
     * the {@link com.google.cloud.dataflow.sdk.transforms.windowing.WindowFn} of the input {@code PCollection} to
     * determine what windows the element should be in, throwing an exception if the {@code WindowFn} attempts to access
     * any information about the input element except for the timestamp.
     */
    public abstract void outputWithTimestamp(IndexedRecord output, Instant timestamp);

    /**
     * Adds the given element to the side output {@code PCollection} with the given tag.
     *
     * <p>
     * Once passed to {@code sideOutput} the element should not be modified in any way.
     *
     * <p>
     * The caller of {@code ParDo} uses {@link ParDo#withIndexedRecordags withIndexedRecordags} to specify the tags of side outputs
     * that it consumes. Non-consumed side outputs, e.g., outputs for monitoring purposes only, don't necessarily need
     * to be specified.
     *
     * <p>
     * The output element will have the same timestamp and be in the same windows as the input element passed to
     * {@link DoFn#processElement processElement}.
     *
     * <p>
     * If invoked from {@link #startBundle startBundle} or {@link #finishBundle finishBundle}, this will attempt to use
     * the {@link com.google.cloud.dataflow.sdk.transforms.windowing.WindowFn} of the input {@code PCollection} to
     * determine what windows the element should be in, throwing an exception if the {@code WindowFn} attempts to access
     * any information about the input element. The output element will have a timestamp of negative infinity.
     *
     * @see ParDo#withIndexedRecordags
     */
    public abstract void sideOutput(TupleTag tag, IndexedRecord output);

    /**
     * Adds the given element to the specified side output {@code PCollection}, with the given timestamp.
     *
     * <p>
     * Once passed to {@code sideOutputWithTimestamp} the element should not be modified in any way.
     *
     * <p>
     * If invoked from {@link DoFn#processElement processElement}, the timestamp must not be older than the input
     * element's timestamp minus {@link DoFn#getAllowedTimestampSkew getAllowedTimestampSkew}. The output element will
     * be in the same windows as the input element.
     *
     * <p>
     * If invoked from {@link #startBundle startBundle} or {@link #finishBundle finishBundle}, this will attempt to use
     * the {@link com.google.cloud.dataflow.sdk.transforms.windowing.WindowFn} of the input {@code PCollection} to
     * determine what windows the element should be in, throwing an exception if the {@code WindowFn} attempts to access
     * any information about the input element except for the timestamp.
     *
     * @see ParDo#withIndexedRecordags
     */
    public abstract void sideOutputWithTimestamp(TupleTag tag, IndexedRecord output, Instant timestamp);
}
