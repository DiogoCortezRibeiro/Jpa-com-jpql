package br.com.alura.loja.testes;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDAO;
import br.com.alura.loja.dao.PedidoDAO;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.*;
import br.com.alura.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastrarPedido {

    public static void main(String[] args) {
        popularBD();

        EntityManager em      = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);
        Produto produto       = produtoDao.buscarPorId(1l);
        Produto produto2      = produtoDao.buscarPorId(2l);
        ClienteDAO clienteDAO = new ClienteDAO(em);

        em.getTransaction().begin();

        Cliente cliente = clienteDAO.buscarPorId(1l);
        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(10, pedido, produto));

        Pedido pedido2 = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(5, pedido2, produto2));

        PedidoDAO pedidoDAO = new PedidoDAO(em);
        pedidoDAO.cadastrar(pedido);
        pedidoDAO.cadastrar(pedido2);

        em.getTransaction().commit();

        BigDecimal totalVendido = pedidoDAO.valorTotalVendido();
        System.out.println("VALOR TOTAL: " + totalVendido);

        List<RelatorioDeVendasVo> produtos = pedidoDAO.relatorioDeVendas();

        for(RelatorioDeVendasVo p : produtos)
        {
            System.out.println("Nome do produto: " + p.getNome());
            System.out.println("Qtde do produto: " + p.getQuantidade());
            System.out.println("Ultima compra  : " + p.getData());
            System.out.println(" --------------------- ");
        }

        em.close();
    }

    private static void popularBD() {
        Categoria celulares = new Categoria("CELULARES");
        Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares );
        Categoria videogame = new Categoria("VIDEOGAME");
        Produto game = new Produto("PlayStation - 5", "Muito legal", new BigDecimal("5400"), videogame );
        Categoria informatica = new Categoria("INFORMATICA");
        Produto macbook = new Produto("Macbook", "Muito legal", new BigDecimal("3600"), informatica );
        Cliente cliente = new Cliente("Diogo Cortez", "123456");

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);
        ClienteDAO clienteDAO = new ClienteDAO(em);

        em.getTransaction().begin();

        categoriaDao.cadastrar(celulares);
        categoriaDao.cadastrar(videogame);
        categoriaDao.cadastrar(informatica);

        produtoDao.cadastrar(celular);
        produtoDao.cadastrar(game);
        produtoDao.cadastrar(macbook);

        clienteDAO.cadastrar(cliente);

        em.getTransaction().commit();
        em.close();
    }
}
