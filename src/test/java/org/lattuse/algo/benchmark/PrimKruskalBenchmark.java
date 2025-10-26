package org.lattuse.algo.benchmark;

import org.lattuse.algo.base.Graph;
import org.lattuse.algo.model.MSTResult;
import org.lattuse.algo.algorithms.PrimAlgorithm;
import org.lattuse.algo.algorithms.KruskalAlgorithm;
import org.lattuse.algo.handler.IOHandler;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5)
@Measurement(iterations = 10)
@Fork(1)
@State(Scope.Benchmark)
public class PrimKruskalBenchmark {

    private List<Graph> graphs;
    private int graphIndex;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        graphs = IOHandler.loadGraphs("datasets/graphs_dataset.json");
        graphIndex = 0;
    }

    private Graph getNextGraph() {
        Graph g = graphs.get(graphIndex % graphs.size());
        graphIndex++;
        return g;
    }

    @Benchmark
    public MSTResult primBenchmark() {
        Graph graph = getNextGraph();
        return PrimAlgorithm.run(graph);
    }

    @Benchmark
    public MSTResult kruskalBenchmark() {
        Graph graph = getNextGraph();
        return KruskalAlgorithm.run(graph);
    }
}

