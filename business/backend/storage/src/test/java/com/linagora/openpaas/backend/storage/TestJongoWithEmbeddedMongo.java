package com.linagora.openpaas.backend.storage;

import static org.fest.assertions.api.Assertions.assertThat;

import java.net.UnknownHostException;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.Mongo;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

public class TestJongoWithEmbeddedMongo {

    private MongodExecutable mongodExe;
    private MongodProcess mongod;
    private Mongo mongo;
    
    @Before
    public void setUp() throws Exception {
        MongodStarter runtime = MongodStarter.getDefaultInstance();
        mongodExe = runtime.prepare(new MongodConfig(Version.Main.V2_0, 12345, Network.localhostIsIPv6()));
        mongod = mongodExe.start();
        mongo = new Mongo("localhost", 12345);
    }

    @After
    public void tearDown() throws Exception {
        mongod.stop();
        mongodExe.cleanup();
    }

    @Test
	public void testCreateUser() throws UnknownHostException {
		Jongo jongo = new Jongo(mongo.getDB("matthieu"));
		MongoCollection users = jongo.getCollection("users");
		users.save(new User("matthieu"));
		Iterable<User> list = users.find().as(User.class);
		assertThat(list).containsOnly(new User("matthieu"));
	}
	
}
