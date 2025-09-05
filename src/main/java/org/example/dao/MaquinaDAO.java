package org.example.dao;

import org.example.Conexao;
import org.example.model.Maquina;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaquinaDAO {

    public void inserirMaquina(Maquina maquina){
        String query = " INSERT INTO Maquina(nome,idSetor,status) VALUES (?,?,?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, maquina.getNome());
            stmt.setInt(2, maquina.getIdSetor());
            stmt.setString(3, maquina.getStatus());
            stmt.executeUpdate();

            System.out.println("Cadastrado com Sucesso!");

        }catch(SQLException e){
            e.printStackTrace();}
    }

    public List<Maquina> listarMaquinas() {
        String query = """
                SELECT nome
                      ,idSetor
                      ,status
                FROM Maquina
                """;
        List<Maquina> maquinas = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                String nomeMaquina = rs.getString("nome");
                int idSetor = rs.getInt("idSetor");
                String status = rs.getString("status");

                Maquina maquina = new Maquina(nomeMaquina,idSetor,status);
                maquinas.add(maquina);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return maquinas;
    }

    public List<Maquina> listarMaquinasOperacional(){
        String query = """
                SELECT id
                      ,nome
                      ,idSetor
                      ,status
                FROM Maquina
                WHERE status = 'OPERACIONAL'
                """;
        List<Maquina> maquinas = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String nomeMaquina = rs.getString("nome");
                int idSetor = rs.getInt("idSetor");
                String status = rs.getString("status");

                Maquina maquina = new Maquina(id,nomeMaquina,idSetor,status);
                maquinas.add(maquina);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return maquinas;
    }

    public boolean buscarMaquinaPorSetor(Maquina maquina){
        String query = """
                SELECT nome
                FROM Maquina
                WHERE nome = ?
                AND idSetor = ?
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, maquina.getNome());
            stmt.setInt(2, maquina.getIdSetor());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void atualizarStatus(int id, String status){
        String query = """
                UPDATE Maquina 
                SET status = ?
                WHERE id = ?
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1,status);
            stmt.setInt(2,id);
            stmt.executeUpdate();

            System.out.println("Status da Máquina atualizado para " + status + "!");
        }catch (SQLException e){
            System.out.println("Erro ao atualizar o status!!");
            e.printStackTrace();
        }
    }
}
