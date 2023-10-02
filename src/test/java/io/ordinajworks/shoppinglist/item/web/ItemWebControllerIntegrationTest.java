package io.ordinajworks.shoppinglist.item.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
public class ItemWebControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(roles = {"ADMIN", "DEV"})
    @Test
    public void addItem() throws Exception {
        var resource = """
                {
                    "name": "item",
                    "amount": 2
                }
                """;

        mockMvc.perform(post("/item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(resource))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, startsWith("/item/")))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.order").value(0))
                .andExpect(jsonPath("$.name").value("item"))
                .andExpect(jsonPath("$.amount").value(2));
    }

    @WithMockUser(roles = {"ADMIN", "DEV"})
    @Test
    public void addItemBigName() throws Exception {
        var resource = """
                {
                    "name": "item with big name",
                    "amount": 2
                }
                """;

        mockMvc.perform(post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resource))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, startsWith("/item/")))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.order").value(0))
                .andExpect(jsonPath("$.name").value("item with big name"))
                .andExpect(jsonPath("$.amount").value(2));
    }

    @WithMockUser(roles = {"ADMIN", "DEV"})
    @Test
    public void getItemById() throws Exception {
        mockMvc.perform(get("/item/c570dada-e134-44ad-84ef-a2e18d9c9f2d")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("c570dada-e134-44ad-84ef-a2e18d9c9f2d"))
                .andExpect(jsonPath("$.order").value(0))
                .andExpect(jsonPath("$.name").value("Toilet paper"))
                .andExpect(jsonPath("$.amount").value(1));
    }

    @WithMockUser(roles = {"ADMIN", "DEV"})
    @Test
    public void getItemByIdNotFound() throws Exception {
        mockMvc.perform(get("/item/3a93fe55-27e9-4836-ba84-08be67cd4996")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = {"ADMIN", "DEV"})
    @Test
    public void getItem() throws Exception {
        mockMvc.perform(get("/item")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNotEmpty());
    }
}
