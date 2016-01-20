// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.components.spark.mongodb.tmongodbinput;

import java.util.Map;

import org.apache.avro.generic.IndexedRecord;
import org.apache.hadoop.conf.Configuration;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaRDDLike;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.bson.BSONObject;
import org.talend.components.mongodb.BSONObjectIndexedRecordWrapper;

import scala.Tuple2;

import com.cloudera.dataflow.spark.BroadcastHelper;
import com.cloudera.dataflow.spark.DoFnFunction;
import com.cloudera.dataflow.spark.EvaluationContext;
import com.cloudera.dataflow.spark.TransformEvaluator;
import com.cloudera.dataflow.spark.WindowingHelpers;
import com.google.api.client.util.Maps;
import com.google.cloud.dataflow.sdk.transforms.ParDo;
import com.google.cloud.dataflow.sdk.util.WindowedValue;
import com.google.cloud.dataflow.sdk.values.TupleTag;
import com.mongodb.hadoop.MongoInputFormat;

public class MongoDBInputTransformEvaluator<I, O> extends TransformEvaluator<ParDo.Bound<I, O>> {

    // TODO: these must come from component properties. to delete.

    public static String HOST = "localhost";

    public static int PORT = 27017;

    public static String DB_NAME = "test";

    public static String DB_COLLECTION = "inputCollection";

    // TODO Find the option on the configuraiton to use the query
    public static String QUERY = "{}";

    @Override
    public void evaluate(ParDo.Bound<I, O> transform, EvaluationContext context) {

        // String pattern = transform.getFilepattern();
        JavaSparkContext jsc = context.getSparkContext();
        @SuppressWarnings("unchecked")
        Configuration mongodbConfig = new Configuration();
        mongodbConfig.set("mongo.job.input.format", "com.mongodb.hadoop.MongoInputFormat");
        mongodbConfig.set("mongo.input.uri", "mongodb://" + HOST + ":" + PORT + "/" + DB_NAME + "." + DB_COLLECTION);
        JavaPairRDD<Object, BSONObject> documents = jsc.newAPIHadoopRDD(mongodbConfig, // Configuration
                MongoInputFormat.class, // InputFormat: read from a live cluster.
                Object.class, // Key class
                BSONObject.class // Value class
                );
        JavaRDD<WindowedValue<IndexedRecord>> rdd = documents.map(new Function<Tuple2<Object, BSONObject>, IndexedRecord>() {

            @Override
            public IndexedRecord call(Tuple2<Object, BSONObject> input) throws Exception {
                return new BSONObjectIndexedRecordWrapper(input._2);
            }
        }).map(WindowingHelpers.<IndexedRecord> windowFunction());
        context.setOutputRDD(transform, rdd);
    }

    public void old(ParDo.Bound<I, O> transform, EvaluationContext context) {
        Map<TupleTag<?>, BroadcastHelper<?>> emptySideInputs = Maps.newHashMap();
        DoFnFunction<I, O> dofn = new DoFnFunction<I, O>(transform.getFn(), context.getRuntimeContext(), emptySideInputs);
        @SuppressWarnings("unchecked")
        JavaRDDLike<WindowedValue<I>, ?> inRDD = (JavaRDDLike<WindowedValue<I>, ?>) context.getInputRDD(transform);
        context.setOutputRDD(transform, inRDD.mapPartitions(dofn));

    }
}
