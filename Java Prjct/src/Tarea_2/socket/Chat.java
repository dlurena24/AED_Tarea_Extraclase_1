package Tarea_2.socket;

/**
 * Esta clase permite relacionar un número de contacto (puerto) a una conversación
 */
public class Chat {

    int contacto;
    String conve = "";

    public Chat() {
    }

    public int getContacto() {
        return contacto;
    }

    public void setContacto(int contacto) {
        this.contacto = contacto;
    }

    public String getConve() {
        return conve;
    }

    public void setConve(String conve) {
        this.conve = conve;
    }
}