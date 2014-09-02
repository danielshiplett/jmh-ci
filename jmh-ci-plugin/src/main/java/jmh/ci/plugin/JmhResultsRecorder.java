package jmh.ci.plugin;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import jmh.ci.plugin.types.PrimaryMetric;
import jmh.ci.plugin.types.Result;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.kohsuke.stapler.DataBoundConstructor;

import com.google.common.collect.Lists;

public class JmhResultsRecorder extends Recorder {

	private PrintStream logger;

	public final String results;

	@DataBoundConstructor
	public JmhResultsRecorder(String results) {
		super();

		this.results = results.trim();

		System.out.println("results: " + this.results);
	}

	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher,
			BuildListener listener) throws InterruptedException, IOException {

		logger = listener.getLogger();
		logger.println("Recording JMH results: " + results);

		List<FilePath> files = getFileList(build);

		for (FilePath fp : files) {
			readResultsFromJsonFile(fp);
		}

		return true;
	}

	private void readResultsFromJsonFile(FilePath fp)
			throws JsonParseException, JsonMappingException, IOException {
		Json j = new Json();

		FileInputStream is = new FileInputStream(fp.getRemote());
		JsonParser jp = j.getJf().createJsonParser(is);

		Result[] results = j.getOm().readValue(jp, Result[].class);

		logger.println(String.format("results.size: %d", results.length));

		for (Result r : results) {
			logger.println(String.format("r.benchmark: %s", r.getBenchmark()));
			PrimaryMetric pm = r.getPrimaryMetric();
			logger.println(String.format("r.primary.score: %f %s",
					pm.getScore(), pm.getScoreUnit()));
		}
	}

	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.NONE;
	}

	@Override
	public BuildStepDescriptor<Publisher> getDescriptor() {
		return (DescriptorImpl) super.getDescriptor();
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

	List<FilePath> getFileList(AbstractBuild<?, ?> build) throws IOException,
			InterruptedException {
		List<FilePath> readFiles = Lists.newArrayList();

		for (FilePath f : build.getWorkspace().list(results)) {
			logger.println("filePath: " + f);
			readFiles.add(f);
		}

		return readFiles;
	}
}
