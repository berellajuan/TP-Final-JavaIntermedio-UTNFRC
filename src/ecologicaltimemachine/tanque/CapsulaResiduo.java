
package ecologicaltimemachine.tanque;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public abstract class CapsulaResiduo{
    private Date fechaCarga;
    private float peso;

    public CapsulaResiduo(String fechaCarga, float peso) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fecha = formato.parse(fechaCarga);      
            this.fechaCarga = fecha;
            this.peso = peso;
        } catch (ParseException ex) {
            System.out.println("Ingrese el formato correcto de la fecha dia/mes/año" + ex.getMessage());
        }
        
    }
    // GETTERS Y SETTERS
    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }
    
    public float cantidadKm(){
        return this.getPeso();
    }

    @Override
    public String toString() {
        SimpleDateFormat fechaFormato = new SimpleDateFormat("dd/MM/yyyy"); 
        return "Fecha de carga: " + fechaFormato.format(fechaCarga) + " Peso " + peso + " Grs";
    }

     
  
    
    
}
