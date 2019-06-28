import java.awt.Color;

class Payload {
  private Color c;
  private int cIdx;
  private int lineThickness;
  private int toolIndex;
  private boolean fromChooser;

  public Payload(Color c, int cIdx, int l, int t, boolean fromChooser) {
    this.c = c;
    this.cIdx = cIdx;
    this.lineThickness = l;
    this.toolIndex = t;
    this.fromChooser = fromChooser;
  }

  public void setColor(Color c, int cIdx, boolean fromChooser) {
    this.c = c;
    this.cIdx = cIdx;
    this.fromChooser = fromChooser;
  }

  public void setLineThickness(int l) {
    this.lineThickness = l;
  }

  public void setToolIndex(int t) {
    this.toolIndex = t;
  }

  public Color getColor() {
    return this.c;
  }

  public int getColorIndex() {
    return this.cIdx;
  }

  public int getLineThickness() {
    return this.lineThickness;
  }

  public int getToolIndex() {
    return this.toolIndex;
  }
  
  public boolean isFromChooser() {
    return this.fromChooser;
  }
}