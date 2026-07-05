package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Modelo.Curso;

public class CursoDao {
    private Connection conn;

    public CursoDao(Connection conn) {
        this.conn = conn;
    }

    public void insertar(Curso c) throws SQLException {
        String sql = "INSERT INTO cursos (nombre) VALUES (?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, c.getNombre());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            c.setId(rs.getInt(1));
        }
    }

    public Curso buscarCurso(int id) throws SQLException {
        String sql = "SELECT * FROM cursos WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Curso(rs.getInt("id"), rs.getString("nombre"));
        }
        return null;
    }

    public List<Curso> listarTodos() throws SQLException {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT * FROM cursos";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            lista.add(new Curso(rs.getInt("id"), rs.getString("nombre")));
        }
        return lista;
    }
}