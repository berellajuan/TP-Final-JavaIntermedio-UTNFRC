package ecologicaltimemachine.condensador;

public final class Lampara implements Comparable<Lampara>{

    private boolean estado;
    private float potencia;

    
    public Lampara(boolean estado, float potencia) {
        this.estado = estado;
        this.potencia = potencia;
    }

    public boolean isEstado() {
        return this.estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public float getPotencia() {
        return this.potencia;
    }

    public void setPotencia(float potencia) {
        this.potencia = potencia;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Estado de la Lampara: ").append(estado);
        sb.append(", Potencia: ").append(potencia).append(" Watts");
        return sb.toString();
    }
    
    // Implementacion de compareTo para la ordenacion de mayor a menor
    @Override
    public int compareTo(Lampara lam) {
        if(this.getPotencia() == lam.getPotencia()){
            return 0;
        }else if(this.getPotencia() > lam.getPotencia() ){
            return -1;
        }else{
            return 1;
        }
    }
   

}
