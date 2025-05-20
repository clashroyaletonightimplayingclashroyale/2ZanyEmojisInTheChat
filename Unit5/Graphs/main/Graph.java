package Graphs.main;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

/**
 * Class definition for a generic (Directed) Graph.
 * The Graph contains a collection of Nodes, each Node contains
 * a collection of references (edges) to their neighboring Nodes.
 * @param <T> - reference type of Nodes contained in the Graph.
 * The type T is expected to implement the Comparable interface, 
 * such that Nodes can be compared to each other.<br>
 * E.g.:<pre>Graph&lt;String&gt; g = new Graph&ltString&gt();</pre>
 * @see Node
 */
public class Graph<T extends Comparable<T>> {

    /**
     * Private Map keying each Node in the Graph by the hashCode of its data
     * E.g: Given <pre>Node<String> n = new Node<String>("abc");</pre> added to the graph,
     * the _nodes map contains a Map.Entry with
     * <pre>key="abc".hashCode()<br>value=n<pre>
     * @see java.lang.Object#hashCode()
     */
    private Map<Integer, Node<T>> _nodes;
    
    /**
     * Constructs a new Graph as an empty container fit for Nodes of the type T.
     * @see Graph
     * @see Node
     */
    public Graph() {
        _nodes = new TreeMap<Integer, Node<T>>();
    }
    
    /**
     * Gets the size of this Graph. The size of the Graph is equal to the number
     * of Nodes it contains.
     * @return number of Nodes in this Graph.
     */
    public int size() {
        return _nodes.size();
    }
    
