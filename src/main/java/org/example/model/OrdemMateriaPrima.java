package org.example.model;

public class OrdemMateriaPrima {
    private int idOrdem;
    private int idMateria;
    private double quantidade;

    public OrdemMateriaPrima(int idOrdem, int idMateria, double quantidade) {
        this.idOrdem = idOrdem;
        this.idMateria = idMateria;
        this.quantidade = quantidade;
    }

    public int getIdOrdem() {
        return idOrdem;
    }

    public void setIdOrdem(int idOrdem) {
        this.idOrdem = idOrdem;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
}
