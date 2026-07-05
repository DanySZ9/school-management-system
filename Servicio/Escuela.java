package Servicio;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import DAO.AlumnoDao;
import DAO.CursoDao;
import DAO.InscripcionDao;
import Modelo.Alumno;
import Modelo.Curso;

public class Escuela {

    private final AlumnoDao alumnoDAO;
    private final CursoDao cursoDAO;
    private final InscripcionDao inscripcionDAO;

    public Escuela(Connection conn) {
        this.alumnoDAO = new AlumnoDao(conn);
        this.cursoDAO = new CursoDao(conn);
        this.inscripcionDAO = new InscripcionDao(conn);
    }

    public void insertar(Alumno alumno) throws SQLException {
        alumnoDAO.insertar(alumno);
         System.out.println("=====================================");
        System.out.println("Alumno registrado");
        System.out.println("=====================================");

    }

    public void agregarCurso(Curso curso) throws SQLException {
        cursoDAO.insertar(curso);
        System.out.println("=====================================");
        System.out.println("Curso agregado");
        System.out.println("=====================================");
    }

    public void listarAlumnosRegistrados() throws SQLException {
        List<Alumno> alumnos = alumnoDAO.listarAlumnos();
        System.out.println("--- Alumnos registrados ---");
        if (alumnos.isEmpty()) {
            System.out.println("=====================================");
            System.out.println("No hay alumnos registrados.");
            System.out.println("=====================================");
            return;
        }
        for (Alumno alumno : alumnos) {
            System.out.println(alumno);
        }
    }

    public void listarCursos() throws SQLException {
        List<Curso> cursos = cursoDAO.listarTodos();
        System.out.println("--- Cursos disponibles ---");
        if (cursos.isEmpty()) {
            System.out.println("=====================================");
            System.out.println("No hay cursos disponibles.");
            System.out.println("=====================================");
            return;
        }
        for (Curso curso : cursos) {
            System.out.println(curso);
        }
    }

    public void inscribirAlumnoEnCurso(int idAlumno, int idCurso) throws SQLException {
        Alumno alumno = alumnoDAO.buscarAlumno(idAlumno);
        if (alumno == null) {
            System.out.println("=====================================");
            System.out.println("Error: alumno no registrado.");
            System.out.println("=====================================");
            return;
        }

        Curso curso = cursoDAO.buscarCurso(idCurso);
        if (curso == null) {
            System.out.println("=====================================");
            System.out.println("Error: curso no existe.");
            System.out.println("=====================================");
            return;
        }

        if (inscripcionDAO.yaEstaInscrito(idAlumno, idCurso)) {
            System.out.println("=====================================");
            System.out.println("Error: el alumno ya está inscrito en este curso.");
            System.out.println("=====================================");
            return;
        }

        inscripcionDAO.inscribir(idAlumno, idCurso);
        System.out.println("=====================================");
        System.out.println(alumno.getNombre() + " fue inscrito en " + curso.getNombre());
        System.out.println("=====================================");
    }

    public void listarAlumnosDeCurso(int idCurso) throws SQLException {
        Curso curso = cursoDAO.buscarCurso(idCurso);
        if (curso == null) {
            System.out.println("=====================================");
            System.out.println("Error: el curso no existe.");
            System.out.println("=====================================");
            return;
        }

        List<Alumno> alumnos = inscripcionDAO.listarAlumnosDeCurso(idCurso);
        System.out.println("--- Alumnos en " + curso.getNombre() + " ---");
        if (alumnos.isEmpty()) {
            System.out.println("=====================================");
            System.out.println("No hay alumnos inscritos en este curso.");
            System.out.println("=====================================");
            return;
        }
        for (Alumno alumno : alumnos) {
            System.out.println(alumno);
        }
    }
}
