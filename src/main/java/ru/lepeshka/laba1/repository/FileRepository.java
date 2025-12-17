package ru.lepeshka.laba1.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.lepeshka.laba1.models.*;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByPath(String path);

    @Query("SELECT f FROM File f WHERE CONCAT(f.path, (" +
            "CASE WHEN f.path = '/' THEN '' ELSE '/' END" +
            "), f.name) = :path")
    File findByFullPath(
            @Param("path") String fullPath);

    @Query("SELECT COUNT(f) > 0 FROM File f WHERE CONCAT(f.path, (" +
            "CASE WHEN f.path = '/' THEN '' ELSE '/' END" +
            "), f.name) = :path")
    boolean findIfFileExist(
            @Param("path") String fullPath);
}
