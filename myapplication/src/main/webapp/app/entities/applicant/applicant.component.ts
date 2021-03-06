import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IApplicant } from 'app/shared/model/applicant.model';
import { Principal } from 'app/core';
import { ApplicantService } from './applicant.service';

@Component({
    selector: 'jhi-applicant',
    templateUrl: './applicant.component.html'
})
export class ApplicantComponent implements OnInit, OnDestroy {
    applicants: IApplicant[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private applicantService: ApplicantService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.applicantService.query().subscribe(
            (res: HttpResponse<IApplicant[]>) => {
                this.applicants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInApplicants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IApplicant) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInApplicants() {
        this.eventSubscriber = this.eventManager.subscribe('applicantListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