    /**
     * Checks if the state of all the Nodes in the Graph matches a given value.
     * @param state - the value to check against all Nodes in the Graph.
     * @return true if all the Nodes in the Graph have a state matching the
     * given value, false otherwise.
     * @see Node#getState()
     */
    public boolean checkState(int state) {
        for (Node<?> n : _nodes.values()) {
            if (state != n.getState()) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Adds a new Node to the Graph containing the <i>data</i>. The method 
     * throws if the Graph already contains a Node with data having the same
     * hashCode().
     * @param data - the data reference (of type T) contained in the new Node.
     * @throws RuntimeException if the Graph already contains a Node for the
     * given data.
     * @see java.lang.Object#hashCode()
     */
    public void addNode(T data) {
        int nodeHash = data.hashCode();
        if (_nodes.containsKey(nodeHash)) {
            throw new RuntimeException("Ambiguous graph!");
        }
        
        _nodes.put(nodeHash, new Node<T>(data));
    }
    
    /**
     * Adds a new directed Edge to the Graph, linking the Nodes containing
     * <i>from</i> and <i>to</i> data. It is expected the two Nodes exist
     * otherwise the method throws an exception.
     * @param from - Node where the Edge is starting.
     * @param to - Node where the Edge is ending.
     * @throws RuntimeException if either of the two Nodes are not present in the Graph.
     * @see Node
     * @see Graph#removeEdge(Comparable, Comparable)
     */
    public void addEdge(T from, T to) {
        Node<T> fromNode = _nodes.get(from.hashCode());
        Node<T> toNode = _nodes.get(to.hashCode());
        if (fromNode == null || toNode == null) {
            throw new RuntimeException("Node(s) not in the graph!");
        }
        
        fromNode.addEdge(toNode);
    }
    
    /**
     * Removes an existent directed Edge from the Graph, if one exists. 
     * The Edge to be removed is linking the nodes containing the <i>from</i> 
     * and <i>to</i> data references. The two Nodes must exist, otherwise the 
     * method throws an exception.
     * @param from - Node at the starting point of the Edge.
     * @param to - Node at the ending point of the Edge.
     * @throws RuntimeException if either of the two Nodes are not present in the Graph.
     * @see Node
     * @see Graph#addEdge(Comparable, Comparable)
     */
    public void removeEdge(T from, T to) {
        // TODO: Implement this method according to
        // TODO: the specification in javadocs
        Node<T> fromNode = _nodes.get(from.hashCode());
        Node<T> toNode = _nodes.get(to.hashCode());
        if (fromNode == null || toNode == null) {
            throw new RuntimeException("Node(s) not in the graph!");
        }
        fromNode.removeEdge(toNode);
        
    }
    
    /**
     * Removes a Node from the Graph if one exists, along with all
     * its outgoing (egress) and incoming (ingress) edges. If there
     * is no Node hosting the <i>data</i> reference the method does
     * nothing.
     * @param data - Node to be removed from the Graph.
     */
    public void removeNode(T data) {
        // TODO: Implement this method according to
        // TODO: the specification in javadocs
        Node<T> toRemove = _nodes.get(data.hashCode());
        if(toRemove == null) return;
        for(Node<T> n : _nodes.values()){
            removeEdge(n.getData(), data);
        }
        _nodes.remove(data.hashCode());
        
    }


    /**
     * Checks if the Graph is undirected.
     * @return true if Graph is undirected, false otherwise.
     */
    public boolean isUGraph() {
        // TODO: Implement this method according to
        // TODO: the specification in javadocs

        //for all the nodes in the map
        for(Node<T> node: _nodes.values()){  
            //for that nodes neighbor
            for(Node<T> neighbor : node.getNeighbors()){
                //if the neighbors neighbor list does not contain the current node we are looking at then return false
                if (!neighbor.getNeighbors().contains(node)) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Checks if the Graph is connected.
     * @return true if the Graph is connected, false otherwise.
     */
    public boolean isConnected() {
        // TODO: Implement this method according to
        // TODO: the specification in javadocs

        //if empty return true;
        int size = _nodes.size();
        if(size == 0){
            return true;
        }

        //visited nodes
        Set<Node<T>> visited = new HashSet();
        //queue for traversing graph
        Queue<Node<T>> queue = new LinkedList<>();
        //arraylist to add random starting node
        List<Node<T>> list = new ArrayList<>(_nodes.values());
        queue.add(list.get(0));

        //while there are still items in the queue
        while(!queue.isEmpty()){
            //look at current one
            Node<T> current = queue.remove();
            //add current to the visited
            visited.add(current);
            //add the neighbors (if not already visited) to the queue
            Set<Node<T>> neighbors = current.getNeighbors();
            for(Node<T> neighbor: neighbors){
                if(!visited.contains(neighbor)){
                    queue.add(neighbor);
                }
            }
        }
        return size == visited.size();
    }

    /**
     * Checks if the Graph is Directed Acyclic graph.
     * @return true if Graph is Directed Acyclic, false otherwise.
     */
    public boolean isDAGraph(){
        //check whether it is directed
        if(isUGraph()){
            return false;
        }

        //check whether it is acyclic
        // use stack for checking recursively the stack
        Stack<Node<T>> recursiveStack = new Stack();

        for(Node<T> node: _nodes.values()){
           setAll(0);
           if(node.hasCycle()){
            return false;
           }
            // recursiveStack.clear();
            // if(helperMethod(recursiveStack, node)){
            //     return false;
            // }
        }

        return true;
    }

    public boolean helperMethod(Stack<Node<T>> recursiveStack, Node<T> current){
        //add to the recursivestack
        recursiveStack.push(current);
        //for all the neighbors, 
        for(Node<T> neighbor: current.getNeighbors()){
            //if the stack already has the neighbor, that means that it is going to be a loop
            if(recursiveStack.contains(neighbor)){
                return true;
            }else{
                helperMethod(recursiveStack, neighbor);
            }
        }

        return false;
    }

    public void setAll(int i) {
        for (Node<T> node : _nodes.values()) {
            node.setState(i);
        }
    }

    /**
     * Generates the adjacency matrix for this Graph.
     * @return the adjacency matrix.
     */
    public int[][] getAdjacencyMatrix() {
        // TODO: Implement this method according to
        // TODO: the specification in javadocs

        
        //2d matrix for the adjacency matrix
        int[][] adjacencyMatrix = new int[_nodes.size()][_nodes.size()];
        //arraylist for convenience, this also determines the indexes of the adjacency matrix.
        List<Node<T>> list = new ArrayList<>(_nodes.values());
        for(int i = 0; i < list.size(); i++){
            Node<T> current = list.get(i);
            for(Node<T> neighbor : current.getNeighbors()){
                int index = list.indexOf(neighbor);
                adjacencyMatrix[i][index] = 1;
            }
        }

        return adjacencyMatrix;
    }

    /**
     * Gives a multi-line String representation of this Graph. Each line in the returned
     * string represent a Node in the graph, followed by its outgoing (egress) Edges.
     * E.g: If the graph contains 3 nodes, A, B an C, where A and B point to each other and
     * both of them point to C, the value returned by toString() will be as follows:
     * <pre>
     * A > B C
     * B > A C
     * C > 
     * </pre>
     * <u>Note:</u> Each line is a space-separated sequence of token. A Node with no
     * outgoing (egress) edges, like C in the example above still has a line where 
     * the ' > ' token is surrounded by the space characters.
     * @return multi-line String reflecting the content and structure of this Graph.
     */
    @Override
    public String toString() {
        String output = "";
        boolean first = true;
        for(Node<?> n : _nodes.values()) {
            if (!first) {
                output += "\n";
            }
            output += n.toString();
            first = false;
        }
        
        return output;
    }

    /**
     * Generates a map grouping all nodes in the graph by their out-degree.
     * 
     * @return a map where each out-degree value in the graph (key) is associated
     * with the set of nodes (value) having that out-degree.
     */

     /**
     * The out-degree of a Node is the number of Edges leading out from it.
     * It returns a TreeMap associating each out-degree count (key) to the
     * set of Nodes having that out-degree (value).
     */
    public TreeMap<Integer, TreeSet<T>> getOutDegrees() {
        TreeMap<Integer, TreeSet<T>> map = new TreeMap<>();
        
        for(Node<T> node : _nodes.values()){
            int x = node.getNeighbors().size();
            map.putIfAbsent(x, new TreeSet<T>());
            map.get(x).add(node.getData());
        }
        return map;
    }

    /**
     * Generates a map grouping all nodes in the graph by their in-degree.
     * @return a map where each in-degree value in the graph (key) is associated
     * with the set of nodes (value) having that in-degree.
     */
    public TreeMap<Integer, TreeSet<T>> getInDegrees() {
        Map<Node<T>, Integer> map = new HashMap<>();

        for (Node<T> node : _nodes.values()) {
            map.put(node, 0);
        }

        for(Node<T> node : _nodes.values()){
            Set<Node<T>> neighbors = node.getNeighbors();
            for(Node<T> neighbor: neighbors){
                map.put(neighbor,map.get(neighbor)+1);
            }
        }
        TreeMap<Integer, TreeSet<T>> sigma = new TreeMap<>();
        for(Entry<Node<T>, Integer> set: map.entrySet()){
            sigma.putIfAbsent(set.getValue(), new TreeSet<T>());
            sigma.get(set.getValue()).add(set.getKey().getData());
        }
        return sigma;
    }

    /**
     * Generates the topological sort of this graph, where all nodes in the graph
     * are grouped by their index in topological order. The first index is 0.
     * @return a map associating each position in the topological sort (key)
     * with the set of Nodes at that position (value). If the Graph is not DAG, the method 
     * returns null.
     */
    public TreeMap<Integer, TreeSet<T>> topoSort() {
        if (!isDAGraph()) {
            return null;
        }

        Map<T, Integer> inDegree = new HashMap<>();
        TreeMap<Integer, TreeSet<T>> indegreeMap = getInDegrees();

        for (Map.Entry<Integer, TreeSet<T>> entry : indegreeMap.entrySet()) {
            int degree = entry.getKey();
            for (T data : entry.getValue()) {
                inDegree.put(data, degree);
            }
        }
        Map<T, Node<T>> dataToNode = new HashMap<>();
        for (Node<T> node : _nodes.values()) {
            dataToNode.put(node.getData(), node);
        }

        Queue<T> queue = new LinkedList<>();
        for (Map.Entry<T, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        TreeMap<Integer, TreeSet<T>> sorted = new TreeMap<>();
        int level = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            TreeSet<T> group = new TreeSet<>();
            for (int i = 0; i < size; i++) {
                T currentData = queue.poll();
                group.add(currentData);

                Node<T> currentNode = dataToNode.get(currentData);
                for (Node<T> neighbor : currentNode.getNeighbors()) {
                    T neighborData = neighbor.getData();
                    int updatedInDegree = inDegree.get(neighborData) - 1;
                    inDegree.put(neighborData, updatedInDegree);
                    if (updatedInDegree == 0) {
                        queue.add(neighborData);
                    }
                }
            }
            sorted.put(level++, group);
        }
        return sorted;
    }



    /**
     * Generates the count of the partitions in the graph.
     * @return count of partitions.
     */
    public int countPartitions() {
        // TODO: Implement this method according to
        // TODO: the specification in javadocs
        
        int partitionCount = 0;
        for(Node<T> current: _nodes.values()){
            if(current.getState() != 1){
                partitionCount++;
                dfs(current);
            }
        }
        return partitionCount;
    
    }

    public Set<Node<T>> getIncomingNeighbors(Node<T> target) {
        Set<Node<T>> incoming = new HashSet<>();
        for (Node<T> node : _nodes.values()) {
            if (node.getNeighbors().contains(target)) {
                incoming.add(node);
            }
        }
        return incoming;
    }    

    public void dfs(Node<T> current){
        current.setState(1);
        Set<Node<T>> allNeighbors = new HashSet<>();
        allNeighbors.addAll(current.getNeighbors());
        allNeighbors.addAll(getIncomingNeighbors(current));
        for(Node<T> neighbor: allNeighbors){
            if(neighbor.getState() == 0){
                dfs(neighbor);
            }
        }
    }

    /**
     * Generates the Dijkstra distances between the node containing fromData and all the
     * other nodes in the graph.
     * @param fromData
     * @return a map where the key is each Node in the Graph (given by its data)
     * and the value is the Dijkstra distance from the <i>source</i> Node to that node.
     */
    public TreeMap<T, Integer> dijkstra(T fromData) {
        setAll(0);
        TreeMap<T, Integer> distances = new TreeMap<>();

        //initialize all distances as unreachable
        for (Node<T> node : _nodes.values()) {
            T data = node.getData();
            distances.put(data, -1);
        }        
        //distance from source to itself is 0
        Node<T> sourceNode = null;
        for(Node<T> node : _nodes.values()){
            if(node.getData().equals(fromData)){
                sourceNode = node;
                break;
            }
        }
        distances.put(fromData, 0);
        sourceNode.setState(1);  

        //create queue
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(sourceNode);
        
        while(!queue.isEmpty()){
            Node<T> removed = queue.poll();
            int currentDist = distances.get(removed.getData());
            for(Node<T> neighbor : removed.getNeighbors()){
                if(neighbor.getState() == 0){
                    distances.put(neighbor.getData(), currentDist+1);
                    neighbor.setState(1);
                    queue.add(neighbor);
                }
            }
        }



        return distances;
    }
}


