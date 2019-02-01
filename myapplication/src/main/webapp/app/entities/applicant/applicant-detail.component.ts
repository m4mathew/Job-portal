import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IApplicant } from 'app/shared/model/applicant.model';

@Component({
    selector: 'jhi-applicant-detail',
    templateUrl: './applicant-detail.component.html'
})
export class ApplicantDetailComponent implements OnInit {
    applicant: IApplicant;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ applicant }) => {
            this.applicant = applicant;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
