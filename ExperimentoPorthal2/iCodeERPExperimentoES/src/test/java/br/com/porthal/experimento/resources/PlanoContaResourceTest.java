/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.porthal.experimento.resources;

import br.com.porthal.experimento.ejb.ClienteSession;
import br.com.porthal.experimento.ejb.NotaFiscalSession;
import br.com.porthal.experimento.ejb.PlanoSession;
import br.com.porthal.experimento.entity.Retorno;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author douglas
 */
public class PlanoContaResourceTest extends JerseyTest {

    @Mock
    private PlanoSession planoSession;
    @Mock
    private NotaFiscalSession notaFiscalSession;
        @Mock
    private ClienteSession clienteSession;

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
                        bind(clienteSession).to(ClienteSession.class);
                    }
                });
    }


    public PlanoContaResourceTest() {
    }

    @Test
    public void testPost() {
        String xml = "1;A;0;1;João;0;0;10/10/2017;1;João;229.42;20.0;249.42;{1;azul 25x25;batata;1;20.0;5.29;14.71;20.0;,2;vermelho 25x25;pimentão;10;220.0;5.29;214.71;50.0}\n"
                + "2;S;1;1;João;2;1;10/11/2017;1;João;40.0;20.0;60.0;{3;verde 25x25;morango;1;10.0;0;10.0;20.0;4;,5;rosa 25x25;power ranger;10;40.0;10.0;30.0;50.0}";

        Entity<String> userEntity = Entity.entity(xml, MediaType.TEXT_PLAIN);
        Retorno resposta = target("planoconta").request().post(userEntity, Retorno.class);
        Assert.assertEquals("Plano importado com sucesso", resposta.getDescricao());
    }
}
