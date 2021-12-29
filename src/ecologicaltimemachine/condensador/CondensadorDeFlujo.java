package ecologicaltimemachine.condensador;

import java.util.Iterator;
import java.util.LinkedList;

public class CondensadorDeFlujo {

    private LinkedList<Lampara> lamparas;
    private float potenciaMinimaParaFuncionar;
    
    //Constructor
    public CondensadorDeFlujo(float potenciaMinimaParaFuncionar) {
        this.lamparas = new LinkedList<>();
        this.potenciaMinimaParaFuncionar = potenciaMinimaParaFuncionar;
    }

    /* Metodos Getters and Setters */
    public float getPotenciaMinimaParaFuncionar() {
        return potenciaMinimaParaFuncionar;
    }

    public void setPotenciaMinimaParaFuncionar(float potenciaMinimaParaFuncionar) {
        this.potenciaMinimaParaFuncionar = potenciaMinimaParaFuncionar;
    }

    public LinkedList<Lampara> getLamparas() {
        return lamparas;
    }

    public void setLamparas(LinkedList<Lampara> lamparas) {
        this.lamparas = lamparas;
    }

    /* Metodos Generales */
    public boolean addLampara(Lampara lamp) {
        boolean res = false;
        if (lamparas.size() < 3) {
            lamparas.add(lamp);
            res = true;
        }
        return res;
    }

    public boolean estaFuncional() {
        boolean res = false;
        float sum = 0;
        int cont = 0;
        Iterator<Lampara> it = lamparas.iterator();
        while (it.hasNext()) {
            Lampara lamAux = it.next();
            if (lamAux.isEstado() != false) {
                sum += lamAux.getPotencia();
                cont += 1;
            }
        }

        if (sum >= this.getPotenciaMinimaParaFuncionar() && cont == 3) {
            res = true;
        }

        return res;
    }

    public boolean cambiarLamparaQuemada(Lampara nuevaLamp) {
        boolean res = false;
        Iterator<Lampara> it = lamparas.iterator();
        while (it.hasNext()) {
            Lampara lamAux = it.next();
            if (lamAux.isEstado() == false) {
                lamparas.remove(lamAux);
                res = lamparas.add(nuevaLamp);
                
                break;
            }
        }
        return res;
    }
    


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("\nPotencia Minima Para Funcionar: ").append(potenciaMinimaParaFuncionar).append(" Watts");
        sb.append("CondensadorDeFlujo: \nLamparas: ");
        for (Lampara lampara : lamparas) {
            sb.append("\n"+lampara.toString());
        }
        sb.append("\n----------------------");
        return sb.toString();
    }

}
