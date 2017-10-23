package io.apigo.api.governance.web.rest;

import io.apigo.api.governance.GovernanceApp;

import io.apigo.api.governance.domain.BusinessCapability;
import io.apigo.api.governance.repository.BusinessCapabilityRepository;
import io.apigo.api.governance.service.BusinessCapabilityService;
import io.apigo.api.governance.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static io.apigo.api.governance.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BusinessCapabilityResource REST controller.
 *
 * @see BusinessCapabilityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GovernanceApp.class)
public class BusinessCapabilityResourceIntTest {

    private static final String DEFAULT_COMMON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMMON_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    private static final ZonedDateTime DEFAULT_DATE_ADDED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_ADDED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_MODIFIED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_MODIFIED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BusinessCapabilityRepository businessCapabilityRepository;

    @Autowired
    private BusinessCapabilityService businessCapabilityService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBusinessCapabilityMockMvc;

    private BusinessCapability businessCapability;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BusinessCapabilityResource businessCapabilityResource = new BusinessCapabilityResource(businessCapabilityService);
        this.restBusinessCapabilityMockMvc = MockMvcBuilders.standaloneSetup(businessCapabilityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessCapability createEntity(EntityManager em) {
        BusinessCapability businessCapability = new BusinessCapability()
            .commonName(DEFAULT_COMMON_NAME)
            .displayName(DEFAULT_DISPLAY_NAME)
            .description(DEFAULT_DESCRIPTION)
            .sortOrder(DEFAULT_SORT_ORDER)
            .dateAdded(DEFAULT_DATE_ADDED)
            .dateModified(DEFAULT_DATE_MODIFIED);
        return businessCapability;
    }

    @Before
    public void initTest() {
        businessCapability = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessCapability() throws Exception {
        int databaseSizeBeforeCreate = businessCapabilityRepository.findAll().size();

        // Create the BusinessCapability
        restBusinessCapabilityMockMvc.perform(post("/api/business-capabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessCapability)))
            .andExpect(status().isCreated());

        // Validate the BusinessCapability in the database
        List<BusinessCapability> businessCapabilityList = businessCapabilityRepository.findAll();
        assertThat(businessCapabilityList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessCapability testBusinessCapability = businessCapabilityList.get(businessCapabilityList.size() - 1);
        assertThat(testBusinessCapability.getCommonName()).isEqualTo(DEFAULT_COMMON_NAME);
        assertThat(testBusinessCapability.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testBusinessCapability.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBusinessCapability.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testBusinessCapability.getDateAdded()).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testBusinessCapability.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
    }

    @Test
    @Transactional
    public void createBusinessCapabilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessCapabilityRepository.findAll().size();

        // Create the BusinessCapability with an existing ID
        businessCapability.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessCapabilityMockMvc.perform(post("/api/business-capabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessCapability)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessCapability in the database
        List<BusinessCapability> businessCapabilityList = businessCapabilityRepository.findAll();
        assertThat(businessCapabilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCommonNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessCapabilityRepository.findAll().size();
        // set the field null
        businessCapability.setCommonName(null);

        // Create the BusinessCapability, which fails.

        restBusinessCapabilityMockMvc.perform(post("/api/business-capabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessCapability)))
            .andExpect(status().isBadRequest());

        List<BusinessCapability> businessCapabilityList = businessCapabilityRepository.findAll();
        assertThat(businessCapabilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDisplayNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessCapabilityRepository.findAll().size();
        // set the field null
        businessCapability.setDisplayName(null);

        // Create the BusinessCapability, which fails.

        restBusinessCapabilityMockMvc.perform(post("/api/business-capabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessCapability)))
            .andExpect(status().isBadRequest());

        List<BusinessCapability> businessCapabilityList = businessCapabilityRepository.findAll();
        assertThat(businessCapabilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBusinessCapabilities() throws Exception {
        // Initialize the database
        businessCapabilityRepository.saveAndFlush(businessCapability);

        // Get all the businessCapabilityList
        restBusinessCapabilityMockMvc.perform(get("/api/business-capabilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessCapability.getId().intValue())))
            .andExpect(jsonPath("$.[*].commonName").value(hasItem(DEFAULT_COMMON_NAME.toString())))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(sameInstant(DEFAULT_DATE_ADDED))))
            .andExpect(jsonPath("$.[*].dateModified").value(hasItem(sameInstant(DEFAULT_DATE_MODIFIED))));
    }

    @Test
    @Transactional
    public void getBusinessCapability() throws Exception {
        // Initialize the database
        businessCapabilityRepository.saveAndFlush(businessCapability);

        // Get the businessCapability
        restBusinessCapabilityMockMvc.perform(get("/api/business-capabilities/{id}", businessCapability.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(businessCapability.getId().intValue()))
            .andExpect(jsonPath("$.commonName").value(DEFAULT_COMMON_NAME.toString()))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.dateAdded").value(sameInstant(DEFAULT_DATE_ADDED)))
            .andExpect(jsonPath("$.dateModified").value(sameInstant(DEFAULT_DATE_MODIFIED)));
    }

    @Test
    @Transactional
    public void getNonExistingBusinessCapability() throws Exception {
        // Get the businessCapability
        restBusinessCapabilityMockMvc.perform(get("/api/business-capabilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessCapability() throws Exception {
        // Initialize the database
        businessCapabilityService.save(businessCapability);

        int databaseSizeBeforeUpdate = businessCapabilityRepository.findAll().size();

        // Update the businessCapability
        BusinessCapability updatedBusinessCapability = businessCapabilityRepository.findOne(businessCapability.getId());
        updatedBusinessCapability
            .commonName(UPDATED_COMMON_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .description(UPDATED_DESCRIPTION)
            .sortOrder(UPDATED_SORT_ORDER)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateModified(UPDATED_DATE_MODIFIED);

        restBusinessCapabilityMockMvc.perform(put("/api/business-capabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBusinessCapability)))
            .andExpect(status().isOk());

        // Validate the BusinessCapability in the database
        List<BusinessCapability> businessCapabilityList = businessCapabilityRepository.findAll();
        assertThat(businessCapabilityList).hasSize(databaseSizeBeforeUpdate);
        BusinessCapability testBusinessCapability = businessCapabilityList.get(businessCapabilityList.size() - 1);
        assertThat(testBusinessCapability.getCommonName()).isEqualTo(UPDATED_COMMON_NAME);
        assertThat(testBusinessCapability.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testBusinessCapability.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBusinessCapability.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testBusinessCapability.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testBusinessCapability.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessCapability() throws Exception {
        int databaseSizeBeforeUpdate = businessCapabilityRepository.findAll().size();

        // Create the BusinessCapability

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBusinessCapabilityMockMvc.perform(put("/api/business-capabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessCapability)))
            .andExpect(status().isCreated());

        // Validate the BusinessCapability in the database
        List<BusinessCapability> businessCapabilityList = businessCapabilityRepository.findAll();
        assertThat(businessCapabilityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBusinessCapability() throws Exception {
        // Initialize the database
        businessCapabilityService.save(businessCapability);

        int databaseSizeBeforeDelete = businessCapabilityRepository.findAll().size();

        // Get the businessCapability
        restBusinessCapabilityMockMvc.perform(delete("/api/business-capabilities/{id}", businessCapability.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BusinessCapability> businessCapabilityList = businessCapabilityRepository.findAll();
        assertThat(businessCapabilityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessCapability.class);
        BusinessCapability businessCapability1 = new BusinessCapability();
        businessCapability1.setId(1L);
        BusinessCapability businessCapability2 = new BusinessCapability();
        businessCapability2.setId(businessCapability1.getId());
        assertThat(businessCapability1).isEqualTo(businessCapability2);
        businessCapability2.setId(2L);
        assertThat(businessCapability1).isNotEqualTo(businessCapability2);
        businessCapability1.setId(null);
        assertThat(businessCapability1).isNotEqualTo(businessCapability2);
    }
}
