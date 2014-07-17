package jmh.ci.plugin;

import hudson.model.Action;
import hudson.model.AbstractBuild;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmhBuildAction implements Action {
	private static final Logger log = LoggerFactory
			.getLogger(JmhBuildAction.class);

	private final String[] jsonResults;
	public final AbstractBuild<?, ?> build;

	public JmhBuildAction(String[] jsonResults, AbstractBuild<?, ?> build) {
		this.jsonResults = jsonResults;
		this.build = build;

		for (String s : jsonResults) {
			log.debug("results: {}", s);
		}
	}

	/**
	 * For JmhBuildAction/summary.jelly
	 */
	public String getSummary() {

		return String
				.format("Collected  timing result, memory result, found regression");
	}

	public String getIconFileName() {
		return "/plugin/jmh-ci/jmh.png";
	}

	public String getDisplayName() {
		return "JMH Benchmarks";
	}

	public String getUrlName() {
		return "jmh";
	}

}
