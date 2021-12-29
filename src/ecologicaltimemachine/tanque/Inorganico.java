
package ecologicaltimemachine.tanque;



public final class Inorganico extends CapsulaResiduo{
    private int tipo;
       
    public Inorganico(String fechaCarga, float peso, int tipo) {
        super(fechaCarga, peso);
        this.setTipo(tipo);
        
    }
    
    //GETTERS AND SETTERS
    public int getTipo() {
        return tipo;
    }

    public final void setTipo(int tipo) {
        if(tipo == 1 || tipo == 2 || tipo == 3){
            this.tipo = tipo;
        }  
    }
    // OTROS METODOS
    @Override
    public float cantidadKm(){
        float res;
        switch (this.getTipo()) {
            case 1:
                res = super.cantidadKm() * 0.1F;
                break;
            case 2:
                res = super.cantidadKm() * 0.25F;
                break;
            default:
                res = super.cantidadKm() * 0.15F;
                break;
        }
        return res;
    }

    @Override
    public String toString() {
        return super.toString()+ " Tipo: " + this.getTipo();
    }
    
    
    
}
