package br.faetec.backingbeans;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


/**
 *Calcula  progresso da medida em andamento, usa a variavel global "total" e disponibiliza o resultado
  par atualização do form.
@author Antonio Cassiano
 **/
public class ProgressAction implements Serializable {

    private Integer progress;

    public Integer getProgress() {
        int base = 30;
        int totalFinal = RequisicaoAction.total;


        if (totalFinal==0)
        {
            totalFinal=1;
        }
      
        if (progress == null) {
            progress = 0;
        } else {
      
            progress = progress + (base / totalFinal);
      
            if (progress > 100) {
                progress = 100;
            }
        }
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public void onComplete() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Medida Efetuada.", "Medida Efetuada."));
    }

    public void onCancel() {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Medida Iniciada.", "Medida Iniciada."));

    }

    

}
