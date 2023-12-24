public enum Pecado {
    HEROI(0, "Herói nobre livre de pecados. Enviar aos Campos Elíseos."),
    LUXURIA(1, "Luxúria"),
    GULA(2, "Gula"),
    AVAREZA(3, "Avareza"),
    IRA(4, "Ira"),
    HERESIA(5, "Heresia"),
    VIOLENCIA(6, "Violência"),
    FRAUDE(7, "Fraude"),
    TRAICAO(8, "Traição");

    private final int valor;
    private final String pecado;

    Pecado(int valor, String pecado) {
        this.valor = valor;
        this.pecado = pecado;
    }

    public int getValor() {
        return valor;
    }

    public String getPecado() {
        return pecado;
    }
}
