package fintech.project.service;

import fintech.project.entity.Offer;
import java.util.List;
import java.util.Optional;

public interface OfferService {

    Optional<Offer> getOfferById(Long id);

    Offer updateOffer(Long id, Offer offer);

    List<Offer> getOffersByEnquiryId(Long enquiryId);

    Offer acceptOffer(Long offerId);

    List<Offer> assignOffersToCustomerBasedOnEnquiry(List<Offer> list, Long enquiryId);

}
