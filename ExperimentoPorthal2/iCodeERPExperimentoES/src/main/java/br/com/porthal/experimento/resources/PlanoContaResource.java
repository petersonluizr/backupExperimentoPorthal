/*
 */
package br.com.porthal.experimento.resources;

import br.com.porthal.experimento.ejb.NotaFiscalSession;
import br.com.porthal.experimento.ejb.PlanoSession;
import br.com.porthal.experimento.entity.NotaFiscal;
import br.com.porthal.experimento.entity.Retorno;
import br.com.porthal.experimento.parse.Parser;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 */
@Path("/planoconta")
public class PlanoContaResource {

    @Inject
    private PlanoSession planoSession;
    
    @Inject
    private NotaFiscalSession notaSession;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_XML)
    public String get() {
        //return Parser.getConfigFinanceiro(planoSession.getConfiguracao);
        return null;
    }

    @POST
    @Path("/")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_XML)
    public Retorno post(String txt) {
        Retorno retorno = new Retorno();
        try {
            ArrayList<NotaFiscal> nfs = Parser.getNFs(txt);
            this.getNotaSession().importar(nfs);
            retorno.setSucesso(true);
            retorno.setDescricao("Plano importado com sucesso");
        } catch (Exception ex) {//implementar tratamento
            retorno.setSucesso(false);
            retorno.setDescricao("Ocorreu um erro ao importar: " + ex.getMessage());
        }
        return retorno;
    }

    @GET
    @Path("/consultarnotasfiscais")
    @Produces(MediaType.APPLICATION_XML)
    public Retorno consultarNotasFicais(@QueryParam("id") String idPlanoConta) {
        Retorno retorno = new Retorno();
        List<NotaFiscal> listaNotas;
        try {
            listaNotas = this.planoSession.consultarNotasFiscais(Integer.parseInt(idPlanoConta));
            if (listaNotas != null) {
                retorno.setSucesso(true);
                retorno.setDescricao(Parser.getNotasFiscais(listaNotas));
            } else {
                retorno.setSucesso(false);
                retorno.setDescricao("O plano de conta com o id especificado não existe no sistema.");
            }
        } catch (NumberFormatException e) {
            retorno.setSucesso(false);
            retorno.setDescricao("O parâmetro fornecido não é um número.");
        } catch (Exception ex) {//implementar tratamento
            ex.printStackTrace();
            retorno.setSucesso(false);
            retorno.setDescricao("Ocorreu um erro ao consultar: " + ex.getMessage());
        }
        return retorno;
    }
    
    @GET
    @Path("/consultarsomanf")
    @Produces(MediaType.APPLICATION_XML)
    public Retorno consultarSomaNotasFicais(@QueryParam("id") String clienteId) {
        Retorno retorno = new Retorno();
        List<NotaFiscal> listaNotas;
        BigDecimal totalSoma;
        try {
            listaNotas = this.getPlanoSession().consultarPorCliente(Integer.parseInt(clienteId));

            if (listaNotas != null) {
                retorno.setSucesso(true);
                totalSoma = this.getPlanoSession().somarNotas(listaNotas);
                if (totalSoma != null) {
                    retorno.setDescricao("Valor da soma de NFs: " + totalSoma.toString());
                } else {
                    retorno.setSucesso(false);
                    retorno.setDescricao("Não foi possível calcular o valor das notas fiscais.");
                }
            } else {
                retorno.setSucesso(false);
                retorno.setDescricao("O cliente com o id especificado não existe no sistema ou não possui notas fiscais.");
            }
        } catch (NumberFormatException e) {
            retorno.setSucesso(false);
            retorno.setDescricao("O parâmetro fornecido não é um número.");
        } catch (Exception ex) {//implementar tratamento
            retorno.setSucesso(false);
            retorno.setDescricao("Ocorreu um erro ao consultar: " + ex.getMessage());
        }
        return retorno;
    }

    /**
     * @return the planoSession
     */
    public PlanoSession getPlanoSession() {
        return planoSession;
    }

    /**
     * @param planoSession the planoSession to set
     */
    public void setPlanoSession(PlanoSession planoSession) {
        this.planoSession = planoSession;
    }

    /**
     * @return the notaSession
     */
    public NotaFiscalSession getNotaSession() {
        return notaSession;
    }

    /**
     * @param notaSession the notaSession to set
     */
    public void setNotaSession(NotaFiscalSession notaSession) {
        this.notaSession = notaSession;
    }
}
