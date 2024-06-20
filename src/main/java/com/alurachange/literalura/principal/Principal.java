package com.alurachange.literalura.principal;

import com.alurachange.literalura.model.Autor;
import com.alurachange.literalura.model.Datos;
import com.alurachange.literalura.model.Libro;
import com.alurachange.literalura.service.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    Scanner teclado = new Scanner(System.in);
    private static final String URL_BASE = "https://gutendex.com/books/";
    private final ConsumoAPI consumoApi = new ConsumoAPI();
    private final ConvierteDatos convierteDatos = new ConvierteDatos();
    private ILibro libroRepository;
    private IAutor autorRepository;

    //constructor
    public Principal() {
    }

    public Principal(ILibro repository, IAutor autorRepository) {
        this.libroRepository = repository;
        this.autorRepository = autorRepository;
    }


    //METODO DE LA OPCION 1 PARA BUSCAR UN LIBRO
    public void buscarYGuardarLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        String libroSolicitado = teclado.nextLine();

        //llamada a la api por titulo
        String json = consumoApi.obtenerDatos(URL_BASE + "?search=" + libroSolicitado.replace(" ", "+"));
        Datos datosApi = convierteDatos.obtenerDatos(json, Datos.class);

        Optional<Libro> libroEncontrado = datosApi.resultados().stream()
                .map(Libro::new)
                .findFirst();


        if (libroEncontrado.isPresent()) {

            Autor autor = autorRepository.findByNombreContainsIgnoreCase(libroEncontrado.get().getAutor().getNombre());

            if (autor == null) {
                // Si el autor no existe, créalo y asígnalo al libro
                Autor nuevoAutor = libroEncontrado.get().getAutor();
                //nuevoAutor.setNombre(nombreAutor);

                //me retorna el autor con el id, entonces si paso el autor al libro con el id, al guardarlo no me duplicara el autor!!!!
                autor = autorRepository.save(nuevoAutor);
            }

            Libro libro = libroEncontrado.get();

            try {
                libro.setAutor(autor);
                libroRepository.save(libro);
                System.out.println(libro);
            } catch (DataIntegrityViolationException ex) {
                // Manejar la excepción de restricción única
                System.out.println("El libro con este título ya existe en la base de datos.");
            }

        } else {
            System.out.println("el libro no se encuentra.");
        }

    }

    //METODO DE LA OPCION 2 PARA LISTAR LOS LIBROS DE LA BD
    public void listarLibros() {
        List<Libro> libros = libroRepository.findAll();
        libros.stream()
                .forEach(System.out::println);
    }

    //METODO DE LA OPCION 3 PARA LISTAR LOS AUTORES GUARDADOS EN LA BD
    public void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        autores.stream().forEach(System.out::println);
    }

    //METODO DE LA OPCION 4 PARA LISTAR LOS AUTORES VIVOS SEGUN AÑO
    public void listarAutoresVivos() {
        System.out.println("Ingrese el año vivo de autor(es) que desea buscar: (Ejemplo: 1559)");
        int fechaBuscada;
        String fecha;

        try {
            fechaBuscada = teclado.nextInt();

            fecha = String.valueOf(fechaBuscada);

            List<Autor> autoresVivos = autorRepository.buscarAutorVivo(fecha);

            if (autoresVivos.isEmpty()) {
                System.out.println("No se encontraron registros :'(.");
            } else {
                autoresVivos.stream().forEach(System.out::println);
            }

        } catch (Exception e) {
            System.out.println("Escriba una año valido, Ejemplo 1600");
            teclado.nextLine();
        }

    }

    //METODO OPCION 5 PARA LISTAR LIBROS POR IDIOMA
    public void listarIdiomas() {
        List<ILibroIdioma> idiomas = libroRepository.buscarIdiomasCount();
        idiomas.stream().forEach(i -> System.out.println(
                """
                        Codigo idioma: %s, Cantidad de libros: %d""".formatted(i.getIdioma(), i.getCount())
        ));

        System.out.println("Ingresa el codigo de idioma para listar los libros: (Ejemplo: es)");

        try {
            String codigo = teclado.nextLine();

            for (ILibroIdioma idioma : idiomas) {
                if (idioma.getIdioma().equals(codigo)) {
                    libroRepository.findByIdiomaEquals(codigo).stream().forEach(System.out::println);
                    return;
                } else if (codigo.length() > 2) {
                    throw new InputMismatchException("Los Codigos contiene 2 caracteres, Ejemplo: es");
                }
            }
            System.out.println("Codigo invalido!");
        } catch (InputMismatchException e){
            System.out.println(e.getMessage());
        }

    }

    //METODO OPCION 6
    public void top10Descargas(){
        System.out.println("Los TOP 10 con mas descargas son:");
        List<Libro> librosTop10 = libroRepository.findTop10ByOrderByCantidadDeDescargasDesc();
        librosTop10.stream().forEach(System.out::println);
    }

    public void muestraMenu(){
        var opcion = -1;

        while(opcion !=0){
            var menu = """
                    ---------------------------------------------
                                 MENU PRINCIPAL
                    ---------------------------------------------
                       1. BUSCAR LIBROS POR TITULO
                       2. LISTAR LIBROS REGISTRADOS
                       3. LISTAR AUTORES REGISTRADOS
                       4. LISTAR AUTORES VIVOS EN UN AÑO
                       5. LISTAR LIBROS POR IDIOMA
                       6. LISTAR EL TOP 10 LIBROS MAS DESCARGADOS
                       0. SALIR
                    ----------------------------------------------
                    """;

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarYGuardarLibro();
                    break;

                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarIdiomas();
                    break;
                case 6:
                    top10Descargas();
                    break;
                case 0:
                    System.out.println("Cerrar Aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }

        }
    }
}
