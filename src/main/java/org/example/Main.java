package org.example;

import org.example.dao.*;
import org.example.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        inicio();
    }
    public static void inicio() {
        boolean sair = false;

        System.out.println("-- Sistema de Controle de Produção Industrial --");
        System.out.println("-----------------------------------------------");
        System.out.println("1 - Cadastrar Setor");
        System.out.println("2 - Cadastrar Máquina");
        System.out.println("3 - Cadastrar Produto");
        System.out.println("4 - Cadastrar Matéria-Prima");
        System.out.println("5 - Criar Ordem de Produção");
        System.out.println("6 - Associar Matérias-Primas à Ordem");
        System.out.println("7 - Executar Produção");
        System.out.println("8 - Consultar Ordens e Estoque");
        System.out.println("0 - Sair");
        System.out.println("Selecione a opção desejada: ");

        int opcao = SC.nextInt();
        SC.nextLine();

        switch (opcao){
            case 1: {
                cadastrarSetor();
                break;
            }
            case 2: {
                cadastrarMaquina();
                break;
            }
            case 3: {
                cadastrarProduto();
                break;
            }
            case 4: {
                cadastrarMateriaPrima();
                break;
            }
            case 5: {
                criarOrdemProducao();
                break;
            }
            case 6: {
                associarMateriaPrimaOrdem();
                break;
            }
            case 7: {
                executarProducao();
                break;
            }
            case 8: {
                consultarOrdensEstoque();
                break;
            }
            case 0: {
                sair = true;
                break;
            }
        }
        if(!sair){
            inicio();
        }
    }

    public static void cadastrarSetor(){

        System.out.println("Digite o nome do Setor: ");
        String nomeSetor = SC.nextLine();

        if (!nomeSetor.isEmpty()){
            Setor setores = new Setor(nomeSetor);

            SetorDAO dao = new SetorDAO();
            boolean setorExiste = dao.buscarSetorPorNome(setores);

            if (!setorExiste){
                dao.inserirSetor(setores);
            }else {
                System.out.println("Setor ja cadastrado!");
            }
        }else {
            System.out.println("Setor não informado!");
        }
    }

    public static void cadastrarMaquina(){

        System.out.println("Digite o nome da Máquina: ");
        String nomeMaquina = SC.nextLine();

        List<Integer> opcoesSetor = new ArrayList<>();
        SetorDAO setorDAO = new SetorDAO();

        List<Setor> setores = setorDAO.listarSetores();

        for(Setor setor : setores){
            System.out.println("\n-----SETORES-----");
            System.out.println("NOME SETOR: " + setor.getNome());
            System.out.println("ID SETOR: " + setor.getId());

            opcoesSetor.add(setor.getId());
        }
        System.out.println("---------------------------");
        System.out.println("Digite o ID do Setor: ");
        int idSetor = SC.nextInt();
        SC.nextLine();

        if (!nomeMaquina.isEmpty() && opcoesSetor.contains(idSetor)){
            Maquina maquina = new Maquina(nomeMaquina,idSetor,"OPERACIONAL");

            MaquinaDAO dao = new MaquinaDAO();
            boolean maquinaExiste = dao.buscarMaquinaPorSetor(maquina);

            if (!maquinaExiste) {
                dao.inserirMaquina(maquina);
            }else {
                System.out.println("Máquina ja cadastrada!");
            }
        }else{
            System.out.println("Máquina e Setor não informado");
        }
    }

    public static void cadastrarProduto(){

        System.out.println("Digite o nome do Produto: ");
        String nomeProduto = SC.nextLine();

        System.out.println("Digite a categoria do Produto: ");
        String categoria = SC.nextLine();

        if (!nomeProduto.isEmpty() && !categoria.isEmpty()){
            Produto produto = new Produto(nomeProduto,categoria);

            ProdutoDAO dao = new ProdutoDAO();
            boolean produtoExiste = dao.buscarProdutoPorNome(produto);

            if (!produtoExiste) {
                dao.inserirProduto(produto);
            }else {
                System.out.println("Produto ja cadastrado!!");
            }
        }else {
            System.out.println("Produto ou Categoria não informado!");
        }
    }

    public static void cadastrarMateriaPrima(){

        System.out.println("Digite o nome da Matéria-Prima");
        String nomeMateriaPrima = SC.nextLine();

        System.out.println("Digite a quantidade inicial do saldo do estoque: ");
        double estoque = SC.nextDouble();

        if (!nomeMateriaPrima.isEmpty() && estoque >= 0){
            MateriaPrima materiaPrima = new MateriaPrima(nomeMateriaPrima,estoque);

            MateriaPrimaDAO materiaPrimaDAO = new MateriaPrimaDAO();
            boolean materiaPrimaExiste = materiaPrimaDAO.buscarMateriaPrimaPorNome(nomeMateriaPrima);

            if (!materiaPrimaExiste){
                materiaPrimaDAO.inserirMateriaPrima(materiaPrima);
            }else {
                System.out.println("Matéria-Prima ja cadastrado!!");
            }
        }else {
            System.out.println("Matéria-Prima ou estoque não informado!");
        }
    }

    //PRECISA VER ESSE ERRO
    public static void criarOrdemProducao(){

        List<Integer> opcoesProduto = new ArrayList<>();
        ProdutoDAO produtoDAO = new ProdutoDAO();
        List<Produto> produtos = produtoDAO.listarProdutos();

        for (Produto produto : produtos){
            System.out.println("\n-----PRODUTOS-----");
            System.out.println("ID: " + produto.getId());
            System.out.println("NOME PRODUTO: " + produto.getNome());
            System.out.println("CATEGORIA: " + produto.getCategoria());
            System.out.println("---------------------------------------");

            opcoesProduto.add(produto.getId());
        }

        System.out.println("Selecione o ID do produto que deseja: ");
        int idProduto = SC.nextInt();
        SC.nextLine();

        if (opcoesProduto.contains(idProduto)){
            List<Integer> opcoesMaquina = new ArrayList<>();
            MaquinaDAO maquinaDAO = new MaquinaDAO();
            List<Maquina> maquinas = maquinaDAO.listarMaquinasOperacional();

            for (Maquina maquina : maquinas){
                System.out.println("\n-----MÁQUINAS-----");
                System.out.println("ID: " + maquina.getId());
                System.out.println("NOME MÁQUINA: " + maquina.getNome());
                System.out.println("ID DO SETOR: " + maquina.getIdSetor());
                System.out.println("STATUS: " + maquina.getStatus());

                opcoesMaquina.add(maquina.getId());
            }

            System.out.println("Digite o ID da máquina que deseja: ");
            int idMaquina = SC.nextInt();
            SC.nextLine();

            System.out.println("Digite a quantidade que deseja: ");
            double quantidadeProduzir = SC.nextDouble();
            SC.nextLine();

            if (opcoesMaquina.contains(idMaquina)){
                OrdemProducaoDAO ordemDAO = new OrdemProducaoDAO();
                OrdemProducao ordemProducao = new OrdemProducao(idProduto,idMaquina,quantidadeProduzir,LocalDate.now(), "PENDENTE");
                try{
                    ordemDAO.inserirOrdem(ordemProducao);
                    maquinaDAO.atualizarStatus(idMaquina,"EM_PRODUCAO");
                } catch (RuntimeException e){
                    e.printStackTrace();
                }
            }else {
                System.out.println("Opção Invalidada");
                criarOrdemProducao();
            }
        }else {
            System.out.println("Opção Invalida");
            criarOrdemProducao();
        }
    }

    public static void associarMateriaPrimaOrdem(){

    }

    public static void executarProducao(){

    }

    public static void consultarOrdensEstoque(){

    }
}