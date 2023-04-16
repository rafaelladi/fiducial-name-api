package com.dietrich.fiducial.service;

import com.dietrich.fiducial.exception.NotFoundException;
import com.dietrich.fiducial.model.Name;
import com.dietrich.fiducial.model.NameCreationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface NameService {

    Name findById(Long id) throws NotFoundException;

    boolean existsByName(String name);

    NameCreationResponse createNames(Set<String> names);

    Page<String> findPaged(Pageable pageable);
}
