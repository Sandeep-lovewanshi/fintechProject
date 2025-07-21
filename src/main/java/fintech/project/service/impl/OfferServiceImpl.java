package fintech.project.service.impl;

import fintech.project.entity.Enquiry;
import fintech.project.entity.Offer;
import fintech.project.repository.EnquiryRepository;
import fintech.project.repository.LoanProviderRepository;
import fintech.project.repository.OfferRepository;
import fintech.project.service.EnquiryService;
import fintech.project.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private EnquiryRepository enquiryRepository;

    @Autowired
    private EnquiryService enquiryService;

    @Autowired
    private LoanProviderRepository loanProviderRepository;

    //  Get Offer By ID
    @Override
    public Optional<Offer> getOfferById(Long id) {
        return offerRepository.findById(id);
    }

    //  Update Offer
    @Override
    public Offer updateOffer(Long id, Offer offer) {
        Optional<Offer> optionalOffer = offerRepository.findById(id);
        if (optionalOffer.isPresent()) {
            Offer existingOffer = optionalOffer.get();
            existingOffer.setLoanProviderId(offer.getLoanProviderId());
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
            throw new RuntimeException("Offer not found with id: " + id);
        }
    }

    //  Show Offers to Customer by Enquiry ID
    @Override
    public List<Offer> getOffersByEnquiryId(Long enquiryId) {
        return offerRepository.findByEnquiryId(enquiryId);
    }

    @Override
    public Offer acceptOffer(Long offerId) {
        Offer selectedOffer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
        Long enquiryId = selectedOffer.getEnquiryId();
      //add for status selected in enquiry
        Optional<Enquiry> optionalEnquiry = enquiryRepository.findById(enquiryId);
        Enquiry enquiry;
        if (optionalEnquiry.isPresent()) {
            enquiry = optionalEnquiry.get();
        } else {
            throw new RuntimeException("Enquiry not found");
        }

        enquiry.setStatus("Selected");
        enquiryRepository.save(enquiry);
        selectedOffer.setStatus("Selected");
        offerRepository.save(selectedOffer);

        // Reject other offers
        List<Offer> allOffers = offerRepository.findByEnquiryId(enquiryId);
        for (Offer offer : allOffers) {
            if (!offer.getId().equals(offerId)) {
                offer.setStatus("Pending");
            }
        }
        offerRepository.saveAll(allOffers);
        return selectedOffer;
    }

   //create 3 offer for particular enquiry
    @Override
    public List<Offer> assignOffersToCustomerBasedOnEnquiry(List<Offer> offerListRequest, Long enquiryId) {
        Optional<Enquiry> optionalEnquiry = enquiryRepository.findById(enquiryId);
        Enquiry enquiry;
        if (optionalEnquiry.isPresent()) {
            enquiry = optionalEnquiry.get();
        } else {
            throw new RuntimeException("Enquiry not found");
        }
        List<Offer> responseOfferList = new ArrayList<>();
        for (Offer offerObj : offerListRequest){
            Offer offer = new Offer();
            offer.setLoanProviderId(offerObj.getLoanProviderId());
            offer.setLoanAmount(offerObj.getLoanAmount());
            offer.setInterestRate(offerObj.getInterestRate());
            offer.setTermMonths(offerObj.getTermMonths());
            offer.setLoanType(offerObj.getLoanType());
            offer.setEnquiryId(enquiryId);
            offer.setStatus("PENDING");
            offer.setValidFrom(LocalDate.now());
            offer.setValidTo(offerObj.getValidTo());
            offer.setCreatedDate(LocalDate.now());
            offer.setUpdatedDate(LocalDate.now());
            Offer savedOffer = offerRepository.save(offer);
            enquiry.setStatus("OfferProvided");
            enquiryRepository.save(enquiry);
            responseOfferList.add(savedOffer);
        }
        return responseOfferList;
    }

    public List<Offer> getAllSelectedOffers() {

        return offerRepository.findByStatus("SELECTED");
    }

}
