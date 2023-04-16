package com.dietrich.fiducial.service;

import com.dietrich.fiducial.exception.NotFoundException;
import com.dietrich.fiducial.model.Name;
import com.dietrich.fiducial.model.NameCreationResponse;
import com.dietrich.fiducial.repository.NameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class NameServiceImpl implements NameService {

    private final NameRepository nameRepository;

    /**
     * Fetches a name in the database
     * @param id The id of the Name entity
     * @return The name if it exists
     * @throws NotFoundException is thrown if the name is not found
     */
    @Override
    public Name findById(Long id) throws NotFoundException {
        return nameRepository.findById(id).orElseThrow(() -> new NotFoundException("Name", "id", id));
    }

    /**
     * Checks if a name exists in the database
     * @param name The name to be searched
     * @return true if the name exists and false if it does not
     */
    @Override
    public boolean existsByName(String name) {
        return nameRepository.existsByName(name);
    }

    /**
     * Received a list of names and inserts them, if the name exists in the database it implicitly dealt with, not causing an error
     * @param names List of names to be saved.
     * @return List of names saved and the number of names inserted.
     */
    @Override
    public NameCreationResponse createNames(Set<String> names) {
        var response = new NameCreationResponse();
        names.forEach(name -> {
            try {
                nameRepository.save(new Name(name));
                log.info(String.format("Name %s created", name));
                response.addName(name);
            } catch (DataIntegrityViolationException e) {
                log.warn(String.format("Name \"%s\" already exists, skipped creation", name));
            }
        });
        return response;
    }

    /**
     * Finds a list of paged list of names
     * @param pageable Described the page and size of the request
     * @return A list of names and more information about the page
     */
    @Override
    public Page<String> findPaged(Pageable pageable) {
        return nameRepository.findAll(pageable).map(Name::getName);
    }
}
