package ecologicaltimemachine.tanque;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


public class SuperTanqueEcologicoDeCombustible {

    private float pesoMaximo;
    private LinkedList<CapsulaResiduo> capsulas;
    
    // CONSTRUCTOR
    public SuperTanqueEcologicoDeCombustible(float pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
        this.capsulas = new LinkedList<>();
    }

    // Metodos Getters y Setters
    public float getPesoMaximo() {
        return pesoMaximo;
    }

    public void setPesoMaximo(float pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
    }

    public LinkedList<CapsulaResiduo> getCapsulas() {
        return capsulas;
    }

    public void setCapsulas(LinkedList<CapsulaResiduo> capsulas) {
        this.capsulas = capsulas;
    }

    // Metodos restantes
    public float calcularPesoTotal() {
        float pesoTotal = 0;
        Iterator<CapsulaResiduo> it = capsulas.iterator();
        while (it.hasNext()) {
            CapsulaResiduo cap = it.next();
            pesoTotal += cap.getPeso();
        }
        return pesoTotal;
    }

    public float calcularTotalKilometros() {
        float cantTotalKilometros = 0;
        Iterator<CapsulaResiduo> it = capsulas.iterator();
        while (it.hasNext()) {
            CapsulaResiduo cap = it.next();
            if (cap instanceof Organico && !((Organico) cap).estaVencida()) {
                cantTotalKilometros += cap.cantidadKm();
            } else if (cap instanceof Inorganico) {
                cantTotalKilometros += cap.cantidadKm();
            }
        }
        return cantTotalKilometros;

    }

    public float eliminarCapsulasVencidas() {
        float sumCapsulas = 0;
        Iterator<CapsulaResiduo> it = capsulas.iterator();
        while (it.hasNext()) {
            CapsulaResiduo cap = it.next();
            if (cap instanceof Organico && ((Organico) cap).estaVencida()) {
                sumCapsulas += cap.cantidadKm();
                it.remove();
            }
        }
        return sumCapsulas;
    }

    public void generarArchivoCapsulasVencidas() {
        try(RandomAccessFile raf = new RandomAccessFile("capsulasVencidas.txt", "rw")) {
            Iterator<CapsulaResiduo> it = capsulas.iterator();
            while (it.hasNext()) {
                CapsulaResiduo cap = it.next();
                if (cap instanceof Organico && ((Organico) cap).estaVencida()) {
                    try {
                        raf.seek(raf.length());
                        raf.writeBytes(cap.toString());
                    } catch (IOException ex) {
                        System.out.println("Error durante la escritura del archivo: " + ex.getMessage());
                    }
                }
            }
            System.out.println("Archivo creado! ");
        } catch (FileNotFoundException ex) {
            System.out.println("Error al crear el archivo" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al cerrar el archivo" + ex.getMessage());
        }
    }

  
    public String capsulaMayorPeso() {
        String resultado = "";
        // selecciono el primer objeto
        CapsulaResiduo may = capsulas.getFirst();
        ArrayList<CapsulaResiduo> mayores = new ArrayList<>();
        Iterator<CapsulaResiduo> it = capsulas.iterator();
        while (it.hasNext()) {
            CapsulaResiduo cap = it.next();
            if (may.getPeso() < cap.getPeso()) {
                may = cap;
            }
        }
        // comparo el mayor de vuelta si hay iguales
        Iterator<CapsulaResiduo> ite = capsulas.iterator();
        while (ite.hasNext()) {
            CapsulaResiduo cap = ite.next();
            if (may.getPeso() == cap.getPeso()) {
                mayores.add(cap);
            }
        }
        // muentro el vector de mayores
        if (may != null && mayores.size() > 0) {

            Iterator<CapsulaResiduo> iteracion = mayores.iterator();
            while (iteracion.hasNext()) {
                CapsulaResiduo cap = iteracion.next();
                resultado += "La capsula de mayor peso es : " + cap.toString() + "\n";
            }
            return resultado;
        }
        return "El arreglo no contiene capsulas";
    }

    public boolean addCapsulaResiduo(CapsulaResiduo capsula) {
        boolean res = false;
        float aux = this.calcularPesoTotal() + capsula.getPeso();
        if (aux <= this.getPesoMaximo()) {
            capsulas.add(capsula);
            res = true;
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SuperTanqueEcologicoDeCombustible\nPeso maximo: ").append(pesoMaximo).append(" Grs");
        sb.append("\ncapsulas: ");
        for (CapsulaResiduo capsula : capsulas) {
            sb.append("\n"+capsula.toString());
        }
         
        return sb.toString();
    }

}
