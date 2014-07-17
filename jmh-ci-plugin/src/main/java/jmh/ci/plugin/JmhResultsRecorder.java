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

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

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
		logger.println("Recording Caliper results: " + results);

		List<FilePath> files = getFileList(build);

		return true;
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
