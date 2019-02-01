import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISkillset } from 'app/shared/model/skillset.model';
import { SkillsetService } from './skillset.service';

@Component({
    selector: 'jhi-skillset-delete-dialog',
    templateUrl: './skillset-delete-dialog.component.html'
})
export class SkillsetDeleteDialogComponent {
    skillset: ISkillset;

    constructor(private skillsetService: SkillsetService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.skillsetService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'skillsetListModification',
                content: 'Deleted an skillset'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-skillset-delete-popup',
    template: ''
})
export class SkillsetDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ skillset }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SkillsetDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.skillset = skillset;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
