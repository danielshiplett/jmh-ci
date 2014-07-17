package jmh.ci.plugin;

import java.io.FileInputStream;
import java.io.IOException;

import jmh.ci.plugin.types.PrimaryMetric;
import jmh.ci.plugin.types.Result;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUnitTest {
	private static final Logger log = LoggerFactory.getLogger("TestLog");

	@Test
	public void test() throws JsonParseException, IOException {
		Json j = new Json();

		FileInputStream is = new FileInputStream(
				"src/test/resources/sample.json");
		JsonParser jp = j.getJf().createJsonParser(is);

		Result[] results = j.getOm().readValue(jp, Result[].class);

		log.trace("results.size: {}", results.length);

		for (Result r : results) {
			log.trace("r.benchmark: {}", r.getBenchmark());
			PrimaryMetric pm = r.getPrimaryMetric();
			log.trace("r.primary.score: {} {}", pm.getScore(),
					pm.getScoreUnit());
		}
	}

}
