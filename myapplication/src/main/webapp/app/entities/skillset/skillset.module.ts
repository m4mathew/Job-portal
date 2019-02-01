import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyappSharedModule } from 'app/shared';
import {
    SkillsetComponent,
    SkillsetDetailComponent,
    SkillsetUpdateComponent,
    SkillsetDeletePopupComponent,
    SkillsetDeleteDialogComponent,
    skillsetRoute,
    skillsetPopupRoute
} from './';

const ENTITY_STATES = [...skillsetRoute, ...skillsetPopupRoute];

@NgModule({
    imports: [MyappSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SkillsetComponent,
        SkillsetDetailComponent,
        SkillsetUpdateComponent,
        SkillsetDeleteDialogComponent,
        SkillsetDeletePopupComponent
    ],
    entryComponents: [SkillsetComponent, SkillsetUpdateComponent, SkillsetDeleteDialogComponent, SkillsetDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyappSkillsetModule {}
