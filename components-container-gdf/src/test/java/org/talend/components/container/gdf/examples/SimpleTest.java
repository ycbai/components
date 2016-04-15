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

import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.cloud.dataflow.sdk.Pipeline;
import com.google.cloud.dataflow.sdk.coders.StringUtf8Coder;
import com.google.cloud.dataflow.sdk.io.TextIO;
import com.google.cloud.dataflow.sdk.options.DataflowPipelineOptions;
import com.google.cloud.dataflow.sdk.options.Default;
import com.google.cloud.dataflow.sdk.options.DefaultValueFactory;
import com.google.cloud.dataflow.sdk.options.Description;
import com.google.cloud.dataflow.sdk.options.PipelineOptions;
import com.google.cloud.dataflow.sdk.options.PipelineOptionsFactory;
import com.google.cloud.dataflow.sdk.testing.DataflowAssert;
import com.google.cloud.dataflow.sdk.testing.RunnableOnService;
import com.google.cloud.dataflow.sdk.testing.TestPipeline;
import com.google.cloud.dataflow.sdk.transforms.Create;
import com.google.cloud.dataflow.sdk.transforms.DoFn;
import com.google.cloud.dataflow.sdk.transforms.DoFnTester;
import com.google.cloud.dataflow.sdk.transforms.MapElements;
import com.google.cloud.dataflow.sdk.transforms.ParDo;
import com.google.cloud.dataflow.sdk.util.gcsfs.GcsPath;
import com.google.cloud.dataflow.sdk.values.PCollection;
import com.google.cloud.dataflow.sdk.values.POutput;
import org.talend.components.api.test.transformer.SimpleDoFnDefinition;
import org.talend.components.container.gdf.DoFnAdaptor;

/**
 * Tests of WordCount.
 */
@RunWith(JUnit4.class)
public class SimpleTest {

    /** Example test that tests a specific DoFn. */
    @Test
    public void testExtractWordsFn() {
        DoFnTester<String, String> extractWordsFn = DoFnTester.of(new ExtractWordsFn());

        Assert.assertThat(extractWordsFn.processBatch(" some  input  words "), CoreMatchers.hasItems("some", "input", "words"));
        Assert.assertThat(extractWordsFn.processBatch(" "), CoreMatchers.<String> hasItems());
        Assert.assertThat(extractWordsFn.processBatch(" some ", " input", " words"),
                CoreMatchers.hasItems("some", "input", "words"));
    }

    static final String[] WORDS_ARRAY = new String[] { "hi there", "hi", "hi sue bob", "hi sue", "", "bob hi" };

    static final List<String> WORDS = Arrays.asList(WORDS_ARRAY);

    static final String[] COUNTS_ARRAY = new String[] { "hi: 5", "there: 1", "sue: 2", "bob: 2" };

    /** Example test that tests a PTransform by using an in-memory input and inspecting the output. */
    @Test
    @Category(RunnableOnService.class)
    public void testCountWords() throws Exception {
        Pipeline p = TestPipeline.create();

        PCollection<String> input = p.apply(Create.of(WORDS).withCoder(StringUtf8Coder.of()));

        PCollection<String> output = input.apply(new WordCount.CountWords())
                .apply(MapElements.via(new WordCount.FormatAsTextFn()));

        DataflowAssert.that(output).containsInAnyOrder(COUNTS_ARRAY);
        p.run();
    }

    public static interface WordCountOptions extends PipelineOptions {

        @Description("Path of the file to read from")
        @Default.String("gs://dataflow-samples/shakespeare/kinglear.txt")
        String getInputFile();

        void setInputFile(String value);

        @Description("Path of the file to write to")
        @Default.InstanceFactory(OutputFactory.class)
        String getOutput();

        void setOutput(String value);

        /**
         * Returns "gs://${YOUR_STAGING_DIRECTORY}/counts.txt" as the default destination.
         */
        public static class OutputFactory implements DefaultValueFactory<String> {

            @Override
            public String create(PipelineOptions options) {
                DataflowPipelineOptions dataflowOptions = options.as(DataflowPipelineOptions.class);
                if (dataflowOptions.getStagingLocation() != null) {
                    return GcsPath.fromUri(dataflowOptions.getStagingLocation()).resolve("counts.txt").toString();
                } else {
                    throw new IllegalArgumentException("Must specify --output or --stagingLocation");
                }
            }
        }
    }

    public static class Xform1 extends DoFn<String, PCollection<POutput>> {

        @Override
        public void processElement(ProcessContext processContext) throws Exception {

        }
    }

    public static class Xform2 extends DoFn<String, PCollection<POutput>> {

        @Override
        public void processElement(ProcessContext processContext) throws Exception {

        }
    }

    public static class Xform3 extends DoFn<String, PCollection<POutput>> {

        @Override
        public void processElement(ProcessContext processContext) throws Exception {

        }
    }

    public static void main(String[] args) {
        WordCountOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(WordCountOptions.class);
        Pipeline p = Pipeline.create(options);

        // FIXME - trying to figure out this apply stuff.
        p.apply(TextIO.Read.named("ReadLines").from(options.getInputFile()))
                .apply(ParDo.of(new Xform1()))
                .apply(ParDo.of(new Xform1()))
                .apply(ParDo.of(new Xform1()))
                .apply(ParDo.of(new Xform1()))
                .apply(ParDo.of(new Xform2()))

                .apply(ParDo.of(new DoFnAdaptor(new SimpleDoFnDefinition().getRuntime())))
                .apply(TextIO.Write.named("WriteLines").to(options.getOutput()));

        p.run();
    }

}
