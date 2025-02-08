package ColoringBook;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import DrawingLib.drawing.DrawingFrame;
import DrawingLib.drawing.KeyInterceptor.KeyHook;
import DrawingLib.drawing.MouseInterceptor.MouseHook;
import DrawingLib.drawing.Drawing;

public class Program {
    
    /**
     * Global static fields for the Drawing object being worked on
     * and the DrawingFrame containing and displaying it.
     */
    private static Drawing _drawing;
    private static DrawingFrame _frame;

    
    // /**
    //  * Demonstrates a simple alteration to the drawing:
    //  * On a square section of the image, from top-left: (40,30) to bottom-right (140, 130)
    //  * replace the dark pixels with yellow and the bright pixels with yellow.
    //  */
    // public static void paint() throws InterruptedException {
    //     for(int x = 40; x < 140; x++) {
    //         if (x == 90) {
    //             _frame.stop();
    //         }
    //         for (int y = 30; y < 130; y++) {
    //             _frame.step(1);
    //             Color c = _drawing.getPixel(x, y);
    //             if (_drawing.isDarkPixel(x, y)) {
    //                 _drawing.setPixel(x, y, c.brighter());
    //             } else {
    //                 _drawing.setPixel(x, y, c.darker());
    //             }
    //         }
    //     }
    // }

    public static drawingPoint[] getNeighbors(int x, int y){
        drawingPoint[] neighbors = new drawingPoint[8];
        neighbors[0] = new drawingPoint(x+1, y+1);
        neighbors[1] = new drawingPoint(x+1, y);
        neighbors[2] = new drawingPoint(x+1, y-1);
        neighbors[3] = new drawingPoint(x, y+1);
        neighbors[4] = new drawingPoint(x+1, y-1);
        neighbors[5] = new drawingPoint(x-1, y+1);
        neighbors[6] = new drawingPoint(x-1, y);
        neighbors[7] = new drawingPoint(x-1, y-1); 
        return neighbors;
    }

    // Recursive implementation
    public static void recursiveFlood(int xSeed, int ySeed, Color color){
        //color the pixel in desired color
        _drawing.setPixel(xSeed,ySeed,color);
        _frame.repaint();
        drawingPoint[] neighbors = getNeighbors(xSeed, ySeed);
        for(drawingPoint har: neighbors){
            if(_drawing.isValidPixel(har.getX(), har.getY()) && _drawing.isBrightPixel(har.getX(), har.getY())){
                recursiveFlood(har.getX(), har.getY(), color);
            }
        }
    }

    //stack implementation
    public static void stackFlood(int xSeed, int ySeed, Color color){
        _drawing.setPixel(xSeed,ySeed, color);
        
        Stack<drawingPoint> stack = new Stack<drawingPoint>();
        drawingPoint workItem = new drawingPoint(xSeed, ySeed);
        stack.push(workItem);
        
        while(!stack.isEmpty()){
            drawingPoint har = stack.pop();
            drawingPoint[] neighbors = getNeighbors(har.getX(), har.getY());
            for(drawingPoint neighbor: neighbors){
                if(_drawing.isValidPixel(neighbor.getX(), neighbor.getY()) && _drawing.isBrightPixel(neighbor.getX(), neighbor.getY())){
                    _drawing.setPixel(neighbor.getX(),neighbor.getY(), color);
                    stack.push(neighbor);
                    _frame.repaint();
                }
            }
        }
    }

    //queueflood 
    public static void queueFlood(int xSeed, int ySeed, Color color){

        _drawing.setPixel(xSeed,ySeed, color);
        
        Queue<drawingPoint> queue = new LinkedList<drawingPoint>();

        drawingPoint workItem = new drawingPoint(xSeed, ySeed);
        queue.add(workItem);
        
        while(!queue.isEmpty()){
            drawingPoint har = queue.remove();
            drawingPoint[] neighbors = getNeighbors(har.getX(), har.getY());
            for(drawingPoint neighbor: neighbors){
                if(_drawing.isValidPixel(neighbor.getX(), neighbor.getY()) && _drawing.isBrightPixel(neighbor.getX(), neighbor.getY())){
                    _drawing.setPixel(neighbor.getX(),neighbor.getY(), color);
                    queue.add(neighbor);
                    _frame.repaint();
                }
            }
        }
    }



    public static Color randomColor(){
        Random random = new Random();
        return new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256));
    }


    public static MouseHook _onMouseClick = (mouseEvent) -> {
        System.out.printf("(%d,%d)\n", _frame.getCanvasX(mouseEvent), _frame.getCanvasY(mouseEvent));

        //can test diff flood techniques
        queueFlood(_frame.getCanvasX(mouseEvent), _frame.getCanvasY(mouseEvent), randomColor());
        //stackFlood(_frame.getCanvasX(mouseEvent), _frame.getCanvasY(mouseEvent), Color.BLUE);
        //recursiveFlood(_frame.getCanvasX(mouseEvent), _frame.getCanvasY(mouseEvent), Color.BLUE);
    };

    public static KeyHook _onKeyPress = (keyEvent) ->{
        
        char keyCode = keyEvent.getKeyChar();
        if(keyCode == 'r'){
            
        }

    };
    
    /**
     * Main entry point in the program:
     * Initializes the static Drawing (_drawing) with an image of your choice,
     * then initializes the static DrawingFrame (_frame) loading into it the new drawing.
     * Subsequently the frame is opened on the screen then the drawing is painted upon
     * and displayed as it is being modified before the program terminates. 
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Welcome to the Coloring Festival!");
        
        // pick a drawing -- correct the path to point to the drawing of your choice
        _drawing = Drawing.read("Unit2/ColoringBook/drawings/bird.jpg");
        
        // put it in a frame
        _frame = new DrawingFrame(_drawing);
        
        // put the frame on display and stop to admire it.
        _frame.open();
        
        // setup a hook such that we know where we're clicking
        _frame.setMouseClickedHook(_onMouseClick);
        
        // the show is over.
        _frame.close();
        
        System.out.println("Well done, goodbye!");

    }
}
