import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IJob } from 'app/shared/model/job.model';
import { Principal } from 'app/core';
import { JobService } from './job.service';

@Component({
    selector: 'jhi-job',
    templateUrl: './job.component.html'
})
export class JobComponent implements OnInit, OnDestroy {
    jobs: IJob[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jobService: JobService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.jobService.query().subscribe(
            (res: HttpResponse<IJob[]>) => {
                this.jobs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInJobs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IJob) {
        return item.id;
    }

    registerChangeInJobs() {
        this.eventSubscriber = this.eventManager.subscribe('jobListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
