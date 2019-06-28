import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import java.util.ArrayList;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ToolPicker extends JPanel {
  private RadioButtonGroup buttonGroup;
  ArrayList<ToolButtonObserver> toolObservers = new ArrayList<>();

  /**
   * Using sample code
   */
  private class RadioButton extends JButton {
    public RadioButton(String path) {
      super("");
      this.setFocusable(false);
      ImageIcon icon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
      this.setIcon(icon);
      this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
      this.setOpaque(true);
      this.setBackground(Color.WHITE);
    }

    private void selected(boolean state) {
      if (state) {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
      } else {
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
      }
    }
  }
  /**
   * Using sample code
   */
  private class RadioButtonGroup extends JPanel implements ToolButtonObserver {
    private int index = -1;

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

      this.index = 0;
    }

    public void toolSelected(int index) {
      for (int i = 0; i < this.getComponents().length; i++) {
        if (i == index) {
          ((RadioButton) this.getComponent(i)).selected(true);
        } else {
          ((RadioButton) this.getComponent(i)).selected(false);
        }
      }
      this.index = index;
    }
  }

  public ToolPicker() {
    buttonGroup = new RadioButtonGroup(3, 2);
    this.toolObservers.add(buttonGroup);
    this.setSize(new Dimension(120, 170));
    this.setMaximumSize(new Dimension(120, 170));
    this.setMinimumSize(new Dimension(120, 170));

    RadioButton button0 = new RadioButton("src/img/cursor.png");
    button0.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (ToolButtonObserver observer : ToolPicker.this.toolObservers) {
          observer.toolSelected(0);
        }
      }
    });
    buttonGroup.add(button0);

    RadioButton button1 = new RadioButton("src/img/eraser.png");
    button1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (ToolButtonObserver observer : ToolPicker.this.toolObservers) {
          observer.toolSelected(1);
        }
      }
    });
    buttonGroup.add(button1);

    RadioButton button2 = new RadioButton("src/img/line.png");
    button2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (ToolButtonObserver observer : ToolPicker.this.toolObservers) {
          observer.toolSelected(2);
        }
      }
    });
    buttonGroup.add(button2);

    RadioButton button3 = new RadioButton("src/img/circle.png");
    button3.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (ToolButtonObserver observer : ToolPicker.this.toolObservers) {
          observer.toolSelected(3);
        }
      }
    });
    buttonGroup.add(button3);

    RadioButton button4 = new RadioButton("src/img/rect.png");
    button4.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (ToolButtonObserver observer : ToolPicker.this.toolObservers) {
          observer.toolSelected(4);
        }
      }
    });
    buttonGroup.add(button4);

    RadioButton button5 = new RadioButton("src/img/fill.png");
    button5.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (ToolButtonObserver observer : ToolPicker.this.toolObservers) {
          observer.toolSelected(5);
        }
      }
    });
    buttonGroup.add(button5);

    buttonGroup.toolSelected(0);

    this.add(buttonGroup);
  }

  public void addObserver(ToolButtonObserver ob) {
    this.toolObservers.add(ob);
  }
}