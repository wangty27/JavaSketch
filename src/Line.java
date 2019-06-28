import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import java.lang.Math;

import java.util.ArrayList;
import java.util.List;

public class Line implements Shape {
  private int x;
  private int y;
  private int xDist;
  private int yDist;
  private int xStep = 0;
  private int yStep = 0;
  private int endX;
  private int endY;
  private Color color;
  private int colorIdx;
  private boolean fromChooser;
  private int lineThickness;
  private boolean selected;

  public Line(Payload dataPayload, int x, int y, int endX, int endY) {
    this.x = x;
    this.y = y;
    this.endX = endX;
    this.endY = endY;
    this.xDist = endX - x;
    this.yDist = endY - y;
    this.color = dataPayload.getColor();
    this.fromChooser = dataPayload.isFromChooser();
    this.colorIdx = dataPayload.getColorIndex();
    this.lineThickness = dataPayload.getLineThickness();
    this.selected = false;
  }

  public void drawShape(Graphics2D g) {
    g.setColor(this.color);
    g.setStroke(new BasicStroke(this.lineThickness));
    g.drawLine(this.x, this.y, this.endX, this.endY);
    if (selected) {
      g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 8, 4 }, 0));
      g.setColor(Color.LIGHT_GRAY);
      g.drawLine(this.x, this.y, this.endX, this.endY);
    }
  }

  /**
   * Using sample code from slide to calculate point to line distance
   */
  public boolean hitTest(int x, int y) {
    double distance = Line2D.ptSegDist(this.x, this.y, this.endX, this.endY, x, y);
    return distance <= 5;
  }

  public int getShapeType() {
    return 0;
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
    this.endX = x + xDist;
    this.endY = y + yDist;
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
    String type = "Line";
    String pos = this.x + "," + this.y + "," + this.endX + "," + this.endY;
    String color = this.color.getRed() + "," + this.color.getGreen() + "," + this.color.getBlue() + "," +  this.colorIdx + "," + this.fromChooser;
    String line = "" + this.lineThickness;
    return type + "," + pos + "," + color + "," + line;
  }
}