package fr.isen.ticketapp.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.isen.ticketapp.interfaces.models.TicketModel;
import fr.isen.ticketapp.interfaces.services.TicketService;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicketServiceImpl implements TicketService {

    @Override
    public List<TicketModel> getTickets() {
        return getTicketFromJsonFile(TicketModel[].class, "ticket.json");
    }

    @Override
    public TicketModel getTicketById(int id) {
        // Implémentez cette méthode pour récupérer un ticket par son ID
        return null;
    }

    @Override
    public void removeTicket(int id) {
        // Implémentez cette méthode pour supprimer un ticket
    }

    // Méthode générique pour charger le fichier JSON et le convertir en liste d'objets
    public <T> List<T> getTicketFromJsonFile(Class<T[]> cls, String filepath) {
        List<T> elements = new ArrayList<>();
        try {
            // Créer un ObjectMapper pour désérialiser le JSON
            ObjectMapper mapper = new ObjectMapper();

            // Charger le fichier JSON à partir des ressources
            URL res = getClass().getClassLoader().getResource(filepath);
            File file = new File(res.getFile());

            // Désérialiser le JSON en tableau, puis le convertir en liste
            T[] array = mapper.readValue(file, cls);  // Désérialisation directe en tableau
            elements = Arrays.asList(array);  // Convertir le tableau en liste

        } catch (Exception ex) {
            System.out.println("Erreur lors de la lecture du fichier JSON : " + ex.getMessage());
            ex.printStackTrace();
        }

        return elements;
    }
}
