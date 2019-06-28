import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;

import java.util.ArrayList;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class ColorPicker extends JPanel implements ChooserButtonObserver, ToolButtonObserver {
  private RadioButtonGroup buttonGroup;
  public Color selectedColor;
  private ArrayList<ColorButtonObserver> colorObservers = new ArrayList<>();
  /**
   * Using sample code
   */
  private class RadioButton extends JButton {
    private boolean selected;
    public RadioButton(Color bg) {
      super("");
      this.setFocusable(false);
      this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
      this.setOpaque(true);
      this.setBackground(bg);
    }

    private void selected(boolean state) {
      this.selected = state;
      if (state) {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
      } else {
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
      }
    }

    public boolean isSelected() {
      return this.selected;
    }
  }
  /**
   * Using sample code
   */
  private class RadioButtonGroup extends JPanel implements ColorButtonObserver {
    public Dimension getPreferredSize() {
      return new Dimension(120, 160);
    }
  
    public Dimension getMaximumSize() {
      return new Dimension(120, 160);
    }
  
    public Dimension getMinimumSize() {
      return new Dimension(120, 160);
    }

    public RadioButtonGroup(int row, int col) {
      super(new GridLayout(row, col));
    }

    public void colorSelected(int index) {
      for (int i = 0; i < this.getComponents().length; i++) {
        if (i == index) {
          ((RadioButton) this.getComponent(i)).selected(true);
        } else {
          ((RadioButton) this.getComponent(i)).selected(false);
        }
      }
    }
  }

  public void chooserSelected(Color c) {
    this.buttonGroup.colorSelected(-1);
  }

  public void toolSelected(int index) {
    if (index == 1) {
      for (int i = 0; i < this.buttonGroup.getComponents().length; i++) {
        ((RadioButton) this.buttonGroup.getComponent(i)).setEnabled(false);
        if (((RadioButton) this.buttonGroup.getComponent(i)).isSelected()) {
          ((RadioButton) this.buttonGroup.getComponent(i)).setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
        }
      }
    } else {
      for (int i = 0; i < this.buttonGroup.getComponents().length; i++) {
        ((RadioButton) this.buttonGroup.getComponent(i)).setEnabled(true);
        if (((RadioButton) this.buttonGroup.getComponent(i)).isSelected()) {
          ((RadioButton) this.buttonGroup.getComponent(i)).setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        }
      }
    }
  }

  public ColorPicker() {
    buttonGroup = new RadioButtonGroup(3, 2);
    this.addObserver(buttonGroup);
    this.setSize(new Dimension(120, 170));
    this.setMaximumSize(new Dimension(120, 170));
    this.setMinimumSize(new Dimension(120, 170));

    RadioButton button0 = new RadioButton(Color.BLUE);
    button0.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (ColorButtonObserver observer : ColorPicker.this.colorObservers) {
          observer.colorSelected(0);
        }
      }
    });
    buttonGroup.add(button0);

    RadioButton button1 = new RadioButton(Color.RED);
    button1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (ColorButtonObserver observer : ColorPicker.this.colorObservers) {
          observer.colorSelected(1);
        }
      }
    });
    buttonGroup.add(button1);

    RadioButton button2 = new RadioButton(Color.ORANGE);
    button2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (ColorButtonObserver observer : ColorPicker.this.colorObservers) {
          observer.colorSelected(2);
        }
      }
    });
    buttonGroup.add(button2);

    RadioButton button3 = new RadioButton(Color.YELLOW);
    button3.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (ColorButtonObserver observer : ColorPicker.this.colorObservers) {
          observer.colorSelected(3);
        }
      }
    });
    buttonGroup.add(button3);

    RadioButton button4 = new RadioButton(Color.GREEN);
    button4.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (ColorButtonObserver observer : ColorPicker.this.colorObservers) {
          observer.colorSelected(4);
        }
      }
    });
    buttonGroup.add(button4);

    RadioButton button5 = new RadioButton(Color.PINK);
    button5.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (ColorButtonObserver observer : ColorPicker.this.colorObservers) {
          observer.colorSelected(5);
        }
      }
    });
    buttonGroup.add(button5);

    buttonGroup.colorSelected(0);

    this.add(buttonGroup);
  }

  public void addObserver(ColorButtonObserver observer) {
    this.colorObservers.add(observer);
  }

  public void setColorSelected(Payload p) {
    if (p.isFromChooser()) {
      this.chooserSelected(p.getColor());
    } else {
      this.buttonGroup.colorSelected(p.getColorIndex());
    }
  }
}