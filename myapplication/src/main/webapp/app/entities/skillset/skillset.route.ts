import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Skillset } from 'app/shared/model/skillset.model';
import { SkillsetService } from './skillset.service';
import { SkillsetComponent } from './skillset.component';
import { SkillsetDetailComponent } from './skillset-detail.component';
import { SkillsetUpdateComponent } from './skillset-update.component';
import { SkillsetDeletePopupComponent } from './skillset-delete-dialog.component';
import { ISkillset } from 'app/shared/model/skillset.model';

@Injectable({ providedIn: 'root' })
export class SkillsetResolve implements Resolve<ISkillset> {
    constructor(private service: SkillsetService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((skillset: HttpResponse<Skillset>) => skillset.body));
        }
        return of(new Skillset());
    }
}

export const skillsetRoute: Routes = [
    {
        path: 'skillset',
        component: SkillsetComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Skillsets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'skillset/:id/view',
        component: SkillsetDetailComponent,
        resolve: {
            skillset: SkillsetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Skillsets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'skillset/new',
        component: SkillsetUpdateComponent,
        resolve: {
            skillset: SkillsetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Skillsets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'skillset/:id/edit',
        component: SkillsetUpdateComponent,
        resolve: {
            skillset: SkillsetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Skillsets'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const skillsetPopupRoute: Routes = [
    {
        path: 'skillset/:id/delete',
        component: SkillsetDeletePopupComponent,
        resolve: {
            skillset: SkillsetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Skillsets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
