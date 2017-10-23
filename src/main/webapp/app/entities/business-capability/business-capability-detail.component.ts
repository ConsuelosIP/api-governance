import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { BusinessCapability } from './business-capability.model';
import { BusinessCapabilityService } from './business-capability.service';

@Component({
    selector: 'jhi-business-capability-detail',
    templateUrl: './business-capability-detail.component.html'
})
export class BusinessCapabilityDetailComponent implements OnInit, OnDestroy {

    businessCapability: BusinessCapability;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private businessCapabilityService: BusinessCapabilityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBusinessCapabilities();
    }

    load(id) {
        this.businessCapabilityService.find(id).subscribe((businessCapability) => {
            this.businessCapability = businessCapability;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBusinessCapabilities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'businessCapabilityListModification',
            (response) => this.load(this.businessCapability.id)
        );
    }
}
