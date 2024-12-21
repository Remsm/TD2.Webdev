package fr.isen.ticketapp.services;

import fr.isen.ticketapp.models.Poste;

public interface PosteService {
    void createPoste(final Poste poste);

    void updatePoste(final int id, final Poste poste);

    void deletePoste(final int id);

    void getPoste();

}
