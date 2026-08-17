Estructuras de Datos
====================

Práctica 2: Pilas, colas, ordenamientos y búsquedas
---------------------------------------------------

### Fecha de entrega: martes 9 de septiembre, 2025

Deben implementar los métodos faltantes de las clases
[`MeteSaca`](https://aztlan.fciencias.unam.mx/gitlab/2026-1-edd/practica2/blob/main/src/main/java/mx/unam/ciencias/edd/MeteSaca.java),
[`Cola`](https://aztlan.fciencias.unam.mx/gitlab/2026-1-edd/practica2/blob/main/src/main/java/mx/unam/ciencias/edd/Cola.java),
[`Pila`](https://aztlan.fciencias.unam.mx/gitlab/2026-1-edd/practica2/blob/main/src/main/java/mx/unam/ciencias/edd/Pila.java),
y
[`Arreglos`](https://aztlan.fciencias.unam.mx/gitlab/2026-1-edd/practica2/blob/main/src/main/java/mx/unam/ciencias/edd/Arreglos.java).

Además, deben agregar los métodos
[`mergeSort()`](https://aztlan.fciencias.unam.mx/gitlab/2026-1-edd/practica2/blob/main/src/main/java/mx/unam/ciencias/edd/Lista.java#L310)
y
[`busquedaLineal()`](https://aztlan.fciencias.unam.mx/gitlab/2026-1-edd/practica2/blob/main/src/main/java/mx/unam/ciencias/edd/Lista.java#L335)
a la clase
[`Lista`](https://aztlan.fciencias.unam.mx/gitlab/2026-1-edd/practica2/blob/main/src/main/java/mx/unam/ciencias/edd/Lista.java).

Una vez que hayan terminado sus clases, la práctica debe compilar al hacer:

```
$ mvn compile
```

También debe pasar todas sus pruebas unitarias al hacer:

```
$ mvn test
```

Y por último, debe correr correctamente el programa escrito en la clase
[`Practica2`](https://aztlan.fciencias.unam.mx/gitlab/2026-1-edd/practica2/blob/main/src/main/java/mx/unam/ciencias/edd/Practica2.java)
al ejecutar:

```
$ mvn install
...
$ java -jar target/practica2.jar 10000
```

Los únicos archivos que deben modificar son:

* `Arreglos.java`,
* `Cola.java`,
* `Lista.java`,
* `MeteSaca.java`, y
* `Pila.java`.

*No deben modificar de ninguna manera ninguno de los otros archivos de la
práctica*.

### Repositorio

Pueden clonar la práctica con el siguiente comando:

```
$ git clone https://aztlan.fciencias.unam.mx/gitlab/2026-1-edd/practica2.git
```

### Documentación

La documentación generada por JavaDoc la pueden consultar aquí:

[Documentación generada por JavaDoc para la práctica
2](https://aztlan.fciencias.unam.mx/~canek/2026-1-edd/practica2/apidocs/index.html)

### Capítulos del libro

Los capítulos [del
libro](https://tienda.fciencias.unam.mx/es/home/437-estructuras-de-datos-con-java-moderno-9786073009157.html)
relacionados a esta práctica son:

6. Complejidad computacional
7. Arreglos
8. Pilas y colas
9. Lambdas
9. Ordenamientos
10. Búsquedas
