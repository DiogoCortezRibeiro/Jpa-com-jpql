package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.RelatorioDeVendasVo;

public class PedidoDAO {

    private EntityManager em;

    public PedidoDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Pedido pedido) {
        this.em.persist(pedido);
    }

    public Pedido buscarPorId(Long id) {
        return em.find(Pedido.class, id);
    }

    public List<Pedido> buscarTodos() {
        String jpql = "SELECT p FROM Pedido p";
        return em.createQuery(jpql, Pedido.class).getResultList();
    }

    public BigDecimal valorTotalVendido()
    {
        String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
        return em.createQuery(jpql, BigDecimal.class).getSingleResult();
    }

    public List<RelatorioDeVendasVo> relatorioDeVendas()
    {
        String jpql = "SELECT  new br.com.alura.loja.modelo.RelatorioDeVendasVo(produto.nome,SUM(item.quantidade),MAX(pedido.data)) " +
                "         FROM Pedido pedido" +
                "         JOIN pedido.itensPedido item" +
                "         JOIN item.produto produto " +
                "        GROUP BY produto.nome" +
                "        ORDER BY item.quantidade DESC";

        return em.createQuery(jpql, RelatorioDeVendasVo.class).getResultList();
    }

}