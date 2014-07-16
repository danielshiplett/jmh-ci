package jhm.ci.test;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openjdk.jmh.annotations.Mode.AverageTime;
import static org.openjdk.jmh.annotations.Scope.Thread;

import java.util.ArrayList;
import java.util.List;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.GenerateMicroBenchmark;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.logic.BlackHole;

@State(Thread)
@BenchmarkMode(AverageTime)
@OutputTimeUnit(NANOSECONDS)
@Warmup(iterations = 1, time = 2, timeUnit = SECONDS)
@Measurement(iterations = 2, time = 2, timeUnit = SECONDS)
@Fork(1)
@Threads(1)
public class SimpleBenchmark {

	@GenerateMicroBenchmark
	public void benchmark(BlackHole hole) {
		List<Integer> ints = new ArrayList<Integer>();

		for (int i = 0; i < 10; i++) {
			ints.add(i);
		}

		for (Integer i : ints) {
			hole.consume(i);
		}
	}
}
