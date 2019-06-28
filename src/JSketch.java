import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.SwingUtilities;

import java.lang.Runnable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JSketch {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable(){
      public void run() {
        new JSketchFrame("JSketch");
      }
    });
  }
}

class JSketchFrame extends JFrame {
  private ToolBar toolBar = new ToolBar();
  private Canvas canvas = new Canvas(this);
  // private Menu menu = new Menu();

  public JSketchFrame(String title) {
    super(title);
    JMenuBar menuBar = new JMenuBar();
    JMenu menuItems = new JMenu("File");
    JMenuItem newF = new JMenuItem("New");
    JMenuItem save = new JMenuItem("Save");
    JMenuItem load = new JMenuItem("Load");

    newF.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        canvas.clearAll();
      }
    });

    save.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          canvas.saveToFile();
        } catch(Exception exception) {
          System.out.println(exception.getStackTrace());
        }
      }
    });

    load.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          canvas.loadFromFile();
        } catch(Exception exception) {
          System.out.println(exception.getStackTrace());
        }
      }
    });

    menuItems.add(newF);
    menuItems.add(save);
    menuItems.add(load);
    menuBar.add(menuItems);
    this.setJMenuBar(menuBar);
    this.setContentPane(new ContentLayoutWrapper(toolBar, canvas));
    this.setLocation(50, 50);
    this.setMaximumSize(new Dimension(800, 565));
    this.setMinimumSize(new Dimension(800, 565));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.pack();
    this.setVisible(true);
  }
}

class ContentLayoutWrapper extends JPanel {
  private class LeftRegion extends JPanel {
    public LeftRegion(ToolBar toolBar) {
      this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      this.add(Box.createVerticalStrut(5));
      this.add(toolBar);
      this.add(Box.createVerticalGlue());
    }
  
    public Dimension getPreferredSize() {
      return new Dimension(125, 500);
    }
  }
  
  // private class TopRegion extends JPanel {
  //   public TopRegion(Menu menu) {
  //     this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
  //     this.setBackground(Color.ORANGE);
  //     this.add(new JButton("Top"));
  //   }
  // }
  
  private class CenterRegion extends JPanel {
    public CenterRegion(Canvas canvas) {
      this.add(canvas);
    }
  }

	public ContentLayoutWrapper(ToolBar toolBar, Canvas canvas) {
    this.setLayout(new BorderLayout());
    
    toolBar.addObserver(canvas);
    canvas.addObserver(toolBar);

		// Add the components 
		// this.add(new TopRegion(menu), BorderLayout.NORTH);
		this.add(new CenterRegion(canvas), BorderLayout.CENTER);
    this.add(new LeftRegion(toolBar), BorderLayout.WEST);
    
    toolBar.notifyObservers();
	}
}