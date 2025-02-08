package ColoringBook;

public class drawingPoint {

    private int x;
    private int y;

    public drawingPoint(int x,int y){
        this.x = x;
        this.y = y;
    }

    public drawingPoint(){
        this.x = 0;
        this.y = 0;
    }
    
    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
    
}
