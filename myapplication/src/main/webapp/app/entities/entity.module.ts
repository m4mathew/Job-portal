import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MyappJobModule } from './job/job.module';
import { MyappSkillsetModule } from './skillset/skillset.module';
import { MyappApplicantModule } from './applicant/applicant.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        MyappJobModule,
        MyappSkillsetModule,
        MyappApplicantModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyappEntityModule {}
