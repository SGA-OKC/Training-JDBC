package org.example.dao;

import org.example.Conexao;
import org.example.model.MateriaPrima;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MateriaPrimaDAO {

    public void inserirMateriaPrima(MateriaPrima materiaPrima){
        String query = "INSERT INTO MateriaPrima(nome,estoque) VALUES (?,?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, materiaPrima.getNome());
            stmt.setDouble(2, materiaPrima.getEstoque());
            stmt.executeUpdate();

            System.out.println("Matéria-Prima cadastrada com sucesso!");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<MateriaPrima> listarMateriaPrima(){
        String query = """
                SELECT nome
                      ,estoque
                FROM MateriaPrima
                """;
        List<MateriaPrima> materiaPrimas = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                String nome = rs.getString("nome");
                double estoque = rs.getDouble("estoque");

                MateriaPrima materiaPrima = new MateriaPrima(nome,estoque);
                materiaPrimas.add(materiaPrima);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return materiaPrimas;
    }

    public boolean buscarMateriaPrimaPorNome(String nome){
        String query = """
                SELECT nome
                FROM MateriaPrima
                WHERE nome = ?
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean buscarMateriaPrimaPorEstoque(int id){
        String query = """
                SELECT id
                FROM MateriaPrima
                WHERE id = ? 
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1,id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return  false;
    }
}
