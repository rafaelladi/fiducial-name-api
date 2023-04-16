package com.dietrich.fiducial.repository;

import com.dietrich.fiducial.model.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NameRepository extends JpaRepository<Name, Long> {

    @Query("SELECT COUNT(n.id) > 0 FROM Name n WHERE n.name = ?1")
    boolean existsByName(String name);

}
