package jmh.ci.plugin.types;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PrimaryMetric {

	private double score;
	private double scoreError;
	private double[] scoreConfidence;

	// private ScorePercentiles scorePercentiles;

	private String scoreUnit;

	// private double[] rawData;

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getScoreError() {
		return scoreError;
	}

	public void setScoreError(double scoreError) {
		this.scoreError = scoreError;
	}

	public double[] getScoreConfidence() {
		return scoreConfidence;
	}

	public void setScoreConfidence(double[] scoreConfidence) {
		this.scoreConfidence = scoreConfidence;
	}

	public String getScoreUnit() {
		return scoreUnit;
	}

	public void setScoreUnit(String scoreUnit) {
		this.scoreUnit = scoreUnit;
	}

	// public double[] getRawData() {
	// return rawData;
	// }
	//
	// public void setRawData(double[] rawData) {
	// this.rawData = rawData;
	// }
}
