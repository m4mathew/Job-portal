import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IApplicant } from 'app/shared/model/applicant.model';
import { ApplicantService } from './applicant.service';
import { IJob } from 'app/shared/model/job.model';
import { JobService } from 'app/entities/job';

@Component({
    selector: 'jhi-applicant-update',
    templateUrl: './applicant-update.component.html'
})
export class ApplicantUpdateComponent implements OnInit {
    private _applicant: IApplicant;
    isSaving: boolean;

    jobs: IJob[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private applicantService: ApplicantService,
        private jobService: JobService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ applicant }) => {
            this.applicant = applicant;
        });
        this.jobService.query().subscribe(
            (res: HttpResponse<IJob[]>) => {
                this.jobs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.applicant.id !== undefined) {
            this.subscribeToSaveResponse(this.applicantService.update(this.applicant));
        } else {
            this.subscribeToSaveResponse(this.applicantService.create(this.applicant));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IApplicant>>) {
        result.subscribe((res: HttpResponse<IApplicant>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackJobById(index: number, item: IJob) {
        return item.id;
    }
    get applicant() {
        return this._applicant;
    }

    set applicant(applicant: IApplicant) {
        this._applicant = applicant;
    }
}
