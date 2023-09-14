package com.serratec.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.serratec.classes.Pedido;
import com.serratec.classes.Produto;
import com.serratec.classes.Prod_Pedido;
import com.serratec.classes.Prod_Pedido.Itens;
import com.serratec.conexao.Conexao;

public class Prod_PedidoDAO {

	private Conexao conexao;
	private String schema;

	PreparedStatement pInclusao;
	PreparedStatement pAlteracao;
	PreparedStatement pExclusao;

	public Prod_PedidoDAO(Conexao conexao, String schema) {
		this.conexao = conexao;
		this.schema = schema;
		prepararSqlInclusao();
		prepararSqlAlteracao();
		prepararSqlExclusao();
	}

	private void prepararSqlExclusao() {
		String sql = "delete from " + this.schema + ".produto_pedido";
		sql += " where idprod_pedido = ?";

		try {
			this.pExclusao = conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	private void prepararSqlInclusao() {
		String sql = "insert into " + this.schema + ".produto_pedido";
		sql += " (idproduto, quantidade, codigo)";
		sql += " values ";
		sql += " (?, ?, ?)";

		try {
			this.pInclusao = conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	private void prepararSqlAlteracao() {
		String sql = "update " + this.schema + ".produto_pedido";
		sql += " set idproduto = ?,";
		sql += " quantidade = ?,";
		sql += " codigo = ?";
		sql += " where idprod_pedido = ?";

		try {
			this.pAlteracao = conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	public int alterarProd_Pedido(Prod_Pedido prod_pedido, int index, Produto prod, Pedido ped) {
		try {
			pAlteracao.setLong(1, prod.getIdProduto());
			pAlteracao.setInt(2, Prod_Pedido.getProdutosped().get(index).getQuantidade());
			pAlteracao.setLong(3, ped.getCdPedido());
			pAlteracao.setLong(4, prod_pedido.getIdProdPedido());

			return pAlteracao.executeUpdate();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nLista não alterada.\nVerifique se foi chamada a conexão:\n" + e);
			} else {
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}

	public int incluirProd_Pedido(List<Itens> prod, Pedido ped) {
		try {
			int salvo = 0;
			
			
			for (Produto produto : prod) {
				
			pInclusao.setLong(1, produto.getIdProduto());
			pInclusao.setInt(2, prod.get(prod.lastIndexOf(produto)).getQuantidade());
			pInclusao.setLong(3, ped.getCdPedido());
			
			salvo = pInclusao.executeUpdate();
			}
			prod.clear();
			return salvo;
			
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nLista não incluída.\nVerifique se foi chamada a conexão:\n" + e);
			} else {
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}

	public int excluirProd_Pedido(Prod_Pedido prod_pedido) {
		try {
			pExclusao.setLong(1, prod_pedido.getIdProdPedido());

			return pExclusao.executeUpdate();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nLista não excluída.\nVerifique se foi chamada a conexão:\n" + e);
			} else {
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}

	public ResultSet carregarProd_Pedido() {
		ResultSet tabela;
		String sql = "select * from " + this.schema + ".produto_pedido order by idprod_pedido";

		tabela = conexao.query(sql);

		return tabela;
	}
}
