package com.dietrich.fiducial.repository;

import com.dietrich.fiducial.model.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NameRepository extends JpaRepository<Name, Long> {

    boolean existsByName(String name);

}
