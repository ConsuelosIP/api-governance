import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BusinessCapabilityComponent } from './business-capability.component';
import { BusinessCapabilityDetailComponent } from './business-capability-detail.component';
import { BusinessCapabilityPopupComponent } from './business-capability-dialog.component';
import { BusinessCapabilityDeletePopupComponent } from './business-capability-delete-dialog.component';

@Injectable()
export class BusinessCapabilityResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const businessCapabilityRoute: Routes = [
    {
        path: 'business-capability',
        component: BusinessCapabilityComponent,
        resolve: {
            'pagingParams': BusinessCapabilityResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'governanceApp.businessCapability.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'business-capability/:id',
        component: BusinessCapabilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'governanceApp.businessCapability.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const businessCapabilityPopupRoute: Routes = [
    {
        path: 'business-capability-new',
        component: BusinessCapabilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'governanceApp.businessCapability.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'business-capability/:id/edit',
        component: BusinessCapabilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'governanceApp.businessCapability.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'business-capability/:id/delete',
        component: BusinessCapabilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'governanceApp.businessCapability.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
