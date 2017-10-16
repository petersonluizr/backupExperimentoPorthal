/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.porthal.experimento.resources;

import br.com.porthal.experimento.ejb.ClienteSession;
import br.com.porthal.experimento.ejb.NotaFiscalSession;
import br.com.porthal.experimento.ejb.PlanoSession;
import br.com.porthal.experimento.entity.Cliente;
import br.com.porthal.experimento.entity.NotaFiscal;
import br.com.porthal.experimento.entity.PlanoConta;
import br.com.porthal.experimento.entity.Produto;
import br.com.porthal.experimento.entity.Retorno;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public PlanoContaResourceTestLuciano() {
    }

    @Test
    public void testGet() {
        List<NotaFiscal> lista = new ArrayList<>();
        List<Produto> listaProduto = new ArrayList<>();
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("jão");
        NotaFiscal nota = new NotaFiscal(1, Long.parseLong("1"), new Date(), BigDecimal.valueOf(40), BigDecimal.valueOf(60), BigDecimal.valueOf(20), cliente, listaProduto, new PlanoConta());
        NotaFiscal nota1 = new NotaFiscal(2, Long.parseLong("2"), new Date(), BigDecimal.valueOf(195), BigDecimal.valueOf(200), BigDecimal.valueOf(5), cliente, listaProduto, new PlanoConta());
        NotaFiscal nota2 = new NotaFiscal(3, Long.parseLong("3"), new Date(), BigDecimal.valueOf(1000), BigDecimal.valueOf(1500), BigDecimal.valueOf(500), cliente, listaProduto, new PlanoConta());

        lista.add(nota);
        lista.add(nota1);
        lista.add(nota2);

        Mockito.when(clienteSession.getById(1)).thenReturn(cliente);
        Mockito.when(notaFiscalSession.consultarPorCliente(cliente)).thenReturn(lista);
        Mockito.when(notaFiscalSession.somarNotas(lista)).thenReturn(BigDecimal.valueOf(1760));
        Retorno resposta = target("planoconta/consultarsomanf").queryParam("id", "1").request().get(Retorno.class);
        System.out.println(resposta.getDescricao());
        Assert.assertEquals("Valor da soma de NFs: " + "1760", resposta.getDescricao());
    }

    @Test
    public void testGet2() {

        Retorno resposta = target("planoconta/consultarsomanf").queryParam("id", "122").request().get(Retorno.class);
        System.out.println(target().toString());
        Assert.assertEquals("O cliente com o id especificado não existe no sistema!", resposta.getDescricao());
    }

    @Test
    public void testGet3() {

        List<NotaFiscal> lista = new ArrayList<>();
        Cliente cliente = new Cliente();
        cliente.setId(2);
        cliente.setNome("jão");
        
        Mockito.when(clienteSession.getById(2)).thenReturn(cliente);
        Mockito.when(notaFiscalSession.consultarPorCliente(cliente)).thenReturn(null);
        Retorno resposta = target("planoconta/consultarsomanf").queryParam("id", "2").request().get(Retorno.class);
        System.out.println(resposta.getDescricao());
        Assert.assertEquals("O cliente com o id especificado não possui notas fiscais.", resposta.getDescricao());
    }

}
