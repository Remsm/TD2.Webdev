package fr.isen.ticketapp.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/Poste")
public class PosteResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() throws IOException {
        String filePath = "src/main/resources/poste_informatique.json";
        return Files.readString(Paths.get(filePath));
    }
}
