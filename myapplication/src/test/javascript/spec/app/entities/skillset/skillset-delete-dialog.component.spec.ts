/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyappTestModule } from '../../../test.module';
import { SkillsetDeleteDialogComponent } from 'app/entities/skillset/skillset-delete-dialog.component';
import { SkillsetService } from 'app/entities/skillset/skillset.service';

describe('Component Tests', () => {
    describe('Skillset Management Delete Component', () => {
        let comp: SkillsetDeleteDialogComponent;
        let fixture: ComponentFixture<SkillsetDeleteDialogComponent>;
        let service: SkillsetService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyappTestModule],
                declarations: [SkillsetDeleteDialogComponent]
            })
                .overrideTemplate(SkillsetDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SkillsetDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SkillsetService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
