
![Literalura](https://github.com/Danjork/literalura/assets/40806603/0988d364-b17e-4844-ba5d-d5c00d5c38ce)


Literalura aplicación Backend para buscar libros, que usa como API Gutendex para obtener la información.

## :hammer:Funcionalidades del proyecto

- `Menu Literalura`
  
  ![image](https://github.com/Danjork/literalura/assets/40806603/fd80b356-e9f8-444b-8611-8665114bdc0a)

- `1`: Busca libros por titulo: Permite la busqueda del libro por su titulo e inserta la información en la BD.
- `2`: Lista libros registrados: Muestra libros guardados en la BD.
- `3`: Lista autores registrados: Muestra los autores guardados en la BD.
- `4`: Lista autores vivo en un año: Muestra el listado de autores que estaba vivo en una año especifico.
- `5`: Lista libros por idioma: Permite buscar libro por idioma(ES,EN,FR,PT).
- `6`: Lista Top 10 libros más descargados: Muestra los 10 libros mas busccados.
- `0`: Salir: cierra la App

## Requisitos

- Java 17
- Spring Boot 3.2.4
- PostgreSQL
- Maven

  ## API
  [Gutendex API](https://gutendex.com).

 ### Instalación 🔧
 - Descargar el proyecto 
 git clone https://github.com/tu-usuario/tu-proyecto.git
 cd tu-proyecto
 - Configura la base de datos PostgreSQL y crear BD y tablas
   
![image](https://github.com/Danjork/literalura/assets/40806603/cfd28711-8ce4-42e9-9037-e55df35051ef)


 - Configura las credenciales en el archivo application.properties.

- spring.application.name=literalura
- spring.datasource.url=jdbc:postgresql://${DB_HOST}/literalura
- spring.datasource.username=${DB_USER}
- spring.datasource.password=${DB_PASSWORD}
- spring.datasource.driver-class-name=org.postgresql.Driver
- hibernate.dialect=org.hibernate.dialect.HSQLDialect
- spring.jpa.hibernate.ddl-auto=update
- spring.jpa.show-sql=true
- spring.jpa.format-sql=true


Ejecuta la aplicación:

 

  ## Contribución
![badge literalura](https://github.com/Danjork/literalura/assets/40806603/2ed1004f-6c05-450f-84d2-6b3d848091c9)

## Autores ✒️

  Danjork
