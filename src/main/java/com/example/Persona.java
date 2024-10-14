package com.example;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Persona implements Comparable<Persona>{

    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private Genero genero;
    private LocalDate fechaNacimiento;
    private double salario;

    public static List<Persona> getPersonas()  {

        List<Persona> personas = new ArrayList<>();

        personas.add(
            Persona.builder()
                .nombre("Ruben")
                .primerApellido("Gomez")
                .segundoApellido("Sabio")
                .genero(Genero.HOMBRE)
                .fechaNacimiento(LocalDate.of(2000, Month.SEPTEMBER,23))
                .salario(4500.50)
                .build()
        );

        personas.add(
            Persona.builder()
                .nombre("Juan")
                .primerApellido("Guerrero")
                .segundoApellido("Leon")
                .genero(Genero.HOMBRE)
                .fechaNacimiento(LocalDate.of(1997, Month.MAY,23))
                .salario(10000.50)
                .build()
        );

        personas.add(
            Persona.builder()
                .nombre("Ivan")
                .primerApellido("Reina")
                .segundoApellido("Galvez")
                .genero(Genero.HOMBRE)
                .fechaNacimiento(LocalDate.of(2001, Month.OCTOBER, 10))
                .salario(6500.50)
                .build()
        );

        personas.add(
            Persona.builder()
                .nombre("Antonio")
                .primerApellido("Saez")
                .segundoApellido("Vagace")
                .genero(Genero.HOMBRE)
                .fechaNacimiento(LocalDate.of(1990, Month.DECEMBER,3))
                .salario(8500.50)
                .build()
        );

        personas.add(
            Persona.builder()
                .nombre("Marcos")
                .primerApellido("Perez")
                .segundoApellido("Serrano")
                .genero(Genero.HOMBRE)
                .fechaNacimiento(LocalDate.of(1996, Month.APRIL, 23))
                .salario(9500.50)
                .build()
        );
        personas.add(
            Persona.builder()
                .nombre("Elli")
                .primerApellido("Fdez")
                .segundoApellido("Hdez")
                .genero(Genero.MUJER)
                .fechaNacimiento(LocalDate.of(2005, Month.SEPTEMBER,14))
                .salario(3000.50)
                .build()
        );

        personas.add(
            Persona.builder()
                .nombre("Paca")
                .primerApellido("Fdez")
                .segundoApellido("Hdez")
                .genero(Genero.MUJER)
                .fechaNacimiento(LocalDate.of(2004, Month.JULY,14))
                .salario(3000.50)
                .build()
        );


        return personas;
    }

    @Override
    public int compareTo(Persona persona) {
        // Comparar las personas primero por el primer apellido, luego segundo
        // y por ultimo que decida el nombre.
        int cmpPrimerApellido = this.primerApellido.compareTo(persona.getPrimerApellido());
        int cmpSegundoApellido = this.segundoApellido.compareTo(persona.getSegundoApellido());
        int cmpNombre = this.nombre.compareTo(persona.getNombre());

        return cmpPrimerApellido != 0 ? cmpPrimerApellido : 
                       cmpSegundoApellido != 0 ? cmpSegundoApellido : cmpNombre;
    }
}
