package org.example.dao;

import org.example.Conexao;
import org.example.model.OrdemProducao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdemProducaoDAO {
        public void inserirOrdem(OrdemProducao ordemProducao){
            String query = """
                    INSERT INTO OrdemProducao
                    (idProduto,idMaquina,quantidadeProduzir,dataSolicitacao,status)
                    VALUES (?,?,?,?,?)
                    """;

            try(Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(query)){

                stmt.setInt(1, ordemProducao.getIdProduto());
                stmt.setInt(2, ordemProducao.getIdMaquina());
                stmt.setDouble(3, ordemProducao.getQuantidadeProduzir());
                stmt.setDate(4, Date.valueOf(ordemProducao.getDataSolicitacao()));
                stmt.setString(5, ordemProducao.getStatus());
                stmt.executeUpdate();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        public List<OrdemProducao> listarOrdemProducaoPendente(){
            String query = """
                    SELECT id
                          ,idProduto
                          ,idMaquina
                          ,dataSolicitacao
                          ,quantidadeProduzir
                          ,status
                    FROM OrdemProducao
                    WHERE status = 'PENDENTE'
                    """;
            List<OrdemProducao> ordemProducaos = new ArrayList<>();

            try(Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(query)){
                ResultSet rs = stmt.executeQuery();

                while (rs.next()){
                    int id = rs.getInt("id");
                    int idProduto = rs.getInt("idProduto");
                    int idMaquina = rs.getInt("idMaquina");
                    double quantidadeProduzir = rs.getDouble("quantidadeProduzir");
                    Date dataSolicitacao = rs.getDate("dataSolicitacao");
                    String status = rs.getString("status");

                    OrdemProducao ordemProducao = new OrdemProducao(id,idProduto,idMaquina,quantidadeProduzir,dataSolicitacao.toLocalDate(),status);
                    ordemProducaos.add(ordemProducao);
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
            return ordemProducaos;
        }

        public void atualizarStatusOrdem(int idOrdem){
            String query = "UPDATE OrdemProducao SET status = 'EXECUTADA' WHERE id = ?";


            try(Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(query)){

                stmt.setInt(1,idOrdem);
                stmt.executeUpdate();

                System.out.println("Ordem de Produção " + idOrdem + " atualizada com sucesso!!");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

}
