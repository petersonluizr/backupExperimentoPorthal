/*
 */
package br.com.porthal.experimento.resources;

import br.com.porthal.experimento.ejb.ConfiguracaoFinanceiroSession;
import br.com.porthal.experimento.entity.ConfiguracaoFinanceiro;
import br.com.porthal.experimento.parse.Parser;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 */
@Path("/configuracaofinanceiro")
public class ConfigFinanceiroResource {

    @EJB
    private ConfiguracaoFinanceiroSession configFinanceiroSession;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_XML)
    public String get() {
        return Parser.getConfigFinanceiro(configFinanceiroSession.getConfiguracao());
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public boolean post(ConfiguracaoFinanceiro c) {
        try {
            this.configFinanceiroSession.save(c);
            return true;
        } catch (Exception ex) {//implementar tratamento
            return false;
        }
    }
}
