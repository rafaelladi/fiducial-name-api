package com.dietrich.fiducial.controller;

import com.dietrich.fiducial.exception.NotFoundException;
import com.dietrich.fiducial.model.Name;
import com.dietrich.fiducial.model.NameCreationResponse;
import com.dietrich.fiducial.service.NameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NameController.class)
public class NameControllerTest {

    @MockBean
    private NameService nameService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testExistingNameIsReturned() throws Exception {
        when(nameService.findById(any())).thenReturn(new Name("test"));

        mockMvc.perform(get("/names/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"));
    }

    @Test
    public void testIsNotOkWhenNameDoesNotExist() throws Exception {
        when(nameService.findById(any())).thenThrow(new NotFoundException("Name", "id", 1L));

        mockMvc.perform(get("/names/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testNamesArePaged() throws Exception {
        List<String> names = new ArrayList<>();
        names.add("test1");
        Page<String> page = new PageImpl<>(names, PageRequest.of(0, 10), 1);
        when(nameService.findPaged(any())).thenReturn(page);

        mockMvc.perform(get("/names?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    public void testReturnsTrueWhenNameExists() throws Exception {
        when(nameService.existsByName(any())).thenReturn(true);

        mockMvc.perform(get("/names/exists/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void testReturnsFalseWhenNameDoesNotExist() throws Exception {
        when(nameService.existsByName(any())).thenReturn(false);

        mockMvc.perform(get("/names/exists/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    public void testNamesAreInserted() throws Exception {
        var response = new NameCreationResponse();
        response.addName("test");
        when(nameService.createNames(any())).thenReturn(response);

        mockMvc.perform(post("/names")
                        .content("[\"test\"]")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.names[0]").value("test"));
    }


}
