package io.apigo.api.governance.service;

import io.apigo.api.governance.domain.BusinessCapability;
import io.apigo.api.governance.repository.BusinessCapabilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing BusinessCapability.
 */
@Service
@Transactional
public class BusinessCapabilityService {

    private final Logger log = LoggerFactory.getLogger(BusinessCapabilityService.class);

    private final BusinessCapabilityRepository businessCapabilityRepository;

    public BusinessCapabilityService(BusinessCapabilityRepository businessCapabilityRepository) {
        this.businessCapabilityRepository = businessCapabilityRepository;
    }

    /**
     * Save a businessCapability.
     *
     * @param businessCapability the entity to save
     * @return the persisted entity
     */
    public BusinessCapability save(BusinessCapability businessCapability) {
        log.debug("Request to save BusinessCapability : {}", businessCapability);
        return businessCapabilityRepository.save(businessCapability);
    }

    /**
     *  Get all the businessCapabilities.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BusinessCapability> findAll(Pageable pageable) {
        log.debug("Request to get all BusinessCapabilities");
        return businessCapabilityRepository.findAll(pageable);
    }

    /**
     *  Get one businessCapability by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BusinessCapability findOne(Long id) {
        log.debug("Request to get BusinessCapability : {}", id);
        return businessCapabilityRepository.findOne(id);
    }

    /**
     *  Delete the  businessCapability by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BusinessCapability : {}", id);
        businessCapabilityRepository.delete(id);
    }
}
