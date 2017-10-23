import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BusinessCapability } from './business-capability.model';
import { BusinessCapabilityService } from './business-capability.service';

@Injectable()
export class BusinessCapabilityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private businessCapabilityService: BusinessCapabilityService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.businessCapabilityService.find(id).subscribe((businessCapability) => {
                    businessCapability.dateAdded = this.datePipe
                        .transform(businessCapability.dateAdded, 'yyyy-MM-ddTHH:mm:ss');
                    businessCapability.dateModified = this.datePipe
                        .transform(businessCapability.dateModified, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.businessCapabilityModalRef(component, businessCapability);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.businessCapabilityModalRef(component, new BusinessCapability());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    businessCapabilityModalRef(component: Component, businessCapability: BusinessCapability): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.businessCapability = businessCapability;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
