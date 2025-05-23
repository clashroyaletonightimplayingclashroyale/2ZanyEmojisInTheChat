package TrafficFlow;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import edu.ftdev.KeyInterceptor.KeyHook;
import edu.ftdev.Map.MapCanvas;

public class Program {
    
    private static Map<Character, Queue<String>> _locationsMap = new HashMap<Character, Queue<String>>();
    private static Queue<Set<String>> trafficFlowSubsets = new LinkedList<>();

    private static void buildLocationMap(MapCanvas mc){

        Set<String> routes = mc.getRoutes();

            for(String route : routes){

                char sigma = route.charAt(0);    

                if(!_locationsMap.containsKey(sigma)){
                    Queue<String> har = new LinkedList<String>();
                    har.add(route);
                    _locationsMap.put(sigma, har);
                }else{
                    Queue<String> gotten = _locationsMap.get(sigma);
                    gotten.add(route);
                }  
        }

        char[] characters = {'A', 'B', 'C', 'D', 'E'};
        for(char no : characters){
            if(!_locationsMap.containsKey(no)){
                _locationsMap.put(no, null);
            }
        }  
        System.out.println(_locationsMap);      
    }
    
    /**
     * Lambda method which will be called each time the user
     * is pressing the key 'T'.
     * @param keyEvent - details about the key which was pressed.
     */
    private static KeyHook _onKeyT = (KeyEvent keyEvent, Object[] args) -> {
        MapCanvas mc = (MapCanvas)args[0];
        String statusText = "Key: '" + keyEvent.getKeyChar() + "'; ";
        statusText += "Routes: " + mc.getRoutes();
        mc.setStatusMessage(statusText);
    };
    
    private static KeyHook onKeyQ = (keyEvent, args) -> {
        MapCanvas mp = (MapCanvas) args[0];
        Queue<String> routes = (Queue<String>) args[1];
        mp.setOverlays(routes.peek());
        routes.add(routes.remove());
    };

    private static KeyHook onKeyGeneric = (KeyEvent keyEvent, Object[] args) ->{
        MapCanvas mc = (MapCanvas) args[0];
        char key = Character.toUpperCase(keyEvent.getKeyChar());
        mc.clear();
        if(_locationsMap.get(key) != null){
            Queue<String> routes = _locationsMap.get(key);
            if (routes.peek() != null) {
                mc.setOverlays(routes.peek());
                routes.add(routes.remove());
            }
        }
       
    };
    
    //pt 2
    private static KeyHook collisionOnX = (KeyEvent keyEvent, Object[] args) ->{
        MapCanvas mc = (MapCanvas) args[0];
        //make it list so that its ez to access the one element
        List<String> currentRoutes = new ArrayList<>(mc.getOverlays());
        if(currentRoutes.size()==0){
            System.out.println("Select a path");
        }else if(currentRoutes.size() == 1){
            //this means that its just the one route 
            String single = currentRoutes.get(0);
            //for all the available routes,
            List<String> allRoutes = new ArrayList<>(mc.getRoutes());
            Set<String> toBeOverlayed = new HashSet<String>();
            toBeOverlayed.add(single);
            for(String route: allRoutes){
                //if they collide, add it to the to be overlayed
                if(!route.equals(single) && mc.collide(single, route)){
                    toBeOverlayed.add(route);
                }
            }
            //overlay it
            mc.setOverlays(toBeOverlayed);
        }else{
            //else, only display the one route 
            String original = currentRoutes.get(0);
            mc.setOverlays(original);
        }
    };

    //pt 3
    private static void greedy(int label, Queue<String> unlabeledQueue, MapCanvas map, Map<Integer, Set<String>> subsets) {
        Set<String> currentLabelSet = new HashSet<>();
        Queue<String> requeue = new LinkedList<>();
        while (!unlabeledQueue.isEmpty()) {
            String node = unlabeledQueue.remove();
            boolean hasCollision = false;
            for (String labeledNode : currentLabelSet) {
                if (map.collide(node, labeledNode)) {
                    hasCollision = true;
                    break;
                }
            }
            if (!hasCollision) {
                currentLabelSet.add(node);
            } else {
                requeue.add(node);
            }
        }
        subsets.put(label, currentLabelSet);
        unlabeledQueue.addAll(requeue); 
    }    

    //helper method to find collisions:

    private static KeyHook graphColoring = (KeyEvent KeyEvent, Object[] args) ->{
        MapCanvas mc = (MapCanvas) args[0];

        Map<Integer, Set<String>> subsets = new HashMap<>();
        Queue<String> unLabeledQueue = new LinkedList<>(mc.getRoutes());
        int label = 1;
        while(!unLabeledQueue.isEmpty()){
            greedy(label, unLabeledQueue,mc, subsets);
            label++;
        }
        //add to the trafficflowsubset if not initialized
        if(trafficFlowSubsets.size()== 0){
            for (Map.Entry<Integer, Set<String>> set : subsets.entrySet()) {
                trafficFlowSubsets.add(set.getValue());
            }
        }
        System.out.println(subsets);
        //you can now traverse through it
        Set<String> toBeOverlayed = trafficFlowSubsets.poll();
        mc.setOverlays(toBeOverlayed);
        trafficFlowSubsets.add(toBeOverlayed);
    };

  

    public static void main(String[] args) throws IOException, InterruptedException {
        // create a MapCanvas object and load it with an intersection image
        MapCanvas mapCanvas = new MapCanvas("Ravenna.jpg");

        //test building the location map
        buildLocationMap(mapCanvas);
        
        // registers the key T with the method _onKeyT
        mapCanvas.setKeyHook('T', _onKeyT, mapCanvas);
        Queue<String> routes = new LinkedList<String>(mapCanvas.getRoutes());

        //parts 2 and 3
        mapCanvas.setKeyHook(KeyEvent.VK_Q, onKeyQ, mapCanvas, routes);
        mapCanvas.setKeyHook(KeyEvent.VK_X, collisionOnX, mapCanvas);
        mapCanvas.setKeyHook(KeyEvent.VK_W, graphColoring, mapCanvas);

        //registers A-E with generic keyhook to reduce redundancy.
        char[] letters = {'A','B','C','D','E'};
        for(char letter: letters){
            mapCanvas.setKeyHook(letter,onKeyGeneric, mapCanvas);
        }
    
        
        // opens the GUI window
        mapCanvas.open();
        
        // break;step-level execution
        mapCanvas.breakStep();

        // register the 'A', 'B', 'C', .. key strokes for demo route highlights
        mapCanvas.setDemoKeyHooks(false);

        // break jump-level execution
        mapCanvas.breakJump();
        
        // close the window and terminate the program
        mapCanvas.close();
    }
}
