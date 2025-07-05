package fintech.project.service;

import fintech.project.entity.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferService {
    Offer addOffer(Offer offer);

    List<Offer> getAllOffer();

    Optional<Offer> getOfferById(Long id);

    Offer updateOffer(Long id, Offer offer);
}
