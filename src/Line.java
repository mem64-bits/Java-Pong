import java.awt.Color;
import java.awt.Graphics;

public class Line {
    int x,y;
    int height,width;
    Color lineColor;

    public Line(int x, int y, int height,int width, Color lineColor){
        this.x = x;
        this.y = y;
        this.height =height;
        this.width = width;
        this.lineColor = lineColor;
    }

    public void drawLine(Graphics g){
        g.setColor(lineColor);
        g.fillRect(x, y, width,height);
    }
}
