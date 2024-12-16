package fr.isen.ticketapp.interfaces.services;

import fr.isen.ticketapp.interfaces.models.Poste;

public interface PosteService {
    void createPoste(final Poste poste);

    void updatePoste(final int id, final Poste poste);

    void deletePoste(final int id);

    void getPoste();

}
