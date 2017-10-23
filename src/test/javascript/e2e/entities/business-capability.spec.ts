import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('BusinessCapability e2e test', () => {

    let navBarPage: NavBarPage;
    let businessCapabilityDialogPage: BusinessCapabilityDialogPage;
    let businessCapabilityComponentsPage: BusinessCapabilityComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load BusinessCapabilities', () => {
        navBarPage.goToEntity('business-capability');
        businessCapabilityComponentsPage = new BusinessCapabilityComponentsPage();
        expect(businessCapabilityComponentsPage.getTitle()).toMatch(/governanceApp.businessCapability.home.title/);

    });

    it('should load create BusinessCapability dialog', () => {
        businessCapabilityComponentsPage.clickOnCreateButton();
        businessCapabilityDialogPage = new BusinessCapabilityDialogPage();
        expect(businessCapabilityDialogPage.getModalTitle()).toMatch(/governanceApp.businessCapability.home.createOrEditLabel/);
        businessCapabilityDialogPage.close();
    });

    it('should create and save BusinessCapabilities', () => {
        businessCapabilityComponentsPage.clickOnCreateButton();
        businessCapabilityDialogPage.setCommonNameInput('commonName');
        expect(businessCapabilityDialogPage.getCommonNameInput()).toMatch('commonName');
        businessCapabilityDialogPage.setDisplayNameInput('displayName');
        expect(businessCapabilityDialogPage.getDisplayNameInput()).toMatch('displayName');
        businessCapabilityDialogPage.setDescriptionInput('description');
        expect(businessCapabilityDialogPage.getDescriptionInput()).toMatch('description');
        businessCapabilityDialogPage.setSortOrderInput('5');
        expect(businessCapabilityDialogPage.getSortOrderInput()).toMatch('5');
        businessCapabilityDialogPage.setDateAddedInput(12310020012301);
        expect(businessCapabilityDialogPage.getDateAddedInput()).toMatch('2001-12-31T02:30');
        businessCapabilityDialogPage.setDateModifiedInput(12310020012301);
        expect(businessCapabilityDialogPage.getDateModifiedInput()).toMatch('2001-12-31T02:30');
        businessCapabilityDialogPage.parentSelectLastOption();
        businessCapabilityDialogPage.save();
        expect(businessCapabilityDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BusinessCapabilityComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-business-capability div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class BusinessCapabilityDialogPage {
    modalTitle = element(by.css('h4#myBusinessCapabilityLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    commonNameInput = element(by.css('input#field_commonName'));
    displayNameInput = element(by.css('input#field_displayName'));
    descriptionInput = element(by.css('input#field_description'));
    sortOrderInput = element(by.css('input#field_sortOrder'));
    dateAddedInput = element(by.css('input#field_dateAdded'));
    dateModifiedInput = element(by.css('input#field_dateModified'));
    parentSelect = element(by.css('select#field_parent'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setCommonNameInput = function (commonName) {
        this.commonNameInput.sendKeys(commonName);
    }

    getCommonNameInput = function () {
        return this.commonNameInput.getAttribute('value');
    }

    setDisplayNameInput = function (displayName) {
        this.displayNameInput.sendKeys(displayName);
    }

    getDisplayNameInput = function () {
        return this.displayNameInput.getAttribute('value');
    }

    setDescriptionInput = function (description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function () {
        return this.descriptionInput.getAttribute('value');
    }

    setSortOrderInput = function (sortOrder) {
        this.sortOrderInput.sendKeys(sortOrder);
    }

    getSortOrderInput = function () {
        return this.sortOrderInput.getAttribute('value');
    }

    setDateAddedInput = function (dateAdded) {
        this.dateAddedInput.sendKeys(dateAdded);
    }

    getDateAddedInput = function () {
        return this.dateAddedInput.getAttribute('value');
    }

    setDateModifiedInput = function (dateModified) {
        this.dateModifiedInput.sendKeys(dateModified);
    }

    getDateModifiedInput = function () {
        return this.dateModifiedInput.getAttribute('value');
    }

    parentSelectLastOption = function () {
        this.parentSelect.all(by.tagName('option')).last().click();
    }

    parentSelectOption = function (option) {
        this.parentSelect.sendKeys(option);
    }

    getParentSelect = function () {
        return this.parentSelect;
    }

    getParentSelectedOption = function () {
        return this.parentSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
