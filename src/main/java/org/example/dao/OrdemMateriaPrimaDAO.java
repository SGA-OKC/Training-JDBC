package org.example.dao;

import org.example.Conexao;
import org.example.model.OrdemMateriaPrima;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdemMateriaPrimaDAO {
    public void inserirOrdemMateria(OrdemMateriaPrima ordemMateriaPrima){
        String query = """
                INSERT INTO OrdemMateriaPrima
                (idOrdem,idMateriPrima, quantidade)
                VALUES (?,?,?)
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1,ordemMateriaPrima.getIdOrdem());
            stmt.setInt(2, ordemMateriaPrima.getIdMateria());
            stmt.setDouble(3, ordemMateriaPrima.getQuantidade());
            stmt.executeUpdate();

            System.out.println("Ordem Materia-Prima concluida com sucesso!");
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public boolean verificaOrdemEMateriaPrima(int idOrdem, int idMateriaPrima){
        String query = """
                    SELECT COUNT(0) AS contagem
                    FROM OrdemMateriaPrima
                    WHERE idOrdem = ?
                    AND idMateriaPrima = ?
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1,idOrdem);
            stmt.setInt(2,idMateriaPrima);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                int contagem = rs.getInt("contagem");
                if(contagem>0){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<OrdemMateriaPrima> buscarOrdemMateriaPrimaPorIdOrdemProducao(int idOrdem){
        List<OrdemMateriaPrima> ordens = new ArrayList<>();
        String query = """
                    SELECT idOrdem
                    	, idMateriaPrima
                    	, quantidade
                    FROM OrdemMateriaPrima
                    WHERE idOrdem = ?
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1,idOrdem);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
            int idOrdemNew = rs.getInt("idOrdem");
            int idMateriaPrima = rs.getInt("idMateriaPrima");
            double quantidade = rs.getDouble("quantidade");

            OrdemMateriaPrima ordem = new OrdemMateriaPrima(idOrdemNew,idMateriaPrima,quantidade);
            ordens.add(ordem);
        }
    }catch (SQLException e){
        e.printStackTrace();
    }return ordens;
    }
}