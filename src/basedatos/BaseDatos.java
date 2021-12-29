package basedatos;

import ecologicaltimemachine.tanque.Inorganico;
import java.sql.*;
import java.text.SimpleDateFormat;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

public class BaseDatos {

    private static final String SQL_SELECT = "SELECT capsulas_residuo_inorganico.fecha_carga, capsulas_residuo_inorganico.peso, materiales.nombre "
            + "FROM base_maquina_tiempo.capsulas_residuo_inorganico "
            + "INNER JOIN base_maquina_tiempo.materiales ON capsulas_residuo_inorganico.id_material = materiales.id_material";

    private static final String SQL_SELECT_ALL = "SELECT capsulas_residuo_inorganico.id_capsula ,capsulas_residuo_inorganico.fecha_carga, capsulas_residuo_inorganico.peso, materiales.nombre "
            + "FROM base_maquina_tiempo.capsulas_residuo_inorganico "
            + "INNER JOIN base_maquina_tiempo.materiales ON capsulas_residuo_inorganico.id_material = materiales.id_material";

    private static final String SQL_INSERT = "INSERT INTO capsulas_residuo_inorganico(fecha_carga,peso,id_material) VALUES(?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM base_maquina_tiempo.capsulas_residuo_inorganico WHERE id_capsula = ?";


    public void listarCapsulasInorganicasJDBC() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {

                Date fechaCarga = new Date(rs.getDate("fecha_carga").getTime());
                double peso = rs.getDouble("peso");
                String nombre = rs.getString("nombre");

                SimpleDateFormat formateo = new SimpleDateFormat("dd/MM/yyyy");

                System.out.println("Fecha de carga: " + formateo.format(fechaCarga)
                        + " Peso: " + peso + " Tipo de material: " + nombre);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                Conexion.close(rs);
                Conexion.close(stmt);
                Conexion.close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }
    }

    public void insertarCapsula(Inorganico capsula) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);

            java.sql.Date date1 = new java.sql.Date(capsula.getFechaCarga().getTime());

            stmt.setDate(1, date1);
            stmt.setDouble(2, capsula.getPeso());
            stmt.setInt(3, capsula.getTipo());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Conexion.close(stmt);
                Conexion.close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }

    }

    public int borrarCapsula(int idCapsula) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int res = -1;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idCapsula);
            res = stmt.executeUpdate();
            return res;
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Conexion.close(stmt);
                Conexion.close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }
        return res;
    }

    public int cantCapsulasInorganicas(int tipo) {
        String SQL_BUSCAR = "SELECT COUNT(*) FROM base_maquina_tiempo.capsulas_residuo_inorganico WHERE id_material='" + tipo + "'";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_BUSCAR);
            rs = stmt.executeQuery();
            int n = 0;
            if (rs.next()) {
                n = rs.getInt(1);
            }

            return n;

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                Conexion.close(rs);
                Conexion.close(stmt);
                Conexion.close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }

        return 0;
    }
    
    public void listarCapsulasInorganicasJDBC_Completas() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int idCapsula = rs.getInt("id_capsula");
                Date fechaCarga = new Date(rs.getDate("fecha_carga").getTime());
                double peso = rs.getDouble("peso");
                String nombre = rs.getString("nombre");

                SimpleDateFormat formateo = new SimpleDateFormat("dd/MM/yyyy");

                System.out.println("Id de la Capsula: " + idCapsula + " fecha de carga: " + formateo.format(fechaCarga)
                        + " Peso: " + peso + " Tipo de material: " + nombre);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                Conexion.close(rs);
                Conexion.close(stmt);
                Conexion.close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }
    }

}
