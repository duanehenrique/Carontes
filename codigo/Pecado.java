public enum Pecado {
    HEROI(0, "Herói nobre livre de pecados. Enviar aos Campos Elíseos."),
    LIMBO(1, "Comportamento Digno do Limbo"),
    LUXURIA(2, "Luxúria"),
    GULA(3, "Gula"),
    AVAREZA(4, "Avareza"),
    IRA(5, "Ira"),
    HERESIA(6, "Heresia"),
    VIOLENCIA(7, "Violência"),
    FRAUDE(8, "Fraude"),
    TRAICAO(9, "Traição");

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
