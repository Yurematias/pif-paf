package jogodecartas;

public class Principal {
    public static void main(String[] args) {        
        Jogo jogo = new Jogo();
        jogo.iniciarJogo();
        jogo.distribuirCartas(9);
        jogo.mostrarCartas();
        jogo.executar();
    }
}
