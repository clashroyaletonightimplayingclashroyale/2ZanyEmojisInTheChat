package TrafficFlow;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import edu.ftdev.KeyInterceptor.KeyHook;
import edu.ftdev.Map.MapCanvas;

public class Program {
    
    private static Map<Character, Set<String>> _locationsMap = new HashMap<Character, Set<String>>();

    private static void buildLocationMap(MapCanvas mc){

        Set<String> routes = mc.getRoutes();

            for(String route : routes){

                char sigma = route.charAt(0);    

                if(!_locationsMap.containsKey(sigma)){
                    Set<String> har = new HashSet<String>();
                    har.add(route);
                    _locationsMap.put(sigma, har);
                }else{
                    Set<String> gotten = _locationsMap.get(sigma);
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
        //System.out.println("You pressed " + key +"!");
        mc.clear();
        Set<String> har = _locationsMap.get(key);
        if(har != null){
            mc.setOverlays(har);
        }
    };

    // private static KeyHook collision = (KeyEvent keyEvent, Object[] args) ->{
    //     MapCanvas mc = (MapCanvas) args[0];
    //     Set<String> overlaidRoutes = mc.getOverlays();

        
    //     if(overlaidRoutes.size() ==1){
    //         //this means that its just the overlaid route 
    //     }
    // }


    public static void main(String[] args) throws IOException, InterruptedException {
        // create a MapCanvas object and load it with an intersection image
        MapCanvas mapCanvas = new MapCanvas("Ravenna.jpg");

        //test building the location map
        buildLocationMap(mapCanvas);
        
        // registers the key T with the method _onKeyT
        mapCanvas.setKeyHook('T', _onKeyT, mapCanvas);
        Queue<String> routes = new LinkedList<String>(mapCanvas.getRoutes());
        mapCanvas.setKeyHook(KeyEvent.VK_Q, onKeyQ, mapCanvas, routes);

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
        mapCanvas.setDemoKeyHooks(true);

        // break jump-level execution
        mapCanvas.breakJump();
        
        // close the window and terminate the program
        mapCanvas.close();
    }
}
