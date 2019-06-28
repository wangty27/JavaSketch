import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JColorChooser;

import java.util.ArrayList;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class Chooser extends JPanel implements ColorButtonObserver, ToolButtonObserver {
  public Color selectedColor;
  private ChooserButton chooserButton;
  private ArrayList<ChooserButtonObserver> chooserObservers = new ArrayList<>();

  private class ChooserButton extends JButton {
    public ChooserButton() {
      super("Color Chooser");
      this.setFocusable(false);
      this.setOpaque(true);
      this.setBackground(Color.WHITE);
      this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }
  
    public Dimension getPreferredSize() {
      return new Dimension(120, 35);
    }
  
    public Dimension getMaximumSize() {
      return new Dimension(120, 35);
    }
  
    public Dimension getMinimumSize() {
      return new Dimension(120, 35);
    }
  }

  public Chooser() {
    chooserButton = new ChooserButton();
    JColorChooser colorChooser = new JColorChooser();
    this.setSize(new Dimension(120, 45));
    this.setMaximumSize(new Dimension(120, 45));
    this.setMinimumSize(new Dimension(120, 45));
    chooserButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Chooser.this.selectedColor = colorChooser.showDialog(Chooser.this, "Pick", Chooser.this.selectedColor);
        if (Chooser.this.selectedColor == null) {
          Chooser.this.selectedColor = null;
          Chooser.this.setChooser(Chooser.this.selectedColor);
        } else {
          Chooser.this.setChooser(Chooser.this.selectedColor);
          for(ChooserButtonObserver observer: Chooser.this.chooserObservers) {
            observer.chooserSelected(Chooser.this.selectedColor);
          }
        }
      }
    });
    this.add(chooserButton);
  }

  public void setChooser(Color c) {
    if (c == null) {
      this.selectedColor = null;
      this.chooserButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
      this.chooserButton.setText("Color Chooser");
    } else {
      this.selectedColor = c;
      this.chooserButton.setBorder(BorderFactory.createLineBorder(c, 5));
      this.chooserButton.setText("Color Choosed");
    }
  }

  public void colorSelected(int index) {
    this.selectedColor = null;
    this.chooserButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    chooserButton.setText("Color Chooser");
  }

  public void toolSelected(int index) {
    if (index == 1) {
      this.chooserButton.setEnabled(false);
    } else {
      this.chooserButton.setEnabled(true);
    }
  }

  public void addObserver(ChooserButtonObserver observer) {
    this.chooserObservers.add(observer);
  }

  public void setChooserSelected(Payload p) {
    if (p.isFromChooser()) {
      this.setChooser(p.getColor());
    } else {
      this.setChooser(null);
    }
  }
}