
package control;

import java.util.List;
import model.Image;
import view.ImageDisplay;


public class NextImageCommand implements Command {
    
    private final List<Image> images;
    private final ImageDisplay imageDisplay;
    
    public NextImageCommand(List<Image> images, ImageDisplay imageDisplay) {
        this.images = images;
        this.imageDisplay = imageDisplay;
    }
    
    @Override
    public void execute() {
        imageDisplay.display(next());
    }

    private Image next() {
        int index = images.indexOf(imageDisplay.image());
        return images.get((index+1) % images.size());
    } 
    
}
