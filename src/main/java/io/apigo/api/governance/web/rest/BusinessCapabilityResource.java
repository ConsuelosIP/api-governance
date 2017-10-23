package io.apigo.api.governance.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.apigo.api.governance.domain.BusinessCapability;
import io.apigo.api.governance.service.BusinessCapabilityService;
import io.apigo.api.governance.web.rest.errors.BadRequestAlertException;
import io.apigo.api.governance.web.rest.util.HeaderUtil;
import io.apigo.api.governance.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BusinessCapability.
 */
@RestController
@RequestMapping("/api")
public class BusinessCapabilityResource {

    private final Logger log = LoggerFactory.getLogger(BusinessCapabilityResource.class);

    private static final String ENTITY_NAME = "businessCapability";

    private final BusinessCapabilityService businessCapabilityService;

    public BusinessCapabilityResource(BusinessCapabilityService businessCapabilityService) {
        this.businessCapabilityService = businessCapabilityService;
    }

    /**
     * POST  /business-capabilities : Create a new businessCapability.
     *
     * @param businessCapability the businessCapability to create
     * @return the ResponseEntity with status 201 (Created) and with body the new businessCapability, or with status 400 (Bad Request) if the businessCapability has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/business-capabilities")
    @Timed
    public ResponseEntity<BusinessCapability> createBusinessCapability(@Valid @RequestBody BusinessCapability businessCapability) throws URISyntaxException {
        log.debug("REST request to save BusinessCapability : {}", businessCapability);
        if (businessCapability.getId() != null) {
            throw new BadRequestAlertException("A new businessCapability cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessCapability result = businessCapabilityService.save(businessCapability);
        return ResponseEntity.created(new URI("/api/business-capabilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /business-capabilities : Updates an existing businessCapability.
     *
     * @param businessCapability the businessCapability to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated businessCapability,
     * or with status 400 (Bad Request) if the businessCapability is not valid,
     * or with status 500 (Internal Server Error) if the businessCapability couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/business-capabilities")
    @Timed
    public ResponseEntity<BusinessCapability> updateBusinessCapability(@Valid @RequestBody BusinessCapability businessCapability) throws URISyntaxException {
        log.debug("REST request to update BusinessCapability : {}", businessCapability);
        if (businessCapability.getId() == null) {
            return createBusinessCapability(businessCapability);
        }
        BusinessCapability result = businessCapabilityService.save(businessCapability);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, businessCapability.getId().toString()))
            .body(result);
    }

    /**
     * GET  /business-capabilities : get all the businessCapabilities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of businessCapabilities in body
     */
    @GetMapping("/business-capabilities")
    @Timed
    public ResponseEntity<List<BusinessCapability>> getAllBusinessCapabilities(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of BusinessCapabilities");
        Page<BusinessCapability> page = businessCapabilityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/business-capabilities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /business-capabilities/:id : get the "id" businessCapability.
     *
     * @param id the id of the businessCapability to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the businessCapability, or with status 404 (Not Found)
     */
    @GetMapping("/business-capabilities/{id}")
    @Timed
    public ResponseEntity<BusinessCapability> getBusinessCapability(@PathVariable Long id) {
        log.debug("REST request to get BusinessCapability : {}", id);
        BusinessCapability businessCapability = businessCapabilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(businessCapability));
    }

    /**
     * DELETE  /business-capabilities/:id : delete the "id" businessCapability.
     *
     * @param id the id of the businessCapability to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/business-capabilities/{id}")
    @Timed
    public ResponseEntity<Void> deleteBusinessCapability(@PathVariable Long id) {
        log.debug("REST request to delete BusinessCapability : {}", id);
        businessCapabilityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
