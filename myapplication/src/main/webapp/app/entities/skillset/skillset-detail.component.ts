import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISkillset } from 'app/shared/model/skillset.model';

@Component({
    selector: 'jhi-skillset-detail',
    templateUrl: './skillset-detail.component.html'
})
export class SkillsetDetailComponent implements OnInit {
    skillset: ISkillset;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ skillset }) => {
            this.skillset = skillset;
        });
    }

    previousState() {
        window.history.back();
    }
}
