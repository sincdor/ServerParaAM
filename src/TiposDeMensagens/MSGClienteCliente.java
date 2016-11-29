package TiposDeMensagens;

import java.io.Serializable;

/**
 * Created by Andre on 29/11/2016.
 */
public class MSGClienteCliente implements Serializable {
    String remetente;
    String destinatario;
    String mensagem;

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
