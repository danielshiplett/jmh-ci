package c3.hpi;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;

import org.kohsuke.stapler.DataBoundConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmhResultsRecorder extends Recorder {
	private static final Logger log = LoggerFactory
			.getLogger(JmhResultsRecorder.class);

	public final String results;
	public final String postUrl;
	public final String apiKey;
	public final String proxyHostPort;

	@DataBoundConstructor
	public JmhResultsRecorder(String results, String postUrl, String apiKey,
			String proxyHostPort) {
		super();

		this.results = results.trim();
		this.postUrl = postUrl.trim();
		this.apiKey = apiKey.trim();
		this.proxyHostPort = proxyHostPort.trim();

		log.debug("results: {}", this.results);
		log.debug("postUrl: {}", this.postUrl);
		log.debug("apiKey: {}", this.apiKey);
		log.debug("proxyHostPort: {}", this.proxyHostPort);
	}

	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.NONE;
	}

	@Extension
	public static final class DescriptorImpl extends
			BuildStepDescriptor<Publisher> {

		public boolean isApplicable(Class<? extends AbstractProject> aClass) {
			return true;
		}

		public String getDisplayName() {
			return "Publish JMH Benchmark Results";
		}

	}
}
