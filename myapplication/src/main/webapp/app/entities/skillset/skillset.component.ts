import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISkillset } from 'app/shared/model/skillset.model';
import { Principal } from 'app/core';
import { SkillsetService } from './skillset.service';

@Component({
    selector: 'jhi-skillset',
    templateUrl: './skillset.component.html'
})
export class SkillsetComponent implements OnInit, OnDestroy {
    skillsets: ISkillset[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private skillsetService: SkillsetService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.skillsetService.query().subscribe(
            (res: HttpResponse<ISkillset[]>) => {
                this.skillsets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSkillsets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISkillset) {
        return item.id;
    }

    registerChangeInSkillsets() {
        this.eventSubscriber = this.eventManager.subscribe('skillsetListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
