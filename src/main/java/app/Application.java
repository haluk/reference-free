package app;

import fasta.ReadItem;
import fasta.Util;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AbstractEdge;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.layout.Eades84Layout;
import org.graphstream.ui.view.Viewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.util.List;

/**
 * Created by hd on 10/6/15.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    private final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        if (args.length != 1)
            usage();
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }

    private static void usage() {
        System.err.println("Usage: java -jar read_dist.jar test.fasta");
        System.exit(1);
    }

    @Override
    public void run(String... strings) throws Exception {
        List<ReadItem> readItems = Util.readToHash(new File(strings[0]), 10);
        Graph graph = new MultiGraph("Read Distances");

        for (ReadItem i : readItems) {
            graph.addNode(i.getName());
        }
        int[][] distanceMatrix = Util.calcDistanceMatrix(readItems);
        for (int i = 0; i < readItems.size(); i++) {
            for (int j = 0; j < readItems.size(); j++) {
                if (i != j) {
                    Node src = graph.getNode(readItems.get(i).getName());
                    Node dest = graph.getNode(readItems.get(j).getName());
                    String label = src.getId() + "->" + dest.getId();
                    graph.addEdge(label, src, dest);
                    graph.getEdge(label).addAttribute("ui.label", distanceMatrix[i][j]);

                }
            }
        }

        // Visualization
        for (Node node : graph)
            node.addAttribute("ui.label", node.getId());

        graph.addAttribute("ui.stylesheet", "node { size: 30; text-size:20; text-alignment: at-right;}");
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");

        LOGGER.info("{}", graph.getNodeCount());

        Viewer viewer = graph.display();
        viewer.enableAutoLayout(new Eades84Layout());

    }
}
