import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

interface Shape {
  void drawShape(Graphics2D g);
  boolean hitTest(int x, int y);
  int getShapeType();
  List<Integer> getShapeInfo();
  void setShapeInfo(int x, int y);
  void setSelected(boolean selected);
  Payload getDataPayload(Payload p);
  void setColorAndThickness(Color c, int cIdx, boolean fromChooser, int lineThickness);
  String genSaveFileString();
  void setStep(int x, int y);
} 

interface ToolButtonObserver {
  void toolSelected(int index);
}

interface ColorButtonObserver {
  void colorSelected(int index);
}

interface ChooserButtonObserver {
  void chooserSelected(Color c);
}

interface LineButtonObserver {
  void lineSelected(int index);
}

interface ToolBarObserver {
  void toolBarChanged(Payload p);
}

interface CanvasObserver {
  void canvasShapeSelected(Payload p);
  void groupSelectActivate(boolean active);
}