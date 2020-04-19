package jogodecartas;

public class Carta implements Comparable {

    private final int FACE;
    private final String NAIPE;

    public Carta(int face, String naipe) {
        this.FACE = face;
        this.NAIPE = naipe;
    }

    public int getFace() {
        return this.FACE;
    }

    public String getNaipe() {
        return this.NAIPE;
    }
    
    // toString sobreposto, este faz a conversão do (1, 11, 12, 13) para (A, J, Q, K) respectivamente 
    @Override
    public String toString() {
        // conversão da face que é int para String
        // o objetivo é armazenar o A, J, Q, K
        // um desses valores será exibido caso a face seja 1, 11, 12 ou 13 conforme o switch abaixo  
        String converted = null; 

        switch (FACE) {
            case 1:
                converted = "A";
                break;
            case 11:
                converted = "J";
                break;
            case 12:
                converted = "Q";
                break;
            case 13:
                converted = "K";
        }
        // se converted for nulo, significa que pode mostrar o valor de face normalmente
        // senão significa que precisará converter, assim precisará mostrar o valor convertido
        // que é aquele valor armazenado em converted 
        return (converted == null ? FACE : converted) + " de " + NAIPE;
    }
    
    @Override
    public int compareTo(Object o) {
        Carta c = (Carta) o;
        return this.FACE - c.getFace();
    }

}
