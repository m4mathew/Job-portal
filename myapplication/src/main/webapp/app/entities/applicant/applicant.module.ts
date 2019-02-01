import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyappSharedModule } from 'app/shared';
import {
    ApplicantComponent,
    ApplicantDetailComponent,
    ApplicantUpdateComponent,
    ApplicantDeletePopupComponent,
    ApplicantDeleteDialogComponent,
    applicantRoute,
    applicantPopupRoute
} from './';

const ENTITY_STATES = [...applicantRoute, ...applicantPopupRoute];

@NgModule({
    imports: [MyappSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ApplicantComponent,
        ApplicantDetailComponent,
        ApplicantUpdateComponent,
        ApplicantDeleteDialogComponent,
        ApplicantDeletePopupComponent
    ],
    entryComponents: [ApplicantComponent, ApplicantUpdateComponent, ApplicantDeleteDialogComponent, ApplicantDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyappApplicantModule {}
