package org.example;

import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

@Getter
public class Connection {
    private java.sql.Connection conexaoDB;
    private String user = "h2";
    private String psw = "1234";
    private String nameDB = "seminario";
    private String url = "jdbc:h2:tcp://localhost:9092/default";

    public void menu() {
        Statement state;
        try {
            Class.forName("org.h2.Driver");
            conexaoDB = DriverManager.getConnection(url + nameDB, user, psw);
            state = conexaoDB.createStatement();

            while (true) {
                System.out.println ("1 - Banco");
                System.out.println("2 - Verificar arvore");
                System.out.println("3 - Inserir dados");
                System.out.println("0 - Sair");
                Scanner scanner = new Scanner(System.in);
                int scan = scanner.nextInt();
                switch (scan) {
                    case 1:
                        checkConnection(conexaoDB);
                        break;
                    case 2:
                        printOrder(state);
                        break;
                    case 3:
                        Scanner sc = new Scanner(System.in);
                        int hp = sc.nextInt();
                        insertNode(hp);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção Inválida!");
                        break;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkConnection(java.sql.Connection conexaoDB) {
        System.out.println("\n1 - Iniciar Conexão" + "\n2 - Matar conexão");
        Scanner scanner = new Scanner(System.in);
        int scan = scanner.nextInt();
        switch (scan) {
            case 1:
                try {
                    Class.forName("org.h2.Driver");
                    if (conexaoDB != null) {
                        System.out.println("\nConnected on database " + nameDB + "!\n\n");
                    } else {
                        System.out.println("\n\nfalha ao conectar no database " + nameDB + "!");
                    }
                } catch (Exception e) {
                    System.out.println("Erro:" + e);
                }
                break;
            case 2:
                try {
                    conexaoDB.close();
                    System.out.println("Conexão com " + nameDB + " morta!");
                } catch (Exception e) {
                    System.out.println("Erro ao matar conexão:" + e);
                }
                break;
            default:
                System.out.println("Parametro Inválido, tente novamente!");
                break;
        }
    }

    public void printOrder(Statement state) {
        Tree<Comparable<Integer>> tree = new Tree<>();
        try {
            ResultSet resultado = state.executeQuery("select * from dados");
            ArrayList<Integer> dados = new ArrayList<>();
            while (resultado.next()) {
                dados.add(resultado.getInt("node"));
            }
            for (Integer dado : dados) {
                tree.add(dado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("\nEm Ordem: ");
        tree.inOrder(tree.getSource());
        System.out.println("\nPós Ordem: ");
        tree.postOrder(tree.getSource());
        System.out.println("\nPre Ordem: ");
        tree.preOrder(tree.getSource());
        System.out.println("\n ");
    }

    public void insertNode(int node) {
        try {
            Statement state = conexaoDB.createStatement();
            String commandSQL = "INSERT INTO dados(node) VALUES(" + node + ")";
            try {
                state.executeUpdate(commandSQL);
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println("Erro de Node" + " " + e);
        }
    }

    public void insertID(int id) {
        try {
            Statement state = conexaoDB.createStatement();
            String commandSQL = "INSERT INTO dados(id) VALUES(" + id + ")";
            try {
                state.executeUpdate(commandSQL);
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println("Erro de certificado" + " " + e);
        }
    }
}