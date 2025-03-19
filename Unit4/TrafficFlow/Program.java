package TrafficFlow;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import edu.ftdev.KeyInterceptor.KeyHook;
import edu.ftdev.Map.MapCanvas;

public class Program {
    
   
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

    public static void main(String[] args) throws IOException, InterruptedException {
        // create a MapCanvas object and load it with an intersection image
        MapCanvas mapCanvas = new MapCanvas("Woodlawn.jpg");

        // registers the key T with the method _onKeyT
        mapCanvas.setKeyHook('T', _onKeyT, mapCanvas);
        Queue<String> routes = new LinkedList<String>(mapCanvas.getRoutes());
        mapCanvas.setKeyHook(KeyEvent.VK_Q, onKeyQ, mapCanvas, routes);

        
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
