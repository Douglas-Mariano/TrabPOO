package com.serratec.menu;

import com.serratec.ListaClasse.ListaProduto;
import com.serratec.classes.Produto;
import com.serratec.conexao.Connect;
import com.serratec.constantes.Util;
import com.serratec.dml.ProdutoDML;

public class MenuProduto {

	public static ListaProduto produtos = new ListaProduto(Connect.getCon(), Connect.dadosCon.getSchema());

	public static int menu() {

		Util.escrever(Util.LINHAD);
		Util.escrever("Menu Produto");
		Util.escrever(Util.LINHAD);
		Util.escrever("1- Cadastrar");
		Util.escrever("2- Alterar");
		Util.escrever("3- Excluir");
		Util.escrever("4- Listar");
		Util.escrever("5- Voltar");
		Util.escrever("6- Sair");
		Util.escrever(Util.LINHA);

		return Util.validarInteiro("Informe uma opcao: ");
	}

	public static int opcoes(int opcao) {

		switch (opcao) {
		case 1: cadastrar(); break;
		case 2: alterar(); break;
		case 3: excluir(); break;
		case 4: listar(); break;
		case 5:
			int opcaoMenuPrincipal = MenuPrincipal.menuPrincipal();
			return MenuPrincipal.opcoes(opcaoMenuPrincipal);
		case 6:
			Util.escrever("Sistema Finalizado!");
			break;
		default:
			Util.escrever("Opcao inválida");
		}
		return opcao;
	}

	public static void cadastrar() {
		Produto produto = Produto.cadastrarProduto();
		ProdutoDML.gravarProduto(Connect.getCon(), Connect.dadosCon.getSchema(), produto);
		produtos.adicionarProdutoLista(produto);
		
		opcoes(menu());
	}

	public static void alterar() {
		Produto prodAlterar = produtos.localizarProduto(Produto.localizarProduto("Alteração do produto!"));
		
		if (!(prodAlterar == null)) {
			Produto.alterarProduto(prodAlterar);
			ProdutoDML.alterarProduto(Connect.getCon(), Connect.dadosCon.getSchema(), prodAlterar);
		}
		else System.out.println("Produto não encontrado!");
		opcoes(menu());
	}
	
	public static void excluir() {
		Produto prodExcluir = produtos.localizarProduto(Produto.localizarProduto("Exclusão do produto!"));
		
		if(!(prodExcluir == null)) {
			produtos.excluirProduto(prodExcluir);
			ProdutoDML.excluirProduto(Connect.getCon(), Connect.dadosCon.getSchema(), prodExcluir);
		}
		else System.out.println("Produto não encontrado!");
		opcoes(menu());
	}

	public static int listar() {
		ListaProduto.imprimirProdutos();
		return opcoes(menu());
	}
}