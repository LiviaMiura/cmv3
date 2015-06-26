/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.backingbeans;

import br.faetec.model.dao.MedidaDAO;
import br.faetec.model.dto.MedidaDTO;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *Gera arquivo DadosCMV.xls com dados das medidas
@author Antonio Cassiano
 **/
public class ExcelBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();
    String arquivo = session.getServletContext().getRealPath("/") + "arquivo/DadosCMV.xls";

    /**
     *Cria a planilha excel com dados das medidas
    @author Antonio Cassiano     
     **/
    public void criarPlanilha() {

        MedidaDAO medidaDAO = new MedidaDAO();
        List lista = null;
        lista = medidaDAO.listar();

        FileOutputStream fileOut = null;

        HSSFWorkbook wb = new HSSFWorkbook();
        try {
            fileOut = new FileOutputStream(arquivo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        HSSFSheet planilha = wb.createSheet();

        Iterator itens = lista.iterator();
        MedidaDTO medidaDTO = null;

        // cabeçalho da planilha
        HSSFRow linha = planilha.createRow((short) 0);
        linha.createCell((short) 1).setCellValue("Medida");
        linha.createCell((short) 2).setCellValue("Resposta");
        linha.createCell((short) 3).setCellValue("UTC");
        linha.createCell((short) 4).setCellValue("Mensagem");
        linha.createCell((short) 5).setCellValue("Total");
        linha.createCell((short) 6).setCellValue("Intervalo");
        linha.createCell((short) 7).setCellValue("Frequencia");



        // Detalhe da planilha
        int contadorLinha = 1;
        while (itens.hasNext()) {
            linha = planilha.createRow((short) contadorLinha++);
            medidaDTO = (MedidaDTO) itens.next();
            linha.createCell((short) 1).setCellValue(medidaDTO.getIdMedida());
            linha.createCell((short) 2).setCellValue(medidaDTO.getRespostaId().getIdResposta());
            linha.createCell((short) 3).setCellValue(medidaDTO.getUtc());
            linha.createCell((short) 4).setCellValue(medidaDTO.getMensagem());
            linha.createCell((short) 5).setCellValue(medidaDTO.getTotalizador());
            linha.createCell((short) 6).setCellValue(medidaDTO.getIntervalo());
            linha.createCell((short) 7).setCellValue(medidaDTO.getFrequencia());

        }
        try {
            wb.write(fileOut);
        } catch (IOException ex) {
            Logger.getLogger(ExcelBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fileOut.close();
        } catch (IOException ex) {
            Logger.getLogger(ExcelBean.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
