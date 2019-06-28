import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Rectangle implements Shape {
  private int x;
  private int y;
  private int w;
  private int h;
  private int xStep = 0;
  private int yStep = 0;
  private Color color;
  private int colorIdx;
  private boolean fromChooser;
  private int lineThickness;
  private boolean selected;

  public Rectangle(Payload dataPayload, int x, int y, int w, int h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.color = dataPayload.getColor();
    this.fromChooser = dataPayload.isFromChooser();
    this.colorIdx = dataPayload.getColorIndex();
    this.lineThickness = dataPayload.getLineThickness();
    this.selected = false;
  }

  public void drawShape(Graphics2D g) {
    g.setColor(this.color);
    g.fillRect(this.x, this.y, this.w, this.h);
    g.setStroke(new BasicStroke(this.lineThickness));
    g.setColor(Color.BLACK);
    g.drawRect(this.x, this.y, this.w, this.h);
    if (selected) {
      g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {8, 4}, 0));
      g.setColor(Color.LIGHT_GRAY);
      g.drawRect(this.x, this.y, this.w, this.h);
    }
  }

  public boolean hitTest(int x, int y) {
    return x >= this.x && x <= (this.x + this.w) && y >= this.y && y <= (this.y + this.h);
  }

  public int getShapeType() {
    return 2;
  }

  public List<Integer> getShapeInfo() {
    ArrayList<Integer> info = new ArrayList<Integer>();
    info.add(this.x);
    info.add(this.y);
    info.add(this.xStep);
    info.add(this.yStep);
    return info;
  }

  public void setStep(int x, int y) {
    this.xStep = x;
    this.yStep = y;
  }

  public void setShapeInfo(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public Payload getDataPayload(Payload p) {
    return new Payload(this.color, this.colorIdx, this.lineThickness, p.getToolIndex(), this.fromChooser);
  }

  public void setColorAndThickness(Color c, int cIdx, boolean fromChooser, int lineThickness) {
    this.color = c;
    this.colorIdx = cIdx;
    this.fromChooser = fromChooser;
    this.lineThickness = lineThickness;
  }

  public String genSaveFileString() {
    String type = "Rectangle";
    String pos = this.x + "," + this.y + "," + this.w + "," + this.h;
    String color = this.color.getRed() + "," + this.color.getGreen() + "," + this.color.getBlue() + "," +  this.colorIdx + "," + this.fromChooser;
    String line = "" + this.lineThickness;
    return type + "," + pos + "," + color + "," + line;
  }
}