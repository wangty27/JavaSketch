## How to run the program
```bash
$ gradle build
$ gradle run
```

## File operations
### Making a new drawing
* Choose new in the File menu
* The current drawing will be cleared


### Saving a drawing
* Choose save in the File menu
* Choose a location and enter a name
* The drawing will be saved to a `.jsketch` file after pressing "save"

### Loading a drawing
* Choose new in the File menu
* Choose a `.jsketch` file to load from
* The current drawing will be replaced with the loaded drawing
* Sample file for loading: `draw.jsketch`

## Tool functions
### Cursor tool
* **Select a single shape**
  * Allows user to select and drag(move) a shape around
  * Selecting a shape brings the shape to the front
  * After selecting a shape, choosing a different color/line thickness will change the shape's properties
  * Selected shape will be outlined with dotted line
  * Pressing "esc" will cancel the selection
* **Select a group of shapes (Additional feature)**
  * Allows user to select and drag(move) a group of shapes
  * Clicking a shape while pressing `ctrl` will add a shape to the group
  * Click and drag on one of the selected shapes to move the group of shapes around (`ctrl` must be released)
  * After group selecting, color/line thickness choosing are disabled
  * Selected shapes will be outlined with dotted line
  * Pressing "esc" will cancel the selections

### Eraser tool
* **Remove a shape**
  * Clicking on a shape will remove the shape
  * Color/line thickness choosing are disabled
  * Click on the inside a shape then drag to the outside of the shape will **NOT** remove the shape

### Line/Circle/Rectangle 
* **Drawing a shape**
  * Click and drag to draw the corresponding shape
  * Releasing the mouse button will confirm the shape
  * The color and border/line thickness will be determined by the selection from the tool bar

### Fill tool
* **Filling a shape with the selected color**
  * Click on a shape to re-color the shape with the current selected color
  * Click on the inside a shape then drag to the outside of the shape will **NOT** re-color the shape

## References
* Icons from: https://www.iconfont.cn/
* Save/Load and JFileChooser logic from: https://www.codejava.net/java-se/swing/

#### Built and tested on MacOS Mojave 10.14.5 