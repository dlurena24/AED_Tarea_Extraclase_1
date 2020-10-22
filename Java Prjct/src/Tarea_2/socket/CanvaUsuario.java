package Tarea_2.socket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *Esta clase se enccarga de generar los componentes de la clase GUIWindows y los sockets de servidor y cliente de cada usuario
 */

public class CanvaUsuario extends JPanel implements Runnable {

    private JTextArea areaTextoCliente;
    private JTextField campoEscribir, Contacto;
    private JButton boton;
    int puertoInicial = 50000;
    LinkedList Chats = new LinkedList();

    private static Logger log = LoggerFactory.getLogger(CanvaUsuario.class);



    public CanvaUsuario() {

        JLabel texto = new JLabel("Chat --- Introduce el puerto de contacto");
        add(texto);

        //Aquí es necesario introducir el puerto del serverSocket del usuario al que le se desea enviar un mensaje
        Contacto = new JTextField(7);
        add(Contacto);

        //Aquí se muestran las conversaciones
        areaTextoCliente = new JTextArea(24, 35);
        add(areaTextoCliente);

        //Aquí se escriben los mensajes
        campoEscribir = new JTextField(20);
        add(campoEscribir);

        //Se crea un nuevo evento
        TextoEnviado evento = new TextoEnviado();

        //Se ejecuta un evento al presionar este botón
        boton = new JButton("Enviar");
        boton.addActionListener(evento);
        add(boton);

        //Se creó un hilo que permita la continua ejecución del socketServer
        Thread t1 = new Thread(this);
        t1.start();
    }

    /**
     * Esta clase inicia un serverSocket que permite utilizar su puerto como número de contacto
     */
    @Override
    public void run(){
        boolean flag = true;
        //Se crea un bucle que intente iniciar un serverSocket en puertos consecutivos hasta encontrar uno con espacio disponible
        while (flag) {
            try {
                ServerSocket servidor = new ServerSocket(puertoInicial);
                String puertoEmisor, mensaje;
                MensajeUsuario mensajeRecibido;

                //En caso de que el try logre crear el ServerSocket se niega la condición del bucle
                flag = false;

                //Se informa del puerto en el que se alojó el ServerSocket mediante la consola
                System.out.println("===============");
                System.out.println(puertoInicial);
                System.out.println("===============");

                //Se inicia un bucle que acepte las conexiones de otros Sockets y muestre sus mensajes
                while (true) {
                    Socket socket = servidor.accept();
                    ObjectInputStream datos = new ObjectInputStream(socket.getInputStream());
                    mensajeRecibido = (MensajeUsuario) datos.readObject();

                    puertoEmisor = mensajeRecibido.getPuerto();
                    mensaje = mensajeRecibido.getMensaje();

                    conversaciones(Integer.parseInt(puertoEmisor),"De " + puertoEmisor + ": " + mensaje + "\n");

                    socket.close();
                }
            } catch (UnknownHostException ex) {
                ex.printStackTrace();
                System.out.println("UnknownHostException cliente");
                log.error(ex.getMessage(), ex);
            } catch (IOException ex) {
                //ioException.printStackTrace();
                System.out.println("IOException cliente");
                puertoInicial++;
                log.error(ex.getMessage(), ex);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
            } catch (NumberFormatException ex) {
                System.out.println("NumberFormatException cliente");
                log.error(ex.getMessage(), ex);

            }
        }
    }

    /**
     * Este método permite crear un socket de salida para enviar el mensaje al puerto específicado
     */
    private class TextoEnviado implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            //Se guarda el valor del puerto especificado en un int
            int puerto = Integer.parseInt(Contacto.getText());
            try {
                //Se crea un nuevo socket para enviar la información
                Socket nuevoSocket = new Socket("127.0.0.1", puerto);

                //Obtiene los datos que son recibidos de la GUI para enviarlo al contacto espcíficado
                MensajeUsuario datosRecibidos = new MensajeUsuario();
                datosRecibidos.setPuerto(String.valueOf(puertoInicial));
                datosRecibidos.setMensaje(campoEscribir.getText());

                conversaciones(puerto,"Yo: " + campoEscribir.getText() + "\n");

                //Se crea un flujo de datos de salida para enviar los datos recibidos
                ObjectOutputStream informacionDatos = new ObjectOutputStream(nuevoSocket.getOutputStream());
                informacionDatos.writeObject(datosRecibidos);
                nuevoSocket.close();

            } catch (UnknownHostException ex) {
                ex.printStackTrace();
                System.out.println("UnknownHostException cliente");
                log.error(ex.getMessage(), ex);
            } catch (IOException ex) {
                System.out.println("IOException cliente");
                log.error(ex.getMessage(), ex);

            } catch (NumberFormatException ex) {
                System.out.println("NumberFormatException cliente");
                log.error(ex.getMessage(), ex);

            }

        }
    }

    /**
     * Este método se encarga de filtrar los mensajes de acuerdo a la conversación en la que se encuentre el usuario
     * @param contacto Se utiliza para filtrar los mensajes
     * @param texto Contiene los mensajes de la conversación respectiva
     */
    public void conversaciones(int contacto, String texto){
        //Guarda un nuevo contacto en una LinkedList en caso de que no existan
        if(Chats.size() == 0){
            Chat convo = new Chat();
            convo.setContacto(contacto);
            Chats.add(convo);
            convo.conve += texto;
            areaTextoCliente.setText(convo.getConve());
        }
        else{
            boolean aux = false;
            for(int i = 0; i < Chats.size();i++){
                Chat tmp = (Chat) Chats.get(i);
                if(tmp.getContacto() == contacto){
                    tmp.conve += texto;
                    areaTextoCliente.setText(tmp.getConve());
                    aux = true;
                }
            }
            if(!aux){
                Chat convo = new Chat();
                convo.setContacto(contacto);
                Chats.add(convo);
                convo.conve += texto;
                areaTextoCliente.setText(convo.getConve());
            }
        }
    }
}