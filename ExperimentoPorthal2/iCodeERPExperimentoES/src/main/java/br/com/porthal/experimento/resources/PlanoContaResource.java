/*
 */
package br.com.porthal.experimento.resources;

import br.com.porthal.experimento.ejb.ClienteSession;
import br.com.porthal.experimento.ejb.NotaFiscalSession;
import br.com.porthal.experimento.ejb.PlanoSession;
import br.com.porthal.experimento.entity.Cliente;
import br.com.porthal.experimento.entity.NotaFiscal;
import br.com.porthal.experimento.entity.Produto;
import br.com.porthal.experimento.entity.Retorno;
import br.com.porthal.experimento.parse.Parser;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
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

    @Inject
    private ClienteSession clienteSession;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_XML)
    public String get() {
        //return Parser.getConfigFinanceiro(planoSession.getConfiguracao);
        return null;
    }

    public boolean verificarCliente(List<NotaFiscal> nfs) {
        for (NotaFiscal notaFiscal : nfs) {
            System.out.println(notaFiscal.getCliente().getId());
            System.out.println(notaFiscal.getPlanoConta().getCliente().getId());
            System.out.println("-----------------");
            if (!notaFiscal.getCliente().getId().equals(notaFiscal.getPlanoConta().getCliente().getId())) {
                return false;
            }
        }
        return true;
    }

    @POST
    @Path("/")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_XML)
    public Retorno post(String txt) {
        Retorno retorno = new Retorno();
        try {
            ArrayList<NotaFiscal> nfs = Parser.getNFs(txt);

            if (verificarCliente(nfs)) {
                this.notaSession.importar(nfs);
                retorno.setSucesso(true);
                retorno.setDescricao("Plano importado com sucesso");
            } else {
                retorno.setSucesso(false);
                retorno.setDescricao("Ocorreu um erro ao importar: Deve haver um único Cliente.");
            }
        } catch (Exception ex) {//implementar tratamento
            retorno.setSucesso(false);
            retorno.setDescricao("Ocorreu um erro ao importar: " + ex.getMessage());
        }
        return retorno;
    }

    @GET
    @Path("/todoscalculos")
    @Produces(MediaType.APPLICATION_XML)
    public Retorno todoscalculos() {

        Retorno retorno = new Retorno();
        Cliente cliente = (Cliente) this.clienteSession.getById(4);
        try {
            ArrayList<NotaFiscal> nfs = (ArrayList<NotaFiscal>) this.notaSession.consultarPorCliente(cliente);
            System.out.println("total de notas: " + nfs.size());

            this.notaSession.importar(nfs);

            String descricao = "Frete: ";
            for (NotaFiscal notaFiscal : nfs) {
                for (Produto produto : notaFiscal.getProdutos()) {
                    descricao = descricao + "," + produto.getValorTotalFrete();
                }
            }
            descricao = descricao + " \n Nota 1, total: " + nfs.get(0).getTotalProdutos() + " Nota 2, total: " + nfs.get(1).getTotalProdutos();
            retorno.setSucesso(true);
            retorno.setDescricao(descricao);

        } catch (Exception ex) {//implementar tratamento
            retorno.setSucesso(false);
            retorno.setDescricao("Ocorreu um erro ao importar: " + ex.getMessage());
        }
        return retorno;
    }

    /**
     * Peterson desenvolveu //VALOR_TOTAL = (VALOR_UNITARIO * QUANTIDADE) -
     * VALOR_DESCONTO Status banco: criei 2 produtos com seus respectivos
     * valores de um cliente novo, Peter, sob o id 4. Realizando o calulo acima
     * a webservice deve retorar o valor de 2.125
     *
     * @param txt
     * @return
     */
    @GET
    @Path("/calcularvalortotalproduto")
    @Produces(MediaType.APPLICATION_XML)
    public Retorno calculoValorTotalProduto() {

        Retorno retorno = new Retorno();
        Cliente cliente = (Cliente) this.clienteSession.getById(4);
        try {
            ArrayList<NotaFiscal> nfs = (ArrayList<NotaFiscal>) this.notaSession.consultarPorCliente(cliente);
            System.out.println("total de notas: " + nfs.size());
            for (NotaFiscal notaFiscal : nfs) {
                notaFiscal = this.notaSession.calculaTotalProdutos(notaFiscal);
                // this.notaSession.calculaTotalProdutos(notaFiscal);
                //this.notaSession.calculaTotalFreteProduto(notaFiscal);
                // this.notaSession.calculaTotalNotaFiscal(notaFiscal);
            }
            this.notaSession.bulkSave(nfs);
            retorno.setSucesso(true);
            retorno.setDescricao("Nota 1, total: " + nfs.get(0).getTotalProdutos() + " Nota 2, total: " + nfs.get(1).getTotalProdutos());
        } catch (Exception ex) {//implementar tratamento
            retorno.setSucesso(false);
            retorno.setDescricao("Ocorreu um erro ao importar: " + ex.getMessage());
        }
        return retorno;
    }

    /**
     * Peterson desenvolveu //TOTAL_FRETE_PROD := ((( total_frete
     * /total_produtos) *valor_total ) / quantidade);/ Status banco: criei 2
     * produtos com seus respectivos valores de um cliente novo, Peter, sob o id
     * 4. Realizando o calulo acima a webservice deve retorar o valor de
     *
     * @param txt
     * @return
     */
    @GET
    @Path("/calcularcustofreteporproduto")
    @Produces(MediaType.APPLICATION_XML)
    public Retorno calcularCustoFretePorProduto() {

        Retorno retorno = new Retorno();
        Cliente cliente = (Cliente) this.clienteSession.getById(4);
        try {
            ArrayList<NotaFiscal> nfs = (ArrayList<NotaFiscal>) this.notaSession.consultarPorCliente(cliente);
            System.out.println("total de notas para calcular frete: " + nfs.size());
            for (NotaFiscal notaFiscal : nfs) {
                //notaFiscal = this.notaSession.calculaTotalProdutos(notaFiscal);
                // this.notaSession.calculaTotalProdutos(notaFiscal);
                notaFiscal = this.notaSession.calculaTotalFreteProduto(notaFiscal);
                // this.notaSession.calculaTotalNotaFiscal(notaFiscal);
            }
            this.notaSession.bulkSave(nfs);
            retorno.setSucesso(true);
            String descricao = "vai ser> ";
            for (NotaFiscal notaFiscal : nfs) {
                for (Produto produto : notaFiscal.getProdutos()) {
                    descricao = descricao + "," + produto.getValorTotalFrete();
                }
            }
            retorno.setDescricao(descricao);
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
            Cliente c = clienteSession.getById(Integer.parseInt(clienteId));
            System.out.println(c);
            if (c != null) {
                listaNotas = this.notaSession.consultarPorCliente(c);
                System.out.println(listaNotas);
                if (listaNotas != null && !listaNotas.isEmpty()) {
                    retorno.setSucesso(true);
                    totalSoma = this.notaSession.somarNotas(listaNotas);
                    if (totalSoma != null) {
                        retorno.setDescricao("Valor da soma de NFs: " + totalSoma.toString());
                    } else {
                        retorno.setSucesso(false);
                        retorno.setDescricao("Não foi possível calcular o valor das notas fiscais.");
                    }
                } else {
                    retorno.setSucesso(false);
                    retorno.setDescricao("O cliente com o id especificado não possui notas fiscais.");
                }
            } else {
                retorno.setSucesso(false);
                retorno.setDescricao("O cliente com o id especificado não existe no sistema!");
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
}
