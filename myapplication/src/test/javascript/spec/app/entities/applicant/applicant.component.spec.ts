/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyappTestModule } from '../../../test.module';
import { ApplicantComponent } from 'app/entities/applicant/applicant.component';
import { ApplicantService } from 'app/entities/applicant/applicant.service';
import { Applicant } from 'app/shared/model/applicant.model';

describe('Component Tests', () => {
    describe('Applicant Management Component', () => {
        let comp: ApplicantComponent;
        let fixture: ComponentFixture<ApplicantComponent>;
        let service: ApplicantService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyappTestModule],
                declarations: [ApplicantComponent],
                providers: []
            })
                .overrideTemplate(ApplicantComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApplicantComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApplicantService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Applicant(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.applicants[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
