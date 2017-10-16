/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.porthal.experimento.parse;

import br.com.porthal.experimento.entity.Cliente;
import br.com.porthal.experimento.entity.ConfiguracaoFinanceiro;
import br.com.porthal.experimento.entity.NotaFiscal;
import br.com.porthal.experimento.entity.PlanoConta;
import br.com.porthal.experimento.entity.Produto;
import br.com.porthal.experimento.enums.TipoConta;
import br.com.porthal.experimento.enums.TipoRegime;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentCollectionConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentMapConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentSortedMapConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentSortedSetConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernateProxyConverter;
import com.thoughtworks.xstream.hibernate.mapper.HibernateMapper;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.collection.internal.PersistentBag;
import org.hibernate.collection.internal.PersistentList;
import org.hibernate.collection.internal.PersistentMap;
import org.hibernate.collection.internal.PersistentSet;

/**
 *
 * @author douglas
 */
public class Parser {

    public static XStream xs;

    static {

        xs = new XStream() {
            @Override
            protected MapperWrapper wrapMapper(final MapperWrapper next) {
                return new HibernateMapper(next);
            }
        };

        xs.registerConverter(new HibernateProxyConverter());
        xs.registerConverter(new HibernatePersistentCollectionConverter(xs.getMapper()));
        xs.registerConverter(new HibernatePersistentMapConverter(xs.getMapper()));
        xs.registerConverter(new HibernatePersistentSortedMapConverter(xs.getMapper()));
        xs.registerConverter(new HibernatePersistentSortedSetConverter(xs.getMapper()));

        xs.alias("Bag", PersistentBag.class);
        xs.alias("Map", PersistentMap.class);
        xs.alias("List", PersistentList.class);
        xs.alias("Set", PersistentSet.class);

        xs.autodetectAnnotations(true);
    }

    public static String getConfigsFinanceiro(List<ConfiguracaoFinanceiro> configs) {
        return xs.toXML(configs);
    }

    public static String getConfigFinanceiro(ConfiguracaoFinanceiro config) {
        return xs.toXML(config);
    }

    public static ConfiguracaoFinanceiro getObject(String xmlConfig) {
        XStream xstream = new XStream(new DomDriver());
        xstream.autodetectAnnotations(true);
        return (ConfiguracaoFinanceiro) xstream.fromXML(xmlConfig);
    }

    /**
     * idConta[0];tipoConta[1];classificaçãoConta[2];clienteIdConta[3];clienteNomeConta[4];tipoRegimeConta[5];
     * numeroNota[6];dataEmissaoNota[7];clienteIdNota[8];clienteNomeNota[9];somaTotalProdutosNota[10];somaTotalFreteNota[11];
     * totalNota[12];
     *
     *
     * @param notaFiscal
     * @return
     */
    public static ArrayList<NotaFiscal> getNFs(String xml) {
        String[] arquivo = xml.split("\n");
        ArrayList<NotaFiscal> notas = new ArrayList<>();
        for (String textoNF : arquivo) {
            String[] nfeAtributos = textoNF.split(";", 14);
            PlanoConta conta = getPlano(nfeAtributos);
            NotaFiscal nota = getNota(nfeAtributos);
            nota.setPlanoConta(conta);
            nota.setProdutos(new ArrayList<Produto>());
            String[] produtos = nfeAtributos[13].replace("{", "").replace("}", "").split(",");
            for (String textoProduto : produtos) {
                String[] produtoAtributos = textoProduto.split(";");
                Produto produto = getProduto(produtoAtributos);
                nota.getProdutos().add(produto);
            }
            notas.add(nota);
        }
        return notas;
    }

    private static PlanoConta getPlano(String[] texto) {
        PlanoConta conta = new PlanoConta();
        conta.setId(getInt(texto[0]));
        conta.setTipoConta(TipoConta.valueOf(texto[1]));
        conta.setClassificação(texto[2]);

        Cliente cliente = new Cliente();
        cliente.setId(getInt(texto[3]));
        cliente.setNome(texto[4]);

        conta.setCliente(cliente);
        conta.setTipoRegime(TipoRegime.values()[getInt(texto[5])]);
        return conta;
    }

    private static NotaFiscal getNota(String[] texto) {
        NotaFiscal nota = new NotaFiscal();

        nota.setNumero(getLong(texto[6]));
        nota.setId(getInt(texto[6]));
        Date data;
        try {
            data = new SimpleDateFormat("dd/MM/yyyy").parse(texto[7]);
            nota.setDataEmissao(data);
        } catch (ParseException ex) {
            data = null;
        }

        Cliente cliente = new Cliente();
        cliente.setId(getInt(texto[8]));
        cliente.setNome(texto[9]);

        nota.setCliente(cliente);
        nota.setTotalProdutos(new BigDecimal(texto[10]));
        nota.setTotalNota(new BigDecimal(texto[12]));
        nota.setTotalFrete(new BigDecimal(texto[11]));
        return nota;
    }

    /**
     *
     * {codigoBarrasProduto1[0];corDimensoesGrupo1[1];descricaoProduto1[2];qtdProduto1[3];
     * valorUnitarioProduto1[4];valoDescontoProduto1[5];valorTotalProduto1[6];
     * totalFreteProduto1[7]}
     *
     * @param texto
     */
    private static Produto getProduto(String[] texto) {
        Produto produto = new Produto();
        produto.setId(getInt(texto[0]));
        produto.setCodigoBarras(texto[0]);
        produto.setCorDimensoes(texto[1]);
        produto.setDescricao(texto[2]);
        produto.setQuantidade(getInt(texto[3]));
        produto.setValorUnitario(new BigDecimal(texto[4]));
        produto.setValorDesconto(new BigDecimal(texto[5]));
        produto.setValorTotal(new BigDecimal(texto[6]));
        produto.setValorTotalFrete(new BigDecimal(texto[7]));
        return produto;
    }

    public static String getNotasFiscais(List<NotaFiscal> notas) {
        return xs.toXML(notas);
    }

    private static Long getLong(String txt) {
        try {
            return Long.parseLong(txt);
        } catch (NumberFormatException ex) {
            return new Long(0);
        }
    }

    private static Integer getInt(String txt) {
        try {
            return Integer.parseInt(txt);
        } catch (NumberFormatException ex) {
            return new Integer(0);
        }
    }
}
