import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Job } from 'app/shared/model/job.model';
import { JobService } from './job.service';
import { JobComponent } from './job.component';
import { JobDetailComponent } from './job-detail.component';
import { JobUpdateComponent } from './job-update.component';
import { JobDeletePopupComponent } from './job-delete-dialog.component';
import { IJob } from 'app/shared/model/job.model';

@Injectable({ providedIn: 'root' })
export class JobResolve implements Resolve<IJob> {
    constructor(private service: JobService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((job: HttpResponse<Job>) => job.body));
        }
        return of(new Job());
    }
}

export const jobRoute: Routes = [
    {
        path: 'job',
        component: JobComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Jobs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'job/:id/view',
        component: JobDetailComponent,
        resolve: {
            job: JobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Jobs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'job/new',
        component: JobUpdateComponent,
        resolve: {
            job: JobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Jobs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'job/:id/edit',
        component: JobUpdateComponent,
        resolve: {
            job: JobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Jobs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const jobPopupRoute: Routes = [
    {
        path: 'job/:id/delete',
        component: JobDeletePopupComponent,
        resolve: {
            job: JobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Jobs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
