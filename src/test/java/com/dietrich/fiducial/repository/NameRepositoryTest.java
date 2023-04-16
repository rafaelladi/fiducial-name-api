package com.dietrich.fiducial.repository;

import com.dietrich.fiducial.model.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class NameRepositoryTest {

    @Autowired
    private NameRepository nameRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    public void beforeEach() {
        nameRepository.deleteAll();
    }

    @Test
    public void testNameIsSavedAndFetched() {
        var name = new Name();
        name.setName("test");

        name = nameRepository.save(name);

        assertEquals("test", nameRepository.findById(name.getId()).get().getName());
    }

    @Test
    public void testReturnsTrueWhenNameExists() {
        var name = new Name("test");
        testEntityManager.persistAndFlush(name);

        assertTrue(nameRepository.existsByName("test"));
        assertFalse(nameRepository.existsByName("test2"));
    }

}
