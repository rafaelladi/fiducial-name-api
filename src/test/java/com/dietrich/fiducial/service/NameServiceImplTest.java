package com.dietrich.fiducial.service;

import com.dietrich.fiducial.exception.NotFoundException;
import com.dietrich.fiducial.model.Name;
import com.dietrich.fiducial.repository.NameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NameServiceImplTest {

    private NameService nameService;
    private NameRepository nameRepositoryMock;

    @BeforeEach
    public void beforeEach() {
        nameRepositoryMock = Mockito.mock(NameRepository.class);
        nameService = new NameServiceImpl(nameRepositoryMock);
    }

    @Test
    public void testNameIsFoundInDatabase() throws NotFoundException {
        when(nameRepositoryMock.findById(any())).thenReturn(Optional.of(new Name("test")));
        var name = nameService.findById(1L);

        assertEquals("test", name.getName());
    }

    @Test
    public void testNameIsNotFoundInDatabase() {
        when(nameRepositoryMock.findById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> nameService.findById(1L));
    }

    @Test
    public void testNameExistsInDatabase() {
        when(nameRepositoryMock.existsByName(any())).thenReturn(true);

        assertTrue(nameService.existsByName("test"));
    }

    @Test
    public void testNameDoesNotExistInDatabase() {
        when(nameRepositoryMock.existsByName(any())).thenReturn(false);

        assertFalse(nameService.existsByName("test"));
    }

    @Test
    public void testNamesAreInserted() {
        var test1 = new Name("test1");
        var test2 = new Name("test2");

        var names = Set.of(test1.getName(), test2.getName());

        nameService.createNames(names);
        verify(nameRepositoryMock, times(1)).save(test1);
        verify(nameRepositoryMock, times(1)).save(test2);
    }

    @Test
    public void testDuplicateNameIsSkipped() {
        var test1 = new Name("test1");
        var test2 = new Name("test2");

        var names = Set.of(test1.getName(), test2.getName());

        when(nameRepositoryMock.save(test1)).thenThrow(DataIntegrityViolationException.class);

        var response = nameService.createNames(names);
        assertEquals(1, response.getTotal());
    }

    @Test
    public void testNamesArePaged() {
        var pageReq = PageRequest.of(0, 10);
        var page = new PageImpl<Name>(new ArrayList<>(), pageReq, 15);
        when(nameRepositoryMock.findAll(pageReq)).thenReturn(page);
        var response = nameService.findPaged(pageReq);

        assertEquals(15L, response.getTotalElements());
        assertEquals(2, response.getTotalPages());
    }

}
