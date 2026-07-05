package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Modelo.Alumno;

public class AlumnoDao {
    
    private Connection conn;

    public AlumnoDao(Connection conn) {
        this.conn = conn;
    }

    public void insertar(Alumno a) throws SQLException {
        String sql = "INSERT INTO alumnos (nombre) VALUES (?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, a.getNombre());
        ps.executeUpdate();

        // Recuperamos el ID generado automaticamente.
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            a.setId(rs.getInt(1));
        }
    }

    public Alumno buscarAlumno(int id) throws SQLException {
        String sql = "SELECT * FROM alumnos WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Alumno(rs.getInt("id"), rs.getString("nombre"));
        }
        return null;
    }

    public List<Alumno> listarAlumnos() throws SQLException {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT * FROM alumnos";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            lista.add(new Alumno(rs.getInt("id"), rs.getString("nombre")));
        }
        return lista;
    }

}
