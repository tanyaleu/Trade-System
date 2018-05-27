import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RequestTsComponent } from './request-ts.component';
import { RequestTsDetailComponent } from './request-ts-detail.component';
import { RequestTsPopupComponent } from './request-ts-dialog.component';
import { RequestTsDeletePopupComponent } from './request-ts-delete-dialog.component';
import { RequestTsProcessPopupComponent} from './request-ts-process-dialog.component';

export const requestRoute: Routes = [
    {
        path: 'request-ts',
        component: RequestTsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Requests'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'request-ts/:id',
        component: RequestTsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Requests'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const requestPopupRoute: Routes = [
    {
        path: 'request-ts-new',
        component: RequestTsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Requests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-ts/:id/edit',
        component: RequestTsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Requests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-ts/:id/delete',
        component: RequestTsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Requests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-ts/process',
        component: RequestTsProcessPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Requests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
