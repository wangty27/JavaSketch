import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;

import java.util.ArrayList;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

class LinePicker extends JPanel implements ToolButtonObserver {
  private RadioButtonGroup buttonGroup;
  ArrayList<LineButtonObserver> lineObservers = new ArrayList<>();
  /**
   * Using sample code
   */
  private class RadioButton extends JButton {
    public int thickness;
    private boolean selected;
    public RadioButton(int thickness) {
      super("");
      this.setFocusable(false);
      this.thickness = thickness;
      this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 0));
      this.setOpaque(true);
      this.setBackground(Color.WHITE);
    }

    private void selected(boolean state) {
      this.selected = state;
      if (state) {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
      } else {
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 0));
      }
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;

      g2.setStroke(new BasicStroke(thickness));
      g2.drawLine(9, 11, 109, 11);
    }

    public boolean isSelected() {
      return this.selected;
    }
  }
  /**
   * Using sample code
   */
  private class RadioButtonGroup extends JPanel implements LineButtonObserver {
    public Dimension getPreferredSize() {
      return new Dimension(120, 90);
    }
  
    public Dimension getMaximumSize() {
      return new Dimension(120, 90);
    }
  
    public Dimension getMinimumSize() {
      return new Dimension(120, 90);
    }

    public RadioButtonGroup(int row, int col) {
      super(new GridLayout(row, col));
      this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void lineSelected(int index) {
      for (int i = 0; i < this.getComponents().length; i++) {
        if (i == index) {
          ((RadioButton) this.getComponent(i)).selected(true);
        } else {
          ((RadioButton) this.getComponent(i)).selected(false);
        }
      }
      // System.out.println("line selected: " + index);
    }
  }

  public void toolSelected(int index) {
    if (index == 1 || index == 5) {
      for (int i = 0; i < this.buttonGroup.getComponents().length; i++) {
        ((RadioButton) this.buttonGroup.getComponent(i)).setEnabled(false);
        if (((RadioButton) this.buttonGroup.getComponent(i)).isSelected()) {
          ((RadioButton) this.buttonGroup.getComponent(i)).setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        }
      }
    } else {
      for (int i = 0; i < this.buttonGroup.getComponents().length; i++) {
        ((RadioButton) this.buttonGroup.getComponent(i)).setEnabled(true);
        if (((RadioButton) this.buttonGroup.getComponent(i)).isSelected()) {
          ((RadioButton) this.buttonGroup.getComponent(i)).setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        }
      }
    }
  }

  public LinePicker() {
    buttonGroup = new RadioButtonGroup(4, 1);
    this.lineObservers.add(buttonGroup);
    this.setSize(new Dimension(120, 100));
    this.setMaximumSize(new Dimension(120, 100));
    this.setMinimumSize(new Dimension(120, 100));

    RadioButton button0 = new RadioButton(2);
    button0.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (LineButtonObserver observer : LinePicker.this.lineObservers) {
          observer.lineSelected(0);
        }
      }
    });
    buttonGroup.add(button0);

    RadioButton button1 = new RadioButton(4);
    button1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (LineButtonObserver observer : LinePicker.this.lineObservers) {
          observer.lineSelected(1);
        }
      }
    });
    buttonGroup.add(button1);

    RadioButton button2 = new RadioButton(6);
    button2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (LineButtonObserver observer : LinePicker.this.lineObservers) {
          observer.lineSelected(2);
        }
      }
    });
    buttonGroup.add(button2);

    RadioButton button3 = new RadioButton(10);
    button3.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (LineButtonObserver observer : LinePicker.this.lineObservers) {
          observer.lineSelected(3);
        }
      }
    });
    buttonGroup.add(button3);

    buttonGroup.lineSelected(0);

    this.add(buttonGroup);
  }

  public void addObserver(LineButtonObserver ob) {
    this.lineObservers.add(ob);
  }

  public void setLineSelected(Payload p) {
    int idx = -1;
    switch(p.getLineThickness()) {
      case 2: {
        idx = 0;
        break;
      }
      case 4: {
        idx = 1;
        break;
      }
      case 6: {
        idx = 2;
        break;
      }
      case 10: {
        idx = 3;
        break;
      }
      default: break;
    }
    this.buttonGroup.lineSelected(idx);
  }
}