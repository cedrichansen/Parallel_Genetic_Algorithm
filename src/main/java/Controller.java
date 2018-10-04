import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {

    public void startAlgorithm(ActionEvent e) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(new GeneticAlgorithm());
    }

}
