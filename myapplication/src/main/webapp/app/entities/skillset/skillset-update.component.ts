import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISkillset } from 'app/shared/model/skillset.model';
import { SkillsetService } from './skillset.service';

@Component({
    selector: 'jhi-skillset-update',
    templateUrl: './skillset-update.component.html'
})
export class SkillsetUpdateComponent implements OnInit {
    private _skillset: ISkillset;
    isSaving: boolean;

    constructor(private skillsetService: SkillsetService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ skillset }) => {
            this.skillset = skillset;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.skillset.id !== undefined) {
            this.subscribeToSaveResponse(this.skillsetService.update(this.skillset));
        } else {
            this.subscribeToSaveResponse(this.skillsetService.create(this.skillset));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISkillset>>) {
        result.subscribe((res: HttpResponse<ISkillset>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get skillset() {
        return this._skillset;
    }

    set skillset(skillset: ISkillset) {
        this._skillset = skillset;
    }
}
