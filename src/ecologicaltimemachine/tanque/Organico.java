package ecologicaltimemachine.tanque;


import java.util.Calendar;
import java.util.Date;


public final class Organico extends CapsulaResiduo {

    private int duracionDias;

    public Organico(String fechaCarga, float peso, int duracionDias) {
        super(fechaCarga, peso);
        this.duracionDias = duracionDias;
    }
    
    // GETTERS Y SETTERS
    public int getDuracionDias() {
        return this.duracionDias;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
    }
    
    // OTROS METODOS
    public boolean estaVencida() {
        boolean res = false;

        Calendar fechaCarga = Calendar.getInstance();
        fechaCarga.setTime(super.getFechaCarga());
        fechaCarga.add(Calendar.DATE, this.getDuracionDias());

        Date fechaVencimiento = fechaCarga.getTime();
        Date fechaActual;
        fechaActual = new Date();
        if (fechaActual.after(fechaVencimiento)) {
            res = true;
            return res;
        }

        return res;

    }

    @Override
    public float cantidadKm() {
        return super.cantidadKm() * 0.05F;
    }

    @Override
    public String toString() {
        return super.toString() + " Dias de duracion: " + this.getDuracionDias();
    }

}
