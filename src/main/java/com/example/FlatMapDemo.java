package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FlatMapDemo {

    public static void main(String[] args) {
        
        // El metodo flatMap aplana listas, es muy util cuando se tienen
        // listas anidadas, y ademas no tendria en cuenta alguna lista 
        // que estuviese vacia.

        List<List<String>> listaDeLista = Arrays.asList(
            Arrays.asList("Ruben", "Antonio"),
            Arrays.asList("Juan"),
            Arrays.asList("Marco")
        );

        listaDeLista.stream().flatMap(l -> l.stream()).forEach(System.out::println);
        
        List<String> lista1 =  List.of("Ivan","Juan");
        List<String> lista2 =  List.of("Antonio");
        List<String> lista3 =  List.of("Ruben","Marcos");

        Stream<List<String>> flujoDeListas = Stream.of(lista1, lista2, lista3);

        System.out.println("Flujo de listas aplanado por cortesia de flatMap");
        flujoDeListas.flatMap(l -> l.stream()).forEach(System.out::println);

    }

}
