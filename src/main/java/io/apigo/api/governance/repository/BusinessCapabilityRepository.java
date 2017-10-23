package io.apigo.api.governance.repository;

import io.apigo.api.governance.domain.BusinessCapability;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BusinessCapability entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessCapabilityRepository extends JpaRepository<BusinessCapability, Long> {

}
