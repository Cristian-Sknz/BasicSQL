package me.skiincraft.sql.model;

public enum ProdutoType {

    Bolo("Bolo", "Um bolo quadrado decorado"), Torta("Torta", "Uma versão redonda do bolo e menor."), Docinho("Docinho", "Docinho para preencher a barriga antes do bolo/torta.");

    private String name;
    private String description;

    ProdutoType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
