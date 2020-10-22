package Tarea_2.socket;
import javax.swing.*;
import java.awt.*;

/**
 * Esta clase genera la ventana de la interfaz gráfica de la aplicación adaptada a la resolución de pantalla del usuario
 */
public class GUIWindow extends JFrame {

    public GUIWindow(){
        //Se obtienen las dimensiones de la pantalla
        Toolkit pantalla = Toolkit.getDefaultToolkit();
        Dimension tamano = pantalla.getScreenSize();

        //Se obtienen el largo y el ancho de la pantalla y la ventana generada en función de la resolución
        int altura, largo;
        int alturaPantalla = tamano.height;
        int largoPantalla = tamano.width;

        largo = 32*largoPantalla/100;
        altura = 40*alturaPantalla/60;

        //Coloca la ventana en el punto y con las dimensiones específicadas
        setBounds(largo/6,altura/10,largo,altura);
        //Se impide cambiar el tamaño de la ventana
        setResizable(false);
        //Se define el título que tendrá la ventana
        setTitle("Chat");

        CanvaUsuario miCanva = new CanvaUsuario();
        add(miCanva);

        //Muestra la ventana
        setVisible(true);

    }
}
