package com.example;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * https://docs.oracle.com/javase/tutorial/collections/interfaces/map.html
 */
public class App {

    public static void main(String[] args) {

        /* ¿Que es un mapa?
         * Es un tipo de coleccion que almacena la informacion en parejas de clave (key) y valor (value),
         * donde las claves no se pueden repetir, es tu tipo de hash.
         * 
         * No hereda de la interface Collection, por lo cual se dice que no es una verdadera coleccion, aunque
         * se puede tratar como una coleccion utilizando las Collection Views (Vistas de Collection)
         */
        // ¿Como se puede crear un mapa? Para ejemplificar, determinar la frecuencia de ocurrencia de
        // una palabra en un array o lista de palabras.
        List<String> palabras = Arrays.asList("Antonio", "Antonio", "Juan", "Antonio", "Ruben", "Marcos", "Ruben");

        Map<String, Long> m = new HashMap<>();

        // Para rellenar el mapa m, primero utilizaremos una sentencia for mejorado
        for (String palabra : palabras) {

            Long frecuenciaOcurrencia = m.get(palabra);
            m.put(palabra, frecuenciaOcurrencia == null ? 1L : ++frecuenciaOcurrencia);
        }

        System.out.println(m);

        /* Lo anterior esta bien pero es antiguo, actualmente se puede obtener el mismo resultado
         * con OPERACIONES DE AGReGADO (Metodos de la clase Stream, tuberias, lambdas, metodos 
         * por referenca, en fin, PROGAMACION FUNCIONAL)
         */
        // palabra -> palabra es similar a utilizar Function.identity()
        Map<String, Long> m2 = palabras.stream()
                //  .collect(Collectors.groupingBy(palabra -> palabra, Collectors.counting()));
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        System.out.println(m2);

        /* Ejemplos tipicos de creacion de mapas, colecciones Map Interface */

 /* ejemplo # 1
         * 
         * Recorrer la coleccion de personas y obtener un mapa que agrupe las personas por Genero
         * 
         */
        List<Persona> personas = Persona.getPersonas();

        // Siempre que se recorra una lista del mismo tipo que el valor del mapa
        // NO hay que hacer absolutamente nada para obtener el valor del mapa
        Map<Genero, List<Persona>> personasPorGenero = personas.stream()
                //  .collect(Collectors.groupingBy(persona -> persona.getGenero()));
                .collect(Collectors.groupingBy(Persona::getGenero));
        System.out.println("Colleccion de personas agrupadas por Genero");
        System.out.println(personasPorGenero);

        /* ejemplo # 2
         * 
         * Recorrer la lista de personas y obtener una nueva coleccion que agrupe nombres de persona,
         * separados por punto y coma, por genero.
         * 
         */
        Map<Genero, String> nombresPorGenero = personas.stream()
                .collect(Collectors.groupingBy(Persona::getGenero,
                        Collectors.mapping(Persona::getNombre, Collectors.joining(";"))));

        System.out.println(nombresPorGenero);

        /*
         * Un Map Interface no es realmente una coleccion porque no hereda de la interface Collection,
         * pero se puede tratar como tal utilizando las llamadas vistas de collecion (Collection Views),
         * para en cada momento acceder a las claves del mapa, a los valores o a ambos.
         * 
         * https://docs.oracle.com/javase/tutorial/collections/interfaces/map.html (Collection Views)
         */

 /* Recorrer el mapa de personas agrupadas por Genero para mostrar solamente las
          * personas que tengan un salario superior a 4000 euros.
         */

 /* Utilizando una sentencia for mejorada */
        for (Map.Entry<Genero, List<Persona>> entry : personasPorGenero.entrySet()) {

            Genero key = entry.getKey();
            List<Persona> value = entry.getValue();

            System.out.println("Del Genero " + key);
            System.out.println("Personas con salario superior a 4000");

            for (Persona persona : value) {
                if (persona.getSalario() > 4000) {
                    System.out.println(persona);
                }
            }
        }

        /* Utilizando Operaciones de Agregado */
        System.out.println("utilizando Operaciones de Agregado ...");

        personasPorGenero.entrySet().stream().forEach(entry -> {
            System.out.println("Del Genero: " + entry.getKey());
            System.out.println("Personas con salario superior a 4000");

            entry.getValue().stream().filter(p -> p.getSalario() > 4000).forEach(System.out::println);
        });

        /* Ejercicio # 1:
        * 
        * Crear una coleccion que agrupe Personas por Genero y Edad de la persona. 
        * 
        * Recorrer la coleccion obtenida y mostrar solamente las personas del Genero HOMBRE, que tengan
        * un salario superior a la media.
         */
        Map<Genero, Map<Long, List<Persona>>> personasGeneroEdad = personas.stream()
                .collect(Collectors.groupingBy(Persona::getGenero,
                        Collectors.groupingBy(Persona::edad)));

        // Recuperamos el salario promedio
        final Double salarioMedio = personas.stream().mapToDouble(Persona::getSalario)
                .average().orElseThrow(() -> new RuntimeException());

        // Recorremos la coleccion personasGeneroEdad
        personasGeneroEdad.entrySet().stream().forEach(entry1 -> {
            System.out.println("Genero : " + entry1.getKey());
            Map<Long, List<Persona>> entry2 = entry1.getValue();

            entry2.entrySet().stream().forEach(entry -> {
                entry.getValue().stream().filter(persona -> persona.getGenero().equals(Genero.HOMBRE)
                        && persona.getSalario() > salarioMedio).forEach(System.out::println);
            });
        });

        // de otra forma
        // Recorrer la coleccion personasGeneroEdad
        personasGeneroEdad.entrySet().stream().forEach(entry1 -> {

            Genero genero = entry1.getKey();
            System.out.println("Genero: " + genero);

            Map<Long, List<Persona>> entry2 = entry1.getValue();

            // Collection<List<Persona>> persons = entry2.values();
            // persons.stream().flatMap(lista -> lista.stream())
            // .filter(persona -> persona.getGenero().equals(Genero.HOMBRE) &&
            // persona.getSalario() > salarioMedio)
            // .forEach(System.out::println);
            entry2.entrySet().stream().forEach(entry -> {
                List<Persona> persons = entry.getValue();
                persons.stream().filter(p -> p.getGenero().equals(Genero.HOMBRE)
                        && p.getSalario() > salarioMedio)
                        .forEach(System.out::println);
            });

        });


        /* Los mapas se trabajan con la clase HashMap que no permite ordenamiento, y al final
         * si queremos ordenar las claves del mapa, se copia la coleccion a un TreeMap, que si
         * permite ordenamiento.
         */

 /* Ejemplo # 2
          
          Punto 1.
          Agregar nombres repetidos a la clase de Persona y obtener una coleccion que agrupe nombres
          y sumatoria de los salarios de las personas que tienen el mismo nombre.

          Punto 2.
          Obtener una nueva coleccion que ordene las claves del mapa alfabeticamente, de la A a la Z.

          Punto 3.
          Obtener una nueva coleccion que ordene las claves del mapa en oreden
          alfabetico inverso, de la Z a la A.
         */
        
         // Rta al Punto 1. Bien por Ruben.
        Map<String, Double> salariosPorNombre = personas.stream()
                .collect(Collectors.groupingBy(Persona::getNombre,
                        Collectors.summingDouble(Persona::getSalario)));

        System.out.println("Salario por nombre sin ordenar");
        System.out.println(salariosPorNombre);

        // Punto 2.
        Map<String, Double> salariosPorNombreOrdenado = new TreeMap<>();

        System.out.println("Salarios por nombre ordenado alfabeticamente");
        salariosPorNombreOrdenado.putAll(salariosPorNombre);

        System.out.println(salariosPorNombreOrdenado);

// Punto 3
// Variante # 1
        Map<String, Double> salariosPorNombreOrdenInverso = new TreeMap<>((nombre1, nombre2) -> nombre2.compareTo(nombre1));

        System.out.println("Salarios Ordenados por Nombre en orden inverso");
        salariosPorNombreOrdenInverso.putAll(salariosPorNombre);
        System.out.println(salariosPorNombreOrdenInverso);

// Variante # 2. Ivan
        var salariosPorNombreOrdenadoInversamente = new TreeMap<>(Collections.reverseOrder());
        System.out.println("Solucion de Ivan");
        salariosPorNombreOrdenadoInversamente.putAll(salariosPorNombre);
        System.out.println(salariosPorNombreOrdenadoInversamente);

    }
}
