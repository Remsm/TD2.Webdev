package fr.isen.ticketapp.interfaces.services;

import fr.isen.ticketapp.interfaces.models.PosteModel;

public interface PosteService {
    void createPoste(final PosteModel poste);

    void updatePoste(final int id, final PosteModel poste);

    void deletePoste(final int id);

    void getPoste();

}
