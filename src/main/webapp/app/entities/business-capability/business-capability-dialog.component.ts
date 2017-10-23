import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BusinessCapability } from './business-capability.model';
import { BusinessCapabilityPopupService } from './business-capability-popup.service';
import { BusinessCapabilityService } from './business-capability.service';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-business-capability-dialog',
    templateUrl: './business-capability-dialog.component.html'
})
export class BusinessCapabilityDialogComponent implements OnInit {

    businessCapability: BusinessCapability;
    isSaving: boolean;

    businesscapabilities: BusinessCapability[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private businessCapabilityService: BusinessCapabilityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.businessCapabilityService.query()
            .subscribe((res: ResponseWrapper) => { this.businesscapabilities = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.businessCapability.id !== undefined) {
            this.subscribeToSaveResponse(
                this.businessCapabilityService.update(this.businessCapability));
        } else {
            this.subscribeToSaveResponse(
                this.businessCapabilityService.create(this.businessCapability));
        }
    }

    private subscribeToSaveResponse(result: Observable<BusinessCapability>) {
        result.subscribe((res: BusinessCapability) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: BusinessCapability) {
        this.eventManager.broadcast({ name: 'businessCapabilityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBusinessCapabilityById(index: number, item: BusinessCapability) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-business-capability-popup',
    template: ''
})
export class BusinessCapabilityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private businessCapabilityPopupService: BusinessCapabilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.businessCapabilityPopupService
                    .open(BusinessCapabilityDialogComponent as Component, params['id']);
            } else {
                this.businessCapabilityPopupService
                    .open(BusinessCapabilityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
