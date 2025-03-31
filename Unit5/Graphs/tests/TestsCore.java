package Unit5.Graphs.tests;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.lang.reflect.Field;

import Unit5.Graphs.main.Graph;
import Unit5.Graphs.main.Node;

public class TestsCore {
    
    private Scanner getScanner(String graphFile) throws FileNotFoundException {
        URL url = this.getClass().getResource(graphFile);
        String filePath = graphFile;
        try {
            File file = new File(url.getFile());
            filePath = file.getAbsolutePath();
            filePath = java.net.URLDecoder.decode(filePath, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
        }
        return new Scanner(new File(filePath));
    }
    
    public <T extends Comparable<T>> T parseT(String s, Class<T> realType) {
        if (realType == Integer.class) {
            return realType.cast(Integer.parseInt(s));
        } else if (realType == String.class) {
            return realType.cast(s);
        } else if (realType == Double.class) {
            return realType.cast(Double.parseDouble(s));
        } else if (realType == Character.class) {
            if (s.length() != 1) {
                throw new RuntimeException("Invalid format in graph parsing!");
            }
            return realType.cast(s.charAt(0));
        } else {
            throw new RuntimeException("Unsupported type in graph parsing!");
        }
    }

    public <T extends Comparable<T>> Graph<T> readGraph(String graphFile, Class<T> realType) throws FileNotFoundException {
        Scanner input = getScanner(graphFile);
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        while(input.hasNextLine()) {
            String line = input.nextLine();
            String[] tokens = line.split("\\s>\\s");
            if (tokens.length < 1) {
                input.close();
                throw new RuntimeException("Syntax error in parsing graph!");
            }
            String nodeName = tokens[0].split(":")[0].trim();
            String[] nodeNeighbors = tokens.length > 1 ? tokens[1].trim().split("\\s") : null;
            map.put(
                    nodeName,
                    nodeNeighbors != null ? Arrays.asList(nodeNeighbors) : null);
        }
        input.close();
        
        Graph<T> graph = new Graph<T>();
        for(String node : map.keySet()) {
            T n = parseT(node, realType);
            graph.addNode(n);
        }
        
        for(Map.Entry<String, List<String>> kvp : map.entrySet()) {
            T fromNode = parseT(kvp.getKey(), realType);
            List<String> fromNodeNeighbors = kvp.getValue();
            if (fromNodeNeighbors != null) {
                for(String fromNodeNeighbor : fromNodeNeighbors) {
                        T toNode = parseT(fromNodeNeighbor, realType);
                        graph.addEdge(fromNode, toNode);
                }
            }
        }

        return graph;
    }

    public Graph<String> readGraph(String graphFile) throws FileNotFoundException {
        return readGraph(graphFile, String.class);
    }
    
    public <T extends Comparable<T>> void assertSameGraph(String graphFile, Graph<T> g) throws FileNotFoundException {
        try {
            @SuppressWarnings("unchecked")
            // extract the nodes map from the graph
            Field field_nodes = Graph.class.getDeclaredField("_nodes");
            field_nodes.setAccessible(true); // Override access control
            Map<Integer, Node<T>> nodes = (Map<Integer, Node<T>>) field_nodes.get(g);
            Class<T> dataClass = (nodes.size() == 0)
                ? (Class<T>)String.class 
                : (Class<T>)(nodes.values().iterator().next().getData().getClass());
            
            Graph<T> expectedGraph = readGraph(graphFile, dataClass );
            assertEquals(expectedGraph.toString(), g.toString());
        } catch(Exception e) {
            fail(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Comparable<T>> void assertValidTopoSort(TreeMap<Integer, TreeSet<T>> topoMap, Graph<T> g2){
        try {
            // extract the nodes map from the graph
            Field field_nodes = Graph.class.getDeclaredField("_nodes");
            field_nodes.setAccessible(true); // Override access control
            Map<Integer, Node<T>> nodes = (Map<Integer, Node<T>>) field_nodes.get(g2);

            // reverse the topological sort map to get the node to index mapping
            Map<Node<T>, Integer> nodeToIdx = new HashMap<>();
            for (Integer topoIdx : topoMap.keySet()) {
                TreeSet<T> labelAtIdx = topoMap.get(topoIdx);
                for (T label : labelAtIdx) {
                    Node<T> node = nodes.get(label.hashCode());
                    nodeToIdx.put(node, topoIdx); // Map each node to its index in the topological sort
                }
            }

            // for each node in the map...
            for (Node<T> fromNode : nodes.values()) {
                Field field_edges = Node.class.getDeclaredField("_edges");
                field_edges.setAccessible(true); // Override access control
                Map<Integer, Node<T>> edges = (Map<Integer, Node<T>>) field_edges.get(fromNode);
                // ... and for each neighbor of that node...
                for (Node<T> toNode : edges.values()) {
                    // ... get the nodes topological sort index
                    Integer fromNodeIdx = nodeToIdx.get(fromNode);
                    Integer toNodeIdx = nodeToIdx.get(toNode);
                    // ... and check that the fromNode index is less than the toNode index
                    assertTrue(fromNodeIdx < toNodeIdx); 
                }
            }
        } catch (Exception e) {
            fail();
        }
    }
}
