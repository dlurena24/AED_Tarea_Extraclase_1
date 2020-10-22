package Tarea_2.socket;

import java.io.Serializable;

/**
 * Esta clase genera un mensaje que puede ser serializado y enviado a través de la red
 */
public class MensajeUsuario implements Serializable {

    private String puerto, mensaje;

    /**
     * Este constructor permite crear una instancia sin especificar algún parámetro
     */
    public MensajeUsuario() {
    }

    /**
     *
     * @param puerto este parámetro debe ser un String
     * @param mensaje este parámetro debe ser un String
     */
    public MensajeUsuario(String puerto, String mensaje) {
        this.puerto = puerto;
        this.mensaje = mensaje;
    }

    /**
     *
     * @return puerto en String
     */
    public String getPuerto() {
        return puerto;
    }

    /**
     *
     * @param puerto debe ser introducido en String
     */
    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    /**
     *
     * @return mensaje que contiene toda la conversación
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     *
     * @param mensaje Un parámetro de tipo String que sobreescribe el atributo mensaje
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}