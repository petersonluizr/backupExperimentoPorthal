/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.porthal.experimento.resources;

import br.com.porthal.experimento.ejb.NotaFiscalSession;
import br.com.porthal.experimento.ejb.PlanoSession;
import br.com.porthal.experimento.entity.Retorno;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author douglas
 */
public class PlanoContaResourceTestLuciano extends JerseyTest {

    @Mock
    private PlanoSession planoSession;
    @Mock
    private NotaFiscalSession notaFiscalSession;

    @Override
    public ResourceConfig configure() {
        MockitoAnnotations.initMocks(this);
        return new ResourceConfig()
                .register(PlanoContaResource.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(planoSession).to(PlanoSession.class);
                        bind(notaFiscalSession).to(NotaFiscalSession.class);
                    }
                });
    }

    public PlanoContaResourceTestLuciano() {
    }

    @Test
    public void testGet() {

        Retorno resposta = target("planoconta/consultarsomanf").queryParam("id", "1").request().get(Retorno.class);
        System.out.println(target().toString());
        Assert.assertEquals("Valor da soma de NFs: " + "285.0", resposta.getDescricao());
    }
    
    @Test
    public void testGet2() {

        Retorno resposta = target("planoconta/consultarsomanf").queryParam("id", "122").request().get(Retorno.class);
        System.out.println(target().toString());
        Assert.assertEquals("O cliente com o id especificado não existe no sistema!", resposta.getDescricao());
    }

    @Test
    public void testGet3() {

        Retorno resposta = target("planoconta/consultarsomanf").queryParam("id", "2").request().get(Retorno.class);
        System.out.println(target().toString());
        Assert.assertEquals("O cliente com o id especificado não possui notas fiscais.", resposta.getDescricao());
    }

    
}
