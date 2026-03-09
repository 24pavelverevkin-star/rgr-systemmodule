import javax.swing.*;
import java.awt.*;
import widgets.Diagram;

public class TestPanel extends JPanel {
    private Diagram diagramSeederQueue; 
    private Diagram diagramTruckQueue;  

    public TestPanel() {
        setLayout(new GridLayout(2, 1, 10, 10));

        diagramSeederQueue = new Diagram();
        diagramSeederQueue.setTitleText("Черга сівалок за зерном");
        diagramSeederQueue.setPainterColor(Color.RED);

        diagramTruckQueue = new Diagram();
        diagramTruckQueue.setTitleText("Черга вантажівок на складі");
        diagramTruckQueue.setPainterColor(Color.BLUE);

        add(diagramSeederQueue);
        add(diagramTruckQueue);
    }

    public Diagram getDiagramSeederQueue() { return diagramSeederQueue; }
    public Diagram getDiagramTruckQueue() { return diagramTruckQueue; }
}