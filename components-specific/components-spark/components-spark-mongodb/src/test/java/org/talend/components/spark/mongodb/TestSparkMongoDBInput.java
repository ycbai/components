package org.talend.components.spark.mongodb;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.UUID;

import org.apache.avro.generic.IndexedRecord;
import org.apache.hadoop.mapred.IndexRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.talend.components.engine.gdf.SimpleInputGDF;
import org.talend.components.engine.gdf.SimpleOutputGDF;
import org.talend.components.mongodb.tmongodbinput.MongoDBInputRuntime;
import org.talend.components.mongodb.tmongodboutput.MongoDBOutputRuntime;
import org.talend.components.spark.mongodb.tmongodbinput.MongoDBInputTransformEvaluator;

import com.cloudera.dataflow.spark.SparkPipelineOptions;
import com.cloudera.dataflow.spark.SparkPipelineOptionsFactory;
import com.cloudera.dataflow.spark.SparkPipelineRunner;
import com.google.cloud.dataflow.sdk.Pipeline;
import com.google.cloud.dataflow.sdk.values.PCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory;

public class TestSparkMongoDBInput {

    private MongodForTestsFactory factory = null;

    private DB db;

    private int port;

    private String dbName;

    /** Takes about a quarter second to setup and run a simple embedded MongoDB unit test. */
    @Before
    public void setup() throws IOException {
        factory = MongodForTestsFactory.with(Version.Main.V3_2);
        MongoClient mongo = factory.newMongo();
        port = mongo.getAddress().getPort();
        dbName = "test-" + UUID.randomUUID();
        db = mongo.getDB(dbName);
    }

    @After
    public void teardown() {
        if (factory != null) {
            factory.shutdown();
        }

    }

    /**
     * A basic test for MongoDBInputRuntime running in a DirectPipelineRunner.
     */
    @Test
    public void testMongoDBInputRuntime() throws Exception {
        // Create the collection and insert an object
        DBCollection col = db.createCollection("inputCollection", new BasicDBObject());
        col.insert(new BasicDBObject("id", "12345").append("test", //
                new BasicDBObject("hierarchical", //
                        new BasicDBObject().append("name", "toto").append("value", 3))));

        MongoDBInputTransformEvaluator.HOST = "localhost";
        MongoDBInputTransformEvaluator.PORT = port;
        MongoDBInputTransformEvaluator.DB_NAME = dbName;

        MongoDBOutputRuntime.HOST = "localhost";
        MongoDBOutputRuntime.PORT = port;
        MongoDBOutputRuntime.DB_NAME = dbName;

        // set invalid value for MongoDBInputRuntime, it should not be used
        MongoDBInputRuntime.HOST = "INVALID";
        MongoDBInputRuntime.PORT = 2222;
        MongoDBInputRuntime.DB_NAME = "INVALID";

        // Join the two components.
        SimpleInputGDF<? extends IndexedRecord> input = new SimpleInputGDF<>(new MongoDBInputRuntime());

        SimpleOutputGDF output = new SimpleOutputGDF(new MongoDBOutputRuntime());

        SparkPipelineOptions options = SparkPipelineOptionsFactory.create();
        Pipeline p = Pipeline.create(options);

        PCollection<? extends IndexedRecord> inputResult = input.generatePipeline(p);
        output.generatePipeline(inputResult);

        // Override MongoDBInputRuntime by MongoDBInputTransformEvaluator
        SparkPipelineRunner.registerSpecificTransformEvaluator(MongoDBInputRuntime.class,
                new MongoDBInputTransformEvaluator<IndexRecord, IndexRecord>());
        SparkPipelineRunner.create(options).run(p);

        // Go directly to the embedded mongodb to check the output.
        DBCollection actual = db.getCollection(MongoDBOutputRuntime.DB_COLLECTION);
        DBCursor cursor = actual.find();
        DBObject dbo = cursor.next();
        assertTrue(dbo.containsField("_id"));
        assertEquals("{ \"outputcolumn\" : \"12345\"}", dbo.get("simplepath").toString());
        assertTrue(!cursor.hasNext());
    }
}