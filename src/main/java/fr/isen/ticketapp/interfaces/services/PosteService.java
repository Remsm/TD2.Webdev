package fr.isen.ticketapp.interfaces.services;

import fr.isen.ticketapp.interfaces.models.PosteModel;

import java.util.List;

public interface PosteService {

    List<PosteModel> getJSONPostes();

    List<PosteModel> getPostes();

    PosteModel getPosteById(final int id);

    PosteModel addPoste(final PosteModel posteModel);

    PosteModel modifyPoste(final PosteModel posteModel);

    void deletePoste(final int id);



}
