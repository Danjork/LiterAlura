package com.alurachange.literalura.service;

import com.alurachange.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILibro extends JpaRepository<Libro, Long> {

    @Query("SELECT libro.idioma as idioma, COUNT(*) as count FROM Libro libro GROUP BY libro.idioma")
    List<ILibroIdioma> buscarIdiomasCount();


    List<Libro> findByIdiomaEquals(String codigo);

    List<Libro> findTop10ByOrderByCantidadDeDescargasDesc();

}
