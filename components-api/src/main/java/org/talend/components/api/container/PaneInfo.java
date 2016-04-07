/*
 * Copyright (C) 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.talend.components.api.container;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import org.talend.components.api.component.runtime.transformer.DoFn;

import com.google.cloud.dataflow.sdk.coders.AtomicCoder;
import com.google.cloud.dataflow.sdk.coders.Coder;
import com.google.cloud.dataflow.sdk.coders.CoderException;
import com.google.cloud.dataflow.sdk.transforms.DoFn;
import com.google.cloud.dataflow.sdk.transforms.GroupByKey;
import com.google.cloud.dataflow.sdk.util.VarInt;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

/**
 * Provides information about the pane an element belongs to. Every pane is implicitly associated
 * with a window. Panes are observable only via the
 * {@link com.google.cloud.dataflow.sdk.transforms.DoFn.ProcessContext#pane} method of the context
 * passed to a {@link DoFn#processElement} overridden method.
 *
 * <p>Note: This does not uniquely identify a pane, and should not be used for comparisons.
 */
public interface PaneInfo {
  /**
   * Enumerates the possibilities for the timing of this pane firing related to the
   * input and output watermarks for its computation.
   *
   * <p>A window may fire multiple panes, and the timing of those panes generally follows the
   * regular expression {@code EARLY* ON_TIME? LATE*}. Generally a pane is considered:
   * <ol>
   * <li>{@code EARLY} if the system cannot be sure it has seen all data which may contribute
   * to the pane's window.
   * <li>{@code ON_TIME} if the system predicts it has seen all the data which may contribute
   * to the pane's window.
   * <li>{@code LATE} if the system has encountered new data after predicting no more could arrive.
   * It is possible an {@code ON_TIME} pane has already been emitted, in which case any
   * following panes are considered {@code LATE}.
   * </ol>
   *
   * <p>Only an
   * {@link AfterWatermark#pastEndOfWindow} trigger may produce an {@code ON_TIME} pane.
   * With merging {@link WindowFn}'s, windows may be merged to produce new windows that satisfy
   * their own instance of the above regular expression. The only guarantee is that once a window
   * produces a final pane, it will not be merged into any new windows.
   *
   * <p>The predictions above are made using the mechanism of watermarks.
   * See {@link com.google.cloud.dataflow.sdk.util.TimerInternals} for more information
   * about watermarks.
   *
   * <p>We can state some properties of {@code LATE} and {@code ON_TIME} panes, but first need some
   * definitions:
   * <ol>
   * <li>We'll call a pipeline 'simple' if it does not use
   * {@link com.google.cloud.dataflow.sdk.transforms.DoFn.Context#outputWithTimestamp} in
   * any {@code DoFn}, and it uses the same
   * {@link com.google.cloud.dataflow.sdk.transforms.windowing.Window.Bound#withAllowedLateness}
   * argument value on all windows (or uses the default of {@link org.joda.time.Duration#ZERO}).
   * <li>We'll call an element 'locally late', from the point of view of a computation on a
   * worker, if the element's timestamp is before the input watermark for that computation
   * on that worker. The element is otherwise 'locally on-time'.
   * <li>We'll say 'the pane's timestamp' to mean the timestamp of the element produced to
   * represent the pane's contents.
   * </ol>
   *
   * <p>Then in simple pipelines:
   * <ol>
   * <li> (Soundness) An {@code ON_TIME} pane can never cause a later computation to generate a
   * {@code LATE} pane. (If it did, it would imply a later computation's input watermark progressed
   * ahead of an earlier stage's output watermark, which by design is not possible.)
   * <li> (Liveness) An {@code ON_TIME} pane is emitted as soon as possible after the input
   * watermark passes the end of the pane's window.
   * <li> (Consistency) A pane with only locally on-time elements will always be {@code ON_TIME}.
   * And a {@code LATE} pane cannot contain locally on-time elements.
   * </ol>
   *
   * However, note that:
   * <ol>
   * <li> An {@code ON_TIME} pane may contain locally late elements. It may even contain only
   * locally late elements. Provided a locally late element finds its way into an {@code ON_TIME}
   * pane its lateness becomes unobservable.
   * <li> A {@code LATE} pane does not necessarily cause any following computation panes to be
   * marked as {@code LATE}.
   * </ol>
   */
  public enum Timing {
    /**
     * Pane was fired before the input watermark had progressed after the end of the window.
     */
    EARLY,
    /**
     * Pane was fired by a {@link AfterWatermark#pastEndOfWindow} trigger because the input
     * watermark progressed after the end of the window. However the output watermark has not
     * yet progressed after the end of the window. Thus it is still possible to assign a timestamp
     * to the element representing this pane which cannot be considered locally late by any
     * following computation.
     */
    ON_TIME,
    /**
     * Pane was fired after the output watermark had progressed past the end of the window.
     */
    LATE,
    /**
     * This element was not produced in a triggered pane and its relation to input and
     * output watermarks is unknown.
     */
    UNKNOWN;

    // NOTE: Do not add fields or re-order them. The ordinal is used as part of
    // the encoding.
  }

  /**
   * Return true if there is no timing information for the current {@link PaneInfo}.
   * This typically indicates that the current element has not been assigned to
   * windows or passed through an operation that executes triggers yet.
   */
  public boolean isUnknown();

  /**
   * Return true if this is the first pane produced for the associated window.
   */
  public boolean isFirst();

  /**
   * Return true if this is the last pane that will be produced in the associated window.
   */
  public boolean isLast();

  /**
   * Return true if this is the last pane that will be produced in the associated window.
   */
  public Timing getTiming()

  /**
   * The zero-based index of this trigger firing that produced this pane.
   *
   * <p>This will return 0 for the first time the timer fires, 1 for the next time, etc.
   *
   * <p>A given (key, window, pane-index) is guaranteed to be unique in the
   * output of a group-by-key operation.
   */
  public long getIndex();


  /**
   * The zero-based index of this trigger firing among non-speculative panes.
   *
   * <p> This will return 0 for the first non-{@link Timing#EARLY} timer firing, 1 for the next one,
   * etc.
   *
   * <p>Always -1 for speculative data.
   */
  public long getNonSpeculativeIndex();

}
