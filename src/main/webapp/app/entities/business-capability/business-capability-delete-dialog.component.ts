import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BusinessCapability } from './business-capability.model';
import { BusinessCapabilityPopupService } from './business-capability-popup.service';
import { BusinessCapabilityService } from './business-capability.service';

@Component({
    selector: 'jhi-business-capability-delete-dialog',
    templateUrl: './business-capability-delete-dialog.component.html'
})
export class BusinessCapabilityDeleteDialogComponent {

    businessCapability: BusinessCapability;

    constructor(
        private businessCapabilityService: BusinessCapabilityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.businessCapabilityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'businessCapabilityListModification',
                content: 'Deleted an businessCapability'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-business-capability-delete-popup',
    template: ''
})
export class BusinessCapabilityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private businessCapabilityPopupService: BusinessCapabilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.businessCapabilityPopupService
                .open(BusinessCapabilityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
