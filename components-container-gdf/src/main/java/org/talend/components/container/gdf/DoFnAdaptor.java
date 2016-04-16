package org.talend.components.container.gdf;

import com.google.cloud.dataflow.sdk.transforms.DoFn;
import com.google.cloud.dataflow.sdk.values.PCollection;
import com.google.cloud.dataflow.sdk.values.PInput;
import com.google.cloud.dataflow.sdk.values.POutput;
import org.talend.components.api.component.runtime.TransformerDoFn;

import java.io.Serializable;

public class DoFnAdaptor<TInput, TOutput> extends DoFn<TInput, TOutput> implements Serializable {

    protected TransformerDoFn transformer;


    public DoFnAdaptor(TransformerDoFn transformer) {
        this.transformer = transformer;
    }

    @Override
    public void startBundle(Context c) throws Exception {
        //transformer.startBundle();
    }

    @Override
    public void processElement(ProcessContext processContext) throws Exception {
    }

    @Override
    public void finishBundle(Context c) throws Exception {
    }

}
