package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Modelo.Alumno;

public class InscripcionDao {
    private Connection conn;

    public InscripcionDao(Connection conn) {
        this.conn = conn;
    }

    public void inscribir(int idAlumno, int idCurso) throws SQLException {
        String sql = "INSERT INTO inscripciones (id_alumno, id_curso) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idAlumno);
        ps.setInt(2, idCurso);
        ps.executeUpdate();
    }

    // Evita inscribir al mismo alumno dos veces en el mismo curso
    public boolean yaEstaInscrito(int idAlumno, int idCurso) throws SQLException {
        String sql = "SELECT 1 FROM inscripciones WHERE id_alumno = ? AND id_curso = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idAlumno);
        ps.setInt(2, idCurso);
        ResultSet rs = ps.executeQuery();
        return rs.next(); // true si encontró al menos una fila
    }

    public List<Alumno> listarAlumnosDeCurso(int idCurso) throws SQLException {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT a.id, a.nombre FROM alumnos a " +
                     "JOIN inscripciones i ON a.id = i.id_alumno " +
                     "WHERE i.id_curso = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idCurso);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            lista.add(new Alumno(rs.getInt("id"), rs.getString("nombre")));
        }
        return lista;
    }
}