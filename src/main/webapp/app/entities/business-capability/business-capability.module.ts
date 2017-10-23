import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GovernanceSharedModule } from '../../shared';
import {
    BusinessCapabilityService,
    BusinessCapabilityPopupService,
    BusinessCapabilityComponent,
    BusinessCapabilityDetailComponent,
    BusinessCapabilityDialogComponent,
    BusinessCapabilityPopupComponent,
    BusinessCapabilityDeletePopupComponent,
    BusinessCapabilityDeleteDialogComponent,
    businessCapabilityRoute,
    businessCapabilityPopupRoute,
    BusinessCapabilityResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...businessCapabilityRoute,
    ...businessCapabilityPopupRoute,
];

@NgModule({
    imports: [
        GovernanceSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BusinessCapabilityComponent,
        BusinessCapabilityDetailComponent,
        BusinessCapabilityDialogComponent,
        BusinessCapabilityDeleteDialogComponent,
        BusinessCapabilityPopupComponent,
        BusinessCapabilityDeletePopupComponent,
    ],
    entryComponents: [
        BusinessCapabilityComponent,
        BusinessCapabilityDialogComponent,
        BusinessCapabilityPopupComponent,
        BusinessCapabilityDeleteDialogComponent,
        BusinessCapabilityDeletePopupComponent,
    ],
    providers: [
        BusinessCapabilityService,
        BusinessCapabilityPopupService,
        BusinessCapabilityResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GovernanceBusinessCapabilityModule {}
