package com.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Map<Genero, List<Persona> > personasPorGenero = personas.stream()
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

    }
}
