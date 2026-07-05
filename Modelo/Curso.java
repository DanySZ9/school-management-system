package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Curso {

    private int id;
    private String nombre;
    private List<Alumno> alumnos;

    public Curso(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.alumnos = new ArrayList<>();
    }

    public Curso(int id, String nombre, List<Alumno> alumnos) {
        this.id = id;
        this.nombre = nombre;
        this.alumnos = alumnos != null ? alumnos : new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }
    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos != null ? alumnos : new ArrayList<>();
    }

    public void agregarAlumno(Alumno a) {
        if (a != null && !alumnos.contains(a)) {
            alumnos.add(a);
        }
    }

    @Override
    public String toString() {
        return "Curso{id=" + id + ", nombre='" + nombre + "'}";
    }
}
