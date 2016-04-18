/*
 * Copyright (C) 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.talend.components.container.gdf.examples;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.cloud.dataflow.sdk.coders.Coder;
import com.google.cloud.dataflow.sdk.coders.CoderException;
import com.google.cloud.dataflow.sdk.util.CloudObject;
import com.google.cloud.dataflow.sdk.util.common.ElementByteSizeObserver;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.talend.components.api.test.transformer.SimpleDoFnDefinition;
import org.talend.components.container.gdf.DoFnAdaptor;

import com.google.cloud.dataflow.sdk.Pipeline;
import com.google.cloud.dataflow.sdk.coders.StringUtf8Coder;
import com.google.cloud.dataflow.sdk.io.TextIO;
import com.google.cloud.dataflow.sdk.options.Description;
import com.google.cloud.dataflow.sdk.options.PipelineOptions;
import com.google.cloud.dataflow.sdk.options.PipelineOptionsFactory;
import com.google.cloud.dataflow.sdk.testing.DataflowAssert;
import com.google.cloud.dataflow.sdk.testing.RunnableOnService;
import com.google.cloud.dataflow.sdk.testing.TestPipeline;
import com.google.cloud.dataflow.sdk.transforms.Create;
import com.google.cloud.dataflow.sdk.transforms.DoFnTester;
import com.google.cloud.dataflow.sdk.transforms.ParDo;
import com.google.cloud.dataflow.sdk.values.PCollection;

/**
 * Tests of Simple transformation
 */
@RunWith(JUnit4.class)
public class SimpleTest {

    @Test
    @Category(RunnableOnService.class)
    public void testSimple() throws Exception {
        Pipeline p = TestPipeline.create();

        PCollection<String> input = p.apply(Create.of("input").withCoder(StringUtf8Coder.of()));

        PCollection<String> output = input
                .apply(ParDo.of(new DoFnAdaptor<String, String>(new SimpleDoFnDefinition().getRuntime())));

        DataflowAssert.that(output).containsInAnyOrder("input-transformed");
        p.run();
    }

    public static interface SimpleOptions extends PipelineOptions {

        @Description("Path of the file to read from")
        String getInputFile();

        void setInputFile(String value);

        @Description("Path of the file to write to")
        String getOutput();

        void setOutput(String value);
    }

    public static void main(String[] args) {
        SimpleOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(SimpleOptions.class);
        Pipeline p = Pipeline.create(options);

        p.apply(TextIO.Read.named("ReadLines").from(options.getInputFile()))
                .apply(ParDo.of(new DoFnAdaptor<String, String>(new SimpleDoFnDefinition().getRuntime())))
                .apply(TextIO.Write.named("WriteLines").to(options.getOutput()));

        p.run();
    }

}
