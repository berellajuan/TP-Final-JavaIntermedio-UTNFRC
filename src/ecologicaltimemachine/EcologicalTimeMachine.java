package ecologicaltimemachine;

import ecologicaltimemachine.condensador.CondensadorDeFlujo;
import ecologicaltimemachine.condensador.Lampara;
import ecologicaltimemachine.tanque.CapsulaResiduo;
import ecologicaltimemachine.tanque.SuperTanqueEcologicoDeCombustible;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EcologicalTimeMachine {

    private CondensadorDeFlujo condensadorDeFlujo;
    private SuperTanqueEcologicoDeCombustible superTanqueEC;
    private float relacionDiasPorKm;

    public EcologicalTimeMachine(CondensadorDeFlujo condensadorDeFlujo, SuperTanqueEcologicoDeCombustible superTanqueEC, float relacionDiasPorKm) {
        this.condensadorDeFlujo = condensadorDeFlujo;
        this.superTanqueEC = superTanqueEC;
        this.relacionDiasPorKm = relacionDiasPorKm;
    }

    public CondensadorDeFlujo getCondensadorDeFlujo() {
        return this.condensadorDeFlujo;
    }

    public void setCondensadorDeFlujo(CondensadorDeFlujo condensadorDeFlujo) {
        this.condensadorDeFlujo = condensadorDeFlujo;
    }

    public SuperTanqueEcologicoDeCombustible getSuperTanqueEC() {
        return this.superTanqueEC;
    }

    public void setSuperTanqueEC(SuperTanqueEcologicoDeCombustible superTanqueEC) {
        this.superTanqueEC = superTanqueEC;
    }

    public float getRelacionDiasPorKm() {
        return relacionDiasPorKm;
    }

    public void setRelacionDiasPorKm(float relacionDiasPorKm) {
        this.relacionDiasPorKm = relacionDiasPorKm;
    }

    // metodos ABM
    public boolean addLampara(Lampara lam) {
        return condensadorDeFlujo.addLampara(lam);
    }

    public boolean addCapsulaResiduo(CapsulaResiduo capusula) {
        return superTanqueEC.addCapsulaResiduo(capusula);
    }
    
    
    // Metodos de otras Clases
    public float cantTotalKilometros() {
        return superTanqueEC.calcularTotalKilometros();
    }
    
     public float eliminarCapsulasVencidas(){
        return superTanqueEC.eliminarCapsulasVencidas();
    }
    
    // no agregar print en un metodo 
    public void cambiarLamparaQuemada(Lampara lamp) {
        boolean repuesta = condensadorDeFlujo.cambiarLamparaQuemada(lamp);
        if (repuesta) {
            System.out.println("Se pudo remplazar la lampara con exito!!");
        } else {
            System.out.println("No se pudo remplazar la lampara, no hay lampara quemadas");
        }
    }
    
    public void generarArchivoCapsulasVencidas(){
        superTanqueEC.generarArchivoCapsulasVencidas();
    }
    
    public String capsulaMayorPeso(){
        return superTanqueEC.capsulaMayorPeso();
    }
   

    // Metodo de viajar funcional, no se si la forma correcta
    public boolean puedeViajarDesdeHasta(String desde, String hasta) {

        SimpleDateFormat fechaDesde = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fechaHasta = new SimpleDateFormat("dd/MM/yyyy");
        boolean resultado = false;
        int milisegundosUnDia = 86400000;

        try {
            Date fechaInicio = fechaDesde.parse(desde);
            Date fechaFin = fechaHasta.parse(hasta);
            
            long dias = ((fechaInicio.getTime() - fechaFin.getTime()));
            dias = Math.abs((dias/milisegundosUnDia));
                       
            int cantKmViajar = (int) (superTanqueEC.calcularTotalKilometros() / this.relacionDiasPorKm);
            System.out.println("------------------------------------------------");
            System.out.println("La cantidad de Dias que desea viajar: " + dias);
            System.out.println("La cantidad de Dias que puede viajer son: " + cantKmViajar);

            if (cantKmViajar >= dias && condensadorDeFlujo.estaFuncional()) {
                resultado = true;
            }

        } catch (ParseException ex) {
            Logger.getLogger(EcologicalTimeMachine.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;

    }

    // metodo toString
    
    
    @Override
    public String toString() {
        System.out.println("---------------------------------------");
        System.out.println("        Ecological Time Machine");
        System.out.println("----------Configuraciones--------------\n");
        System.out.println("-->Relacion dias por Km: " + this.getRelacionDiasPorKm());
        System.out.println("-->Potencia minima para iniciar el Condensador de Fujo: " + condensadorDeFlujo.getPotenciaMinimaParaFuncionar()+" Watts");
        System.out.println("-->Peso maximo del Tanque de Combustible Ecologico: " + superTanqueEC.getPesoMaximo() + " Grs\n");
        System.out.println("__________Condensador de Flujo_________");
        System.out.println("****** Lamparas dentro del condensador de flujo");
        Iterator<Lampara> it = condensadorDeFlujo.getLamparas().iterator();
        while(it.hasNext()){
            Lampara lam = it.next();
            System.out.println("*"+lam);
        }
        System.out.println("~>El condensador de flujo esta Funcional?: " +condensadorDeFlujo.estaFuncional());
        
        System.out.println("\n__________Super Tanque Ecologico de Combustible_________");
        System.out.println("°°°°°Capsulas dentro del Tanque°°°°°");
        Iterator<CapsulaResiduo> iter = superTanqueEC.getCapsulas().iterator();
        int cont = 0;
        while(iter.hasNext()){
            CapsulaResiduo cap = iter.next();
            System.out.println("^"+cap);
            cont++;
        }
        System.out.println("................................");
        System.out.println("Peso total de las Capsulas "+ superTanqueEC.calcularPesoTotal() +" Grs de: " +
                cont + " capsulas");
        System.out.println("Cantidad total de Km que posee el tanque, sin contar capsulas vencidas: " + superTanqueEC.calcularTotalKilometros());
        System.out.println(superTanqueEC.capsulaMayorPeso());
        
        return "";
    }
}



