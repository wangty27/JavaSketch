import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFrame;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class Canvas extends JPanel implements ToolBarObserver {
  private List<CanvasObserver> observers = new ArrayList<>();
  private Payload dataPayload;
  private int mouseStartX = -1;
  private int mouseStartY = -1;
  private boolean initialHit = false;
  private JFrame rootFrame;
  private List<Shape> shapeList = new ArrayList<>();
  private Shape selectedShape = null;
  private List<Shape> groupSelectedShape = new ArrayList<>();
  private boolean groupHit = false;
  private boolean newShapePending = false;
  private boolean ctrlPressed = false;

  public Canvas(JFrame rootFrame) {
    this.rootFrame = rootFrame;
    this.setBackground(Color.WHITE);
    this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    this.setFocusable(true);
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        handleMouseDown(e);
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        handleMouseUp(e);
      }
    });
    this.addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        handleMouseDrag(e);
      }
    });
    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        handleKeyDown(e);
      }

      @Override
      public void keyReleased(KeyEvent e) {
        handleKeyUp(e);
      }
    });
  }

  public void addNotify() {
    super.addNotify();
    this.requestFocus();
  }

  public Dimension getPreferredSize() {
    return new Dimension(670, 500);
  }

  public Dimension getMaximumSize() {
    return new Dimension(670, 400);
  }

  public Dimension getMinimumSize() {
    return new Dimension(670, 400);
  }

  private void handleMouseDown(MouseEvent e) {
    this.mouseStartX = e.getX();
    this.mouseStartY = e.getY();
    switch (this.dataPayload.getToolIndex()) {
    case 0: {
      int curX = e.getX();
      int curY = e.getY();
      int shapeIdx = -1;
      if (this.ctrlPressed) {
        // System.out.println("Group select");
        Shape hitShape = null;
        for (int i = this.shapeList.size() - 1; i >= 0; --i) {
          if (this.shapeList.get(i).hitTest(curX, curY) && shapeIdx < 0) {
            hitShape = this.shapeList.get(i);
            shapeIdx = i;
            break;
          }
        }
        if (shapeIdx >= 0) {
          for (CanvasObserver ob: this.observers) {
            ob.groupSelectActivate(true);
          }
          if (this.selectedShape != null) {
            this.groupSelectedShape.add(this.selectedShape);
            this.selectedShape = null;
          }
          hitShape.setSelected(true);
          this.groupSelectedShape.add(hitShape);
          this.repaint();
        }
      } else if (this.groupSelectedShape.size() > 0) {
        this.groupHit = false;
        for (Shape shape: this.groupSelectedShape) {
          if (shape.hitTest(curX, curY)) {
            this.groupHit = true;
            break;
          }
        }
        if (this.groupHit) {
          for (Shape shape: this.groupSelectedShape) {
            List<Integer> shapeInfo = shape.getShapeInfo();
            int xStep = curX - shapeInfo.get(0);
            int yStep = curY - shapeInfo.get(1);
            shape.setStep(xStep, yStep);
          }
        }
        // System.out.println("Group hit: " + this.groupHit);
      } else {
        for (int i = this.shapeList.size() - 1; i >= 0; --i) {
          if (this.shapeList.get(i).hitTest(curX, curY) && shapeIdx < 0) {
            this.selectedShape = this.shapeList.get(i);
            shapeIdx = i;
            break;
          }
        }
        if (shapeIdx < 0) {
          this.initialHit = false;
        } else {
          for (Shape shape : this.shapeList) {
            shape.setSelected(false);
          }
          this.selectedShape.setSelected(true);
          this.initialHit = true;
          this.shapeList.remove(shapeIdx);
          this.shapeList.add(this.selectedShape);
          List<Integer> info = this.selectedShape.getShapeInfo();
          this.dataPayload = this.selectedShape.getDataPayload(this.dataPayload);
          for (CanvasObserver ob : this.observers) {
            ob.canvasShapeSelected(this.dataPayload);
          }
          this.selectedShape.setStep(curX - info.get(0), curY - info.get(1));
          this.repaint();
        }
      }
      break;
    }
    case 1: {
      this.selectedShape = null;
      this.groupSelectedShape.clear();
      for (Shape shape : this.shapeList) {
        shape.setSelected(false);
      }
      this.repaint();
      int curX = e.getX();
      int curY = e.getY();
      int shapeIdx = -1;
      for (int i = shapeList.size() - 1; i >= 0; --i) {
        if (shapeList.get(i).hitTest(curX, curY)) {
          this.selectedShape = shapeList.get(i);
          shapeIdx = i;
          break;
        }
      }
      if (shapeIdx < 0) {
        this.selectedShape = null;
      } else {
        this.mouseStartX = curX;
        this.mouseStartY = curY;
      }
      break;
    }
    case 2: {
      this.selectedShape = null;
      this.groupSelectedShape.clear();
      for (Shape shape : this.shapeList) {
        shape.setSelected(false);
      }
      this.repaint();
      this.newShapePending = true;
      break;
    }
    case 3: {
      this.selectedShape = null;
      this.groupSelectedShape.clear();
      for (Shape shape : this.shapeList) {
        shape.setSelected(false);
      }
      this.repaint();
      this.newShapePending = true;
      break;
    }
    case 4: {
      this.selectedShape = null;
      this.groupSelectedShape.clear();
      for (Shape shape : this.shapeList) {
        shape.setSelected(false);
      }
      this.repaint();
      this.newShapePending = true;
      break;
    }
    case 5: {
      this.selectedShape = null;
      this.groupSelectedShape.clear();
      for (Shape shape : this.shapeList) {
        shape.setSelected(false);
      }
      this.repaint();
      int curX = e.getX();
      int curY = e.getY();
      int shapeIdx = -1;
      for (int i = shapeList.size() - 1; i >= 0; --i) {
        if (shapeList.get(i).hitTest(curX, curY)) {
          this.selectedShape = shapeList.get(i);
          shapeIdx = i;
          break;
        }
      }
      if (shapeIdx < 0) {
        this.selectedShape = null;
      } else {
        this.mouseStartX = curX;
        this.mouseStartY = curY;
      }
      break;
    }
    default:
      break;
    }
  }

  private void handleMouseDrag(MouseEvent e) {
    switch (this.dataPayload.getToolIndex()) {
    case 0: {
      if (this.ctrlPressed) {
        break;
      }
      if (this.groupSelectedShape.size() > 0 && this.groupHit) {
        for (Shape shape: this.groupSelectedShape) {
          int xStep = shape.getShapeInfo().get(2);
          int yStep = shape.getShapeInfo().get(3);
          shape.setShapeInfo(e.getX() - xStep, e.getY() - yStep);
        }
        this.repaint();
      } else {
        if (this.selectedShape != null && this.initialHit) {
          this.selectedShape.setShapeInfo(e.getX() - this.selectedShape.getShapeInfo().get(2), e.getY() - this.selectedShape.getShapeInfo().get(3));
          this.repaint();
        }
      }
      break;
    }
    case 1: {
      break;
    }
    case 2: {
      if (!this.newShapePending) {
        this.shapeList.remove(this.shapeList.size() - 1);
      } else {
        this.newShapePending = false;
      }
      int curX = e.getX();
      int curY = e.getY();
      this.shapeList.add(new Line(this.dataPayload, this.mouseStartX, this.mouseStartY, curX, curY));
      this.repaint();
      break;
    }
    case 3: {
      if (!this.newShapePending) {
        this.shapeList.remove(this.shapeList.size() - 1);
      } else {
        this.newShapePending = false;
      }
      int curX = e.getX();
      int curY = e.getY();
      int d = Math.max(Math.abs(curX - this.mouseStartX), Math.abs(curY - this.mouseStartY));
      int cornerX = this.mouseStartX;
      int cornerY = this.mouseStartY;
      if (curX < mouseStartX) {
        cornerX = mouseStartX - d;
      }
      if (curY < mouseStartY) {
        cornerY = mouseStartY - d;
      }
      this.shapeList.add(new Circle(this.dataPayload, cornerX, cornerY, d));
      this.repaint();
      break;
    }
    case 4: {
      if (!this.newShapePending) {
        this.shapeList.remove(this.shapeList.size() - 1);
      } else {
        this.newShapePending = false;
      }
      int curX = e.getX();
      int curY = e.getY();
      int w = Math.abs(curX - this.mouseStartX);
      int h = Math.abs(curY - this.mouseStartY);
      int cornerX = this.mouseStartX;
      int cornerY = this.mouseStartY;
      if (curX < mouseStartX) {
        cornerX = mouseStartX - w;
      }
      if (curY < mouseStartY) {
        cornerY = mouseStartY - h;
      }
      shapeList.add(new Rectangle(this.dataPayload, cornerX, cornerY, w, h));
      this.repaint();
      break;
    }
    case 5: {
      break;
    }
    default:
      break;
    }
  }

  private void handleMouseUp(MouseEvent e) {
    switch (this.dataPayload.getToolIndex()) {
    case 0: {
      break;
    }
    case 1: {
      int curX = e.getX();
      int curY = e.getY();
      if (this.selectedShape != null && this.selectedShape.hitTest(curX, curY)) {
        this.shapeList.remove(this.selectedShape);
        this.selectedShape = null;
        this.repaint();
      } else if (this.selectedShape != null && !this.selectedShape.hitTest(curX, curY)) {
        this.selectedShape = null;
      }
      break;
    }
    case 2: {
      break;
    }
    case 3: {
      break;
    }
    case 4: {
      break;
    }
    case 5: {
      int curX = e.getX();
      int curY = e.getY();
      if (this.selectedShape != null && this.selectedShape.hitTest(curX, curY)) {
        Payload colorInfo = this.selectedShape.getDataPayload(this.dataPayload);
        this.selectedShape.setColorAndThickness(this.dataPayload.getColor(), this.dataPayload.getColorIndex(),
            this.dataPayload.isFromChooser(), colorInfo.getLineThickness());
        this.selectedShape = null;
        this.repaint();
      } else if (this.selectedShape != null && !this.selectedShape.hitTest(curX, curY)) {
        this.selectedShape = null;
      }
      break;
    }
    default:
      break;
    }
  }

  private void handleKeyDown(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      for (Shape shape : this.shapeList) {
        shape.setSelected(false);
      }
      for (CanvasObserver ob: this.observers) {
        ob.groupSelectActivate(false);
      }
      this.selectedShape = null;
      this.groupSelectedShape.clear();
      this.groupHit = false;
      this.initialHit = false;
      this.repaint();
    }
    if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
      this.ctrlPressed = true;
    }
  }

  private void handleKeyUp(KeyEvent e) {
    // System.out.println("Key released: " + e.getKeyCode());
    if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
      this.ctrlPressed = false;
    }
  }

  public void addObserver(CanvasObserver ob) {
    this.observers.add(ob);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    for (int i = 0; i < shapeList.size(); ++i) {
      for (Shape shape : shapeList) {
        shape.drawShape((Graphics2D) g);
      }
    }
  }

  public void toolBarChanged(Payload p) {
    this.dataPayload = p;
    if (this.selectedShape != null) {
      this.selectedShape.setColorAndThickness(p.getColor(), p.getColorIndex(), p.isFromChooser(), p.getLineThickness());
      this.repaint();
    }
    // System.out.println("\nNew payload");
    // System.out.println("Tool: " + this.dataPayload.getToolIndex());
    // System.out.println("Color: " + this.dataPayload.getColor().toString());
    // System.out.println("Color Index: " + this.dataPayload.getColorIndex());
    // System.out.println("FromChooser: " + this.dataPayload.isFromChooser());
    // System.out.println("Line: " + this.dataPayload.getLineThickness() + "\n");
  }

  public void clearAll() {
    this.shapeList.clear();
    this.selectedShape = null;
    this.groupSelectedShape.clear();
    this.initialHit = false;
    this.groupHit = false;
    this.mouseStartX = -1;
    this.mouseStartY = -1;
    this.newShapePending = false;
    this.repaint();
  }

  /**
   * Using logic from https://www.codejava.net/java-se/swing/
   */
  public void saveToFile() throws IOException {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Specify a file to save");
    fileChooser.addChoosableFileFilter(new FileFilter() {
      public String getDescription() {
        return "JSketch files (*.jsketch)";
      }

      public boolean accept(File f) {
        if (f.isDirectory()) {
          return true;
        } else {
          return f.getName().toLowerCase().endsWith(".jsketch");
        }
      }
    });
    int userSelection = fileChooser.showSaveDialog(this.rootFrame);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
      File fileToSave = fileChooser.getSelectedFile();
      List<String> lines = new ArrayList<String>();
      for (Shape shape : this.shapeList) {
        lines.add(shape.genSaveFileString());
      }
      Path file = new File(fileToSave.getAbsolutePath() + ".jsketch").toPath();
      Files.write(file, lines, StandardCharsets.UTF_8);
    }
  }

  /**
   * Using logic from https://www.codejava.net/java-se/swing/
   */
  public void loadFromFile() throws IOException {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Specify a file to import");
    fileChooser.addChoosableFileFilter(new FileFilter() {
      public String getDescription() {
        return "JSketch files (*.jsketch)";
      }

      public boolean accept(File f) {
        if (f.isDirectory()) {
          return true;
        } else {
          return f.getName().toLowerCase().endsWith(".jsketch");
        }
      }
    });
    int userSelection = fileChooser.showOpenDialog(this.rootFrame);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
      this.clearAll();
      File fileToImport = fileChooser.getSelectedFile();
      // System.out.println(fileToImport.getAbsolutePath());
      List<String> lines = new ArrayList<String>();
      Path file = fileToImport.toPath();
      lines = Files.readAllLines(file);
      for (String line : lines) {
        // System.out.println(line);
        List<String> shapeInfoList = Arrays.asList(line.split(","));
        switch (shapeInfoList.get(0)) {
        case "Circle": {
          int x = Integer.parseInt(shapeInfoList.get(1));
          int y = Integer.parseInt(shapeInfoList.get(2));
          int d = Integer.parseInt(shapeInfoList.get(3));
          int r = Integer.parseInt(shapeInfoList.get(4));
          int g = Integer.parseInt(shapeInfoList.get(5));
          int b = Integer.parseInt(shapeInfoList.get(6));
          int cIdx = Integer.parseInt(shapeInfoList.get(7));
          boolean fromChooser = Boolean.parseBoolean(shapeInfoList.get(8));
          int l = Integer.parseInt(shapeInfoList.get(9));
          this.shapeList.add(new Circle(new Payload(new Color(r, g, b), cIdx, l, 0, fromChooser), x, y, d));
          break;
        }
        case "Line": {
          int x = Integer.parseInt(shapeInfoList.get(1));
          int y = Integer.parseInt(shapeInfoList.get(2));
          int endX = Integer.parseInt(shapeInfoList.get(3));
          int endY = Integer.parseInt(shapeInfoList.get(4));
          int r = Integer.parseInt(shapeInfoList.get(5));
          int g = Integer.parseInt(shapeInfoList.get(6));
          int b = Integer.parseInt(shapeInfoList.get(7));
          int cIdx = Integer.parseInt(shapeInfoList.get(8));
          boolean fromChooser = Boolean.parseBoolean(shapeInfoList.get(9));
          int l = Integer.parseInt(shapeInfoList.get(10));
          this.shapeList.add(new Line(new Payload(new Color(r, g, b), cIdx, l, 0, fromChooser), x, y, endX, endY));
          break;
        }
        case "Rectangle": {
          int x = Integer.parseInt(shapeInfoList.get(1));
          int y = Integer.parseInt(shapeInfoList.get(2));
          int w = Integer.parseInt(shapeInfoList.get(3));
          int h = Integer.parseInt(shapeInfoList.get(4));
          int r = Integer.parseInt(shapeInfoList.get(5));
          int g = Integer.parseInt(shapeInfoList.get(6));
          int b = Integer.parseInt(shapeInfoList.get(7));
          int cIdx = Integer.parseInt(shapeInfoList.get(8));
          boolean fromChooser = Boolean.parseBoolean(shapeInfoList.get(9));
          int l = Integer.parseInt(shapeInfoList.get(10));
          this.shapeList.add(new Rectangle(new Payload(new Color(r, g, b), cIdx, l, 0, fromChooser), x, y, w, h));
          break;
        }
        default:
          break;
        }
      }
      this.repaint();
    }
  }
}