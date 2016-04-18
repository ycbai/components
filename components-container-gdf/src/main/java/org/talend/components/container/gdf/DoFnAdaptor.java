package org.talend.components.container.gdf;

import java.io.Serializable;
import java.time.Instant;

import org.talend.components.api.component.runtime.TransformerDoFn;
import org.talend.components.api.container.ContainerTransformerContext;
import org.talend.components.api.container.TupleTag;

import com.google.cloud.dataflow.sdk.transforms.DoFn;
import com.google.cloud.dataflow.sdk.values.TypeDescriptor;

public class DoFnAdaptor<TInput, TOutput> extends DoFn<TInput, TOutput> implements Serializable {

    protected TransformerDoFn transformer;

    protected class ContainerTransformerContextAdaptor implements ContainerTransformerContext {

        protected Context context;

        ContainerTransformerContextAdaptor(Context c) {
            context = c;
        }

        @Override
        public <T> void output(T output) {
            context.output((TOutput) output);
        }

        @Override
        public <T> void outputWithTimestamp(T output, Instant timestamp) {
            // FIXME
            context.outputWithTimestamp((TOutput) output, null);
        }

        @Override
        public <T> void sideOutput(TupleTag tag, T output) {
            // FIXME
        }

        @Override
        public <T> void sideOutputWithTimestamp(TupleTag tag, T output, Instant timestamp) {
            // FIXME
        }
    };

    public DoFnAdaptor(TransformerDoFn transformer) {
        this.transformer = transformer;
    }

    @Override
    public void startBundle(Context c) throws Exception {
        ContainerTransformerContextAdaptor adaptor = new ContainerTransformerContextAdaptor(c);
        transformer.startBundle(adaptor);
    }

    @Override
    public void processElement(ProcessContext processContext) throws Exception {
        transformer.processElement();
    }

    @Override
    public void finishBundle(Context c) throws Exception {
        ContainerTransformerContextAdaptor adaptor = new ContainerTransformerContextAdaptor(c);
        transformer.finishBundle(adaptor);
    }

    @Override
    protected TypeDescriptor getInputTypeDescriptor() {
        return TypeDescriptor.of(String.class);
    }

    @Override
    protected TypeDescriptor getOutputTypeDescriptor() {
        return TypeDescriptor.of(String.class);
    }

}
