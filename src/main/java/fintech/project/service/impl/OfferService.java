package fintech.project.service.impl;
import fintech.project.entity.Offer;
import fintech.project.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService implements fintech.project.service.OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Override
    public Offer addOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public List<Offer> getAllOffer() {
        return offerRepository.findAll();
    }

    @Override
    public Optional<Offer> getOfferById(Long id) {
        return offerRepository.findById(id);
    }

    @Override
    public Offer updateOffer(Long id, Offer offer) {
        Optional<Offer> OpOffer = offerRepository.findById(id);
        if (OpOffer.isPresent()) {

            Offer existingOffer = OpOffer.get();
            existingOffer.setAgentId(offer.getAgentId());
            existingOffer.setProviderId(offer.getProviderId());
            existingOffer.setEnquiryId(offer.getEnquiryId());
            existingOffer.setLoanType(offer.getLoanType());
            existingOffer.setLoanAmount(offer.getLoanAmount());
            existingOffer.setInterestRate(offer.getInterestRate());
            existingOffer.setTermMonths(offer.getTermMonths());
            existingOffer.setValidFrom(offer.getValidFrom());
            existingOffer.setValidTo(offer.getValidTo());
            existingOffer.setStatus(offer.getStatus());
            return offerRepository.save(existingOffer);
        } else {
            throw new RuntimeException("offer not found with id: " + id);
        }
    }
}