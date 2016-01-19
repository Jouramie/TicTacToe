package timer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;


/**
 * Cette classe incrémente un timer pour notre application. (secondes)
 * @author J samson
 *   @Version 1.0        
 */
@SuppressWarnings("rawtypes")
public class FXTimer extends Task {
	
    private Timeline timeline;
    private Label timerLabel = new Label();
    public DoubleProperty timeSeconds = new SimpleDoubleProperty();
    private Duration time = Duration.ZERO;

	@Override
	protected Object call() throws Exception {
 // on utilise un timerlabel, fonction de java
		
        timerLabel.textProperty().bind(timeSeconds.asString());
        timerLabel.setTextFill(Color.BROWN);
        timerLabel.setStyle("-fx-font-size: 4em;");
        timeline = new Timeline(new KeyFrame(Duration.millis(100),new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent t) {
             Duration duration = ((KeyFrame)t.getSource()).getTime();
             time = time.add(duration);
             timeSeconds.set(time.toSeconds());
             }
        })
     );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
		return null;
	}

}