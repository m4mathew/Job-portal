import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Applicant } from 'app/shared/model/applicant.model';
import { ApplicantService } from './applicant.service';
import { ApplicantComponent } from './applicant.component';
import { ApplicantDetailComponent } from './applicant-detail.component';
import { ApplicantUpdateComponent } from './applicant-update.component';
import { ApplicantDeletePopupComponent } from './applicant-delete-dialog.component';
import { IApplicant } from 'app/shared/model/applicant.model';

@Injectable({ providedIn: 'root' })
export class ApplicantResolve implements Resolve<IApplicant> {
    constructor(private service: ApplicantService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((applicant: HttpResponse<Applicant>) => applicant.body));
        }
        return of(new Applicant());
    }
}

export const applicantRoute: Routes = [
    {
        path: 'applicant',
        component: ApplicantComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applicants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'applicant/:id/view',
        component: ApplicantDetailComponent,
        resolve: {
            applicant: ApplicantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applicants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'applicant/new',
        component: ApplicantUpdateComponent,
        resolve: {
            applicant: ApplicantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applicants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'applicant/:id/edit',
        component: ApplicantUpdateComponent,
        resolve: {
            applicant: ApplicantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applicants'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const applicantPopupRoute: Routes = [
    {
        path: 'applicant/:id/delete',
        component: ApplicantDeletePopupComponent,
        resolve: {
            applicant: ApplicantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applicants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
