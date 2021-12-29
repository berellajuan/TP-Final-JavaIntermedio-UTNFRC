package ecologicaltimemachine.interfazconsola;

import basedatos.BaseDatos;
import ecologicaltimemachine.EcologicalTimeMachine;
import ecologicaltimemachine.condensador.CondensadorDeFlujo;
import ecologicaltimemachine.condensador.Lampara;
import ecologicaltimemachine.tanque.Inorganico;
import ecologicaltimemachine.tanque.Organico;
import ecologicaltimemachine.tanque.SuperTanqueEcologicoDeCombustible;
import java.util.Scanner;

public class interfazConsola {

    public static void main(String[] args) {

        //Instancia SQL
        BaseDatos sqlJDBC = new BaseDatos();
        Scanner sc = new Scanner(System.in);

        System.out.println("------------- Bienvenido a Ecological Time Machine -------------");
        System.out.println("Primero Configure los parametros iniciales de la maquina...");
        System.out.print("Ingrese la potencia maxima en Watts para el Condensador de Flujo: ");
        float num = sc.nextInt();
        CondensadorDeFlujo condensadorDeFlujo = new CondensadorDeFlujo(num);
        System.out.print("Ingrese peso maximo del taque de combustible ecologico en Gramos: ");
        num = sc.nextInt();
        SuperTanqueEcologicoDeCombustible superTanqueEC = new SuperTanqueEcologicoDeCombustible(num);
        System.out.print("Ingrese los kilometros necesarios para avanzar/retroceder 1 dia: ");
        num = sc.nextInt();
        EcologicalTimeMachine maquina = new EcologicalTimeMachine(condensadorDeFlujo, superTanqueEC, num);

        int opc = -1;
        do {
            if (opc == 1) {
                System.out.print("Ingrese el estado de la lampara (true/false) funcional / no funcional: ");
                boolean estado = sc.nextBoolean();
                System.out.print("Ingrese la potencia deseada en Watts: ");
                float potencia = sc.nextFloat();
                boolean resultado1 = condensadorDeFlujo.addLampara(new Lampara(estado, potencia));
                System.out.println("***************************************************");
                if (resultado1) {
                    System.out.println("Se agrego con exito la Lampara capacidad " + condensadorDeFlujo.getLamparas().size() + "/3");
                } else {
                    System.out.println("********************************************************");
                    System.out.println("No se pudo agregar la lampara capacidad maxima alcanzada");
                }
            }

            if (opc == 2) {
                System.out.print("Ingrese 1- Cargar Capsula Organica 2- CapsulaInorganica: ");
                int opCarga = sc.nextInt();
                if (opCarga == 1) {
                    System.out.println("Ingrese informacion de capsula Organica");
                    System.out.print("Fecha de Carga (dia/mes/año) ej: 02/10/1996: ");
                    String fechaCarga = sc.next();
                    System.out.print("Peso de la Capsula (Grs): ");
                    float peso = sc.nextFloat();
                    System.out.print("Dias de duracion antes de su vencimiento: ");
                    int dias = sc.nextInt();
                    boolean resultado2 = maquina.addCapsulaResiduo(new Organico(fechaCarga, peso, dias));
                    if (resultado2) {
                        System.out.println("********************************************************");
                        System.out.println("Se Agrego la capsula con exito!");
                    } else {
                        System.out.println("********************************************************");
                        System.out.println("No se pudo agregar la capsula al tanque, alcanzo la capacidad maxima");
                    }
                }
                if (opCarga == 2) {

                    System.out.println("Ingrese informacion de capsula Inorganica");
                    System.out.print("Fecha de Carga (dia/mes/año) ej: 02/10/1996:  ");
                    String fechaCarga = sc.next();
                    System.out.print("Peso de la Capsula (Grs): ");
                    float peso = sc.nextFloat();
                    int tipo;
                    do {
                        System.out.print("Tipo de Capsula 1) Lata 2) Vidrio 3) Plastico: ");
                        tipo = sc.nextInt();
                    } while (tipo != 1 && tipo != 2 && tipo != 3);

                    Inorganico capsulaInorganica = new Inorganico(fechaCarga, peso, tipo);
                    boolean resultado2 = maquina.addCapsulaResiduo(capsulaInorganica);
                    sqlJDBC.insertarCapsula(capsulaInorganica);

                    if (resultado2) {
                        System.out.println("********************************************************");
                        System.out.println("Se Agrego la capsula con exito al vector y a la base de datos !");
                    } else {
                        System.out.println("********************************************************");
                        System.out.println("No se pudo agregar la capsula al tanque, alcanzo la capacidad maxima");
                    }

                }

            }
            if (opc == 3) {
                if (!maquina.getCondensadorDeFlujo().getLamparas().isEmpty() && !maquina.getSuperTanqueEC().getCapsulas().isEmpty()) {
                    System.out.print("Ingrese fecha de Inicio (dia/mes/año) ej: 02/10/1996 : ");
                    String desde = sc.next();
                    System.out.print("Ingrese fecha a la que desea viajar (dia/mes/año) ej: 02/10/2020 : ");
                    String hasta = sc.next();
                    boolean res = maquina.puedeViajarDesdeHasta(desde, hasta);
                    if (res) {
                        System.out.println("********************************************************");
                        System.out.println("Se pude veajar con exito !");
                    } else if (!maquina.getCondensadorDeFlujo().estaFuncional()) {
                        System.out.println("********************************************************");
                        System.out.println("No se puede viajar, no estan funcionales todas las lamparas del condensador");
                    } else {
                        System.out.println("********************************************************");
                        System.out.println("No se puede viajar, cantidad de km insuficientes");
                    }

                } else {
                    System.out.println("********************************************************");
                    System.out.println("Primero debe cargar las capsulas y lamparas a la maquina");
                }
            }
            if (opc == 4) {
                if (!maquina.getCondensadorDeFlujo().getLamparas().isEmpty() && !maquina.getSuperTanqueEC().getCapsulas().isEmpty()) {
                    System.out.println("********************************************************");
                    System.out.println(maquina.toString());
                } else {
                    System.out.println("********************************************************");
                    System.out.println("Primero debe cargar las capsulas y lamparas a la maquina");
                }
            }
            if (opc == 5) {
                if (!maquina.getCondensadorDeFlujo().getLamparas().isEmpty() && !maquina.getSuperTanqueEC().getCapsulas().isEmpty()) {
                    System.out.print("Ingrese (true|Funcional|/false|No funcional|) para ingresar una lampara quemada: ");
                    boolean estado = sc.nextBoolean();
                    System.out.print("Ingrese la potencia deseada en Watts: ");
                    float potencia = sc.nextFloat();
                    System.out.println("********************************************************");
                    maquina.cambiarLamparaQuemada(new Lampara(estado, potencia));
                } else {
                    System.out.println("********************************************************");
                    System.out.println("Primero debe cargar las capsulas y lamparas a la maquina");
                }

            }
            if (opc == 6) {
                if (!maquina.getCondensadorDeFlujo().getLamparas().isEmpty() && !maquina.getSuperTanqueEC().getCapsulas().isEmpty()) {
                    System.out.println("********************************************************");
                    System.out.println(maquina.capsulaMayorPeso());

                } else {
                    System.out.println("********************************************************");
                    System.out.println("Primero debe cargar las capsulas y lamparas a la maquina");
                }
            }
            if (opc == 7) {
                if (!maquina.getCondensadorDeFlujo().getLamparas().isEmpty() && !maquina.getSuperTanqueEC().getCapsulas().isEmpty()) {
                    System.out.println("********************************************************");
                    maquina.generarArchivoCapsulasVencidas();
                    System.out.println("Se agregaron al archivo las capsulas vencidas");
                    System.out.println("Cantidad de kilometros eliminados de las capsulas vencidas: " + maquina.eliminarCapsulasVencidas());
                } else {
                    System.out.println("********************************************************");
                    System.out.println("Primero debe cargar las capsulas y lamparas a la maquina");
                }
            }
            if (opc == 8) {

                System.out.println("********************************************************");
                sqlJDBC.listarCapsulasInorganicasJDBC();
                System.out.println("********************************************************");

            }
            if (opc == 9) {

                System.out.println("********************************************************");
                System.out.print("Ingrese el tipo de material que desea hacer la consulta: 1)Latas 2)Vidrio 3)Plastico: ");
                int opCargaSql = sc.nextInt();
                if (opCargaSql == 1) {
                    System.out.println("La cantidad de Capsulas inorganicas del tipo Latas son: "
                            + sqlJDBC.cantCapsulasInorganicas(opCargaSql));
                }
                if (opCargaSql == 2) {
                    System.out.println("La cantidad de Capsulas inorganicas del tipo Vidrio son: "
                            + sqlJDBC.cantCapsulasInorganicas(opCargaSql));
                }
                if (opCargaSql == 3) {
                    System.out.println("La cantidad de Capsulas inorganicas del tipo Plastico son: "
                            + sqlJDBC.cantCapsulasInorganicas(opCargaSql));
                }

            }

            if (opc == 10) {
                
                    System.out.println("********************************************************");
                    sqlJDBC.listarCapsulasInorganicasJDBC_Completas();
                    System.out.print("Ingrese el ID de la capsula que desea ELIMINAR de la Base de Datos: ");
                    int opCargaSqlBorrar = sc.nextInt();
                    int auxVar = sqlJDBC.borrarCapsula(opCargaSqlBorrar);
                    if (auxVar != -1) {
                        System.out.println("Se elimino con exito la Capsula Inorganica de la base de datos");
                    } else {
                        System.out.println("No se pudo eliminar la capsula de la base de datos");
                    }
            }
            System.out.println("\n------------- Bienvenido a Ecological Time Machine -------------");
            System.out.println("1- Cargar Lamparas al condensador ");
            System.out.println("2- Cargar Capsulas al tanque ");
            System.out.println("3- Viajar Desde - hasta ");
            System.out.println("4- Mostrar informacion de la Maquina ");
            System.out.println("5- Cambiar lampara quemada ");
            System.out.println("6- Capsula de mayor peso ");
            System.out.println("7- Eliminar Capsulas Vencidas ");
            System.out.println("8- Listado de Capsulas inorganicas en la Base de Datos (SQL) ");
            System.out.println("9- Cantidad de Capsulas inorganicas de un tipo de material en la Base de Datos (SQL) ");
            System.out.println("10- Borrar una capsula INORGANICA de la Base de Datos (SQL)");
            System.out.println("0- Salir del sistema");
            System.out.print("Ingrese opcion: ");

            opc = sc.nextInt();

        } while (opc != 0);

    }

}
