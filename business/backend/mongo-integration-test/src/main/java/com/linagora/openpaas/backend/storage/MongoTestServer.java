package com.linagora.openpaas.backend.storage;

import java.io.IOException;
import java.net.UnknownHostException;

import com.mongodb.Mongo;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;

public class MongoTestServer {

	private MongodExecutable mongodExe;
	private MongodProcess mongod;
	private MongodConfig mongodConfig;

	public MongoTestServer() throws UnknownHostException, IOException {
		MongodStarter runtime = MongodStarter.getDefaultInstance();
        mongodConfig = new MongodConfig(Version.Main.V2_0);
		mongodExe = runtime.prepare(mongodConfig);
	}

	public void start() throws IOException {
		mongod = mongodExe.start();
	}
	
	public void stop() {
		mongod.stop();
        mongodExe.stop();
	}
	
	public Mongo client() throws UnknownHostException {
		return new Mongo(mongodConfig.getBindIp(),  mongodConfig.getPort());
	}
}
