package com.crostifocaccias.crosti_focaccias.services;

import java.util.List;

import com.crostifocaccias.crosti_focaccias.entities.Focaccia;

public interface IFocacciaService {
    Focaccia createFocaccia(Focaccia focaccia);

    List<Focaccia> getAllFocaccias();

    Focaccia getFocacciaById(Long id);

    Focaccia updateFocaccia(Long id, Focaccia focaccia);

    boolean deleteFocaccia(Long id);

    List<Focaccia> searchByNameContaining(String keyword);

    List<Focaccia> getFocacciasByPriceLessThan(Double price);

    List<Focaccia> getFocacciasByPriceGreaterThan(Double price);

    List<Focaccia> getFocacciasByImageUrl(String imageUrl);

    List<Focaccia> getFeaturedFocaccias();

}
