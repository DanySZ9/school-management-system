import java.sql.*;
import java.util.Scanner;

import Modelo.Alumno;
import Modelo.Curso;
import Servicio.Escuela;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:escuela.db")) {

            crearTablas(conn); 

            Escuela escuela = new Escuela(conn);
            int opcion;

            do {
                System.out.println("\n--- SISTEMA ESCOLAR ---");
                System.out.println("=====================================");
                System.out.println("1. Registrar alumno");
                System.out.println("2. Agregar curso");
                System.out.println("3. Inscribir alumno en curso");
                System.out.println("4. Listar alumnos registrados");
                System.out.println("5. Listar cursos");
                System.out.println("6. Listar alumnos de un curso");
                System.out.println("0. Salir");
                System.out.println("=====================================");

                System.out.print("Elige una opción: ");
                opcion = sc.nextInt();
                sc.nextLine(); 

                System.out.println("=====================================");

                switch (opcion) {
                    case 1:
                        System.out.print("Nombre del alumno: ");
                        String nombreAlumno = sc.nextLine();
                        escuela.insertar(new Alumno(0, nombreAlumno));
                        break;
                    case 2:
                        System.out.print("Nombre del curso: ");
                        String nombreCurso = sc.nextLine();
                        escuela.agregarCurso(new Curso(0, nombreCurso));
                        break;
                    case 3:
                        System.out.println("=====================================");
                        escuela.listarAlumnosRegistrados();
                        System.out.println("=====================================");
                        System.out.print("ID del alumno: ");
                        int idAlumno = sc.nextInt();
                        System.out.println("=====================================");
                        escuela.listarCursos();
                        System.out.println("=====================================");
                        System.out.print("ID del curso: ");
                        int idCurso = sc.nextInt();
                        escuela.inscribirAlumnoEnCurso(idAlumno, idCurso);
                        break;
                    case 4:
                        System.out.println("=====================================");
                        escuela.listarAlumnosRegistrados();
                        System.out.println("=====================================");
                        break;
                    case 5:
                        System.out.println("=====================================");
                        escuela.listarCursos();
                        System.out.println("=====================================");
                        break;
                    case 6:
                        System.out.println("=====================================");
                        escuela.listarCursos();
                        System.out.println("=====================================");
                        System.out.print("ID del curso: ");
                        int idCursoConsulta = sc.nextInt();
                        System.out.println("=====================================");
                        escuela.listarAlumnosDeCurso(idCursoConsulta);
                        System.out.println("=====================================");
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        System.out.println("=====================================");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                        System.out.println("=====================================");
                }
            } while (opcion != 0);

        } catch (SQLException e) {
            System.out.println("=====================================");
            System.out.println("Error de base de datos: " + e.getMessage());
            System.out.println("=====================================");
            sc.close();
        }
    }

    private static void crearTablas(Connection conn) throws SQLException {
        Statement st = conn.createStatement();
        st.execute("CREATE TABLE IF NOT EXISTS alumnos (" +
                   "id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL)");
        st.execute("CREATE TABLE IF NOT EXISTS cursos (" +
                   "id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL)");
        st.execute("CREATE TABLE IF NOT EXISTS inscripciones (" +
                   "id_alumno INTEGER, id_curso INTEGER, " +
                   "FOREIGN KEY (id_alumno) REFERENCES alumnos(id), " +
                   "FOREIGN KEY (id_curso) REFERENCES cursos(id))");
    }
}