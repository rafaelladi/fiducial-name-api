package com.dietrich.fiducial.controller;

import com.dietrich.fiducial.exception.NotFoundException;
import com.dietrich.fiducial.model.Name;
import com.dietrich.fiducial.model.NameCreationResponse;
import com.dietrich.fiducial.service.NameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * This is the controller used to manage Names.
 * Please see {@link com.dietrich.fiducial.model.Name} for more details.
 */
@RestController
@RequestMapping("names")
@RequiredArgsConstructor
public class NameController {

    private final NameService nameService;

    /**
     *
     * @param pageable A page request describing the page and size of the request
     * @return pages names
     */
    @GetMapping
    public ResponseEntity<Page<String>> pagedNames(final Pageable pageable) {
        return ResponseEntity.ok(nameService.findPaged(pageable));
    }

    /**
     * Return the name entity if it exists
     * @param id The id of the Name entity
     * @return The name entity for the given id
     * @throws NotFoundException is thrown if the name is not found
     */
    @GetMapping("{id}")
    public ResponseEntity<Name> getName(@PathVariable("id") final Long id) throws NotFoundException {
        return ResponseEntity.ok(nameService.findById(id));
    }

    /**
     * Checks if a name exists
     * @param name Name to be searched
     * @return true if name exists or false if it does not
     */
    @GetMapping("exists/{name}")
    public ResponseEntity<Boolean> exists(@PathVariable("name") final String name) {
        return ResponseEntity.ok(nameService.existsByName(name));
    }

    /**
     * Inserts a list of names
     * @param names List of names
     * @return List of names created and the number of names created
     */
    @PostMapping
    public ResponseEntity<NameCreationResponse> insertNames(@RequestBody final Set<String> names) {
        var response = nameService.createNames(names);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
