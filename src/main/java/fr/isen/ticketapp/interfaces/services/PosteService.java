package fr.isen.ticketapp.interfaces.services;

import fr.isen.ticketapp.interfaces.models.PosteModel;

import java.util.List;

public interface PosteService {

    List<PosteModel> getJSONPostes();

    PosteModel addPoste(final PosteModel posteModel);

    void createPoste(final PosteModel poste);

    void updatePoste(final int id, final PosteModel poste);

    void deletePoste(final int id);

    void getPoste();

}
