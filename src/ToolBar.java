import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.Box;

import java.util.ArrayList;

import java.awt.Dimension;
import java.awt.Color;

public class ToolBar extends JPanel implements ChooserButtonObserver, ColorButtonObserver, ToolButtonObserver, LineButtonObserver, CanvasObserver {
  private ToolPicker toolPicker = new ToolPicker();
  private ColorPicker colorPicker = new ColorPicker();
  private Chooser chooser = new Chooser();
  private LinePicker linePicker = new LinePicker();
  private Payload dataPayload = new Payload(Color.BLUE, 0, 2, 0, false);
  private ArrayList<ToolBarObserver> toolBarObservers = new ArrayList<>();
  
  public ToolBar() {
    this.colorPicker.addObserver(chooser);
    this.chooser.addObserver(colorPicker);
    this.colorPicker.addObserver(this);
    this.chooser.addObserver(this);
    this.toolPicker.addObserver(this);
    this.linePicker.addObserver(this);
    this.toolPicker.addObserver(this.colorPicker);
    this.toolPicker.addObserver(this.chooser);
    this.toolPicker.addObserver(this.linePicker);
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.add(this.toolPicker);
    this.add(Box.createVerticalStrut(5));
    this.add(this.colorPicker);
    this.add(Box.createVerticalStrut(5));
    this.add(this.chooser);
    this.add(Box.createVerticalStrut(5));
    this.add(this.linePicker);
  }

  public Dimension getPreferredSize() {
    return new Dimension(120, 550);
  }

  public Dimension getMaximumSize() {
    return new Dimension(120, 550);
  }

  public Dimension getMinimumSize() {
    return new Dimension(120, 550);
  }

  public void addObserver(ToolBarObserver ob) {
    this.toolBarObservers.add(ob);
  }

  public void notifyObservers() {
    for(ToolBarObserver observer: this.toolBarObservers) {
      observer.toolBarChanged(this.dataPayload);
    }
  }

  public void chooserSelected(Color c) {
    // System.out.println("chooserSelected");
    this.dataPayload.setColor(c, -1, true);
    this.notifyObservers();
  }

  public void colorSelected(int index) {
    // System.out.println("colorSelected");
    switch(index) {
      case 0: {
        this.dataPayload.setColor(Color.BLUE, 0, false);
        break;
      }
      case 1: {
        this.dataPayload.setColor(Color.RED, 1, false);
        break;
      }
      case 2: {
        this.dataPayload.setColor(Color.ORANGE, 2, false);
        break;
      }
      case 3: {
        this.dataPayload.setColor(Color.YELLOW, 3, false);
        break;
      }
      case 4: {
        this.dataPayload.setColor(Color.GREEN, 4, false);
        break;
      }
      case 5: {
        this.dataPayload.setColor(Color.PINK, 5, false);
        break;
      }
      default: {
        break;
      }
    }
    this.notifyObservers();
  }

  public void lineSelected(int index) {
    // System.out.println("lineSelected");
    switch(index) {
      case 0: {
        this.dataPayload.setLineThickness(2);
        break;
      }
      case 1: {
        this.dataPayload.setLineThickness(4);
        break;
      }
      case 2: {
        this.dataPayload.setLineThickness(6);
        break;
      }
      case 3: {
        this.dataPayload.setLineThickness(10);
        break;
      }
    }
    this.notifyObservers();
  }

  public void toolSelected(int index) {
    // System.out.println("toolSelected");
    this.dataPayload.setToolIndex(index);
    this.notifyObservers();
  }

  public void setPayload(Payload payload) {
    this.dataPayload = payload;
    this.notifyObservers();
  }

  public void canvasShapeSelected(Payload p) {
    this.dataPayload = p;
    this.colorPicker.setColorSelected(p);
    this.chooser.setChooserSelected(p);
    this.linePicker.setLineSelected(p);
    // System.out.println("\nCanvas new payload");
    // System.out.println("Tool: " + this.dataPayload.getToolIndex());
    // System.out.println("Color: " + this.dataPayload.getColor().toString());
    // System.out.println("Color Index: " +  this.dataPayload.getColorIndex());
    // System.out.println("FromChooser: " + this.dataPayload.isFromChooser());
    // System.out.println("Line: " + this.dataPayload.getLineThickness() + "\n");
  }

  public void groupSelectActivate(boolean active) {
    if (active) { 
      this.colorPicker.toolSelected(1);
      this.chooser.toolSelected(1);
      this.linePicker.toolSelected(1);
    } else {
      this.colorPicker.toolSelected(6);
      this.chooser.toolSelected(6);
      this.linePicker.toolSelected(6);
    }
  }
}