/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyappTestModule } from '../../../test.module';
import { ApplicantUpdateComponent } from 'app/entities/applicant/applicant-update.component';
import { ApplicantService } from 'app/entities/applicant/applicant.service';
import { Applicant } from 'app/shared/model/applicant.model';

describe('Component Tests', () => {
    describe('Applicant Management Update Component', () => {
        let comp: ApplicantUpdateComponent;
        let fixture: ComponentFixture<ApplicantUpdateComponent>;
        let service: ApplicantService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyappTestModule],
                declarations: [ApplicantUpdateComponent]
            })
                .overrideTemplate(ApplicantUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApplicantUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApplicantService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Applicant(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.applicant = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Applicant();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.applicant = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
