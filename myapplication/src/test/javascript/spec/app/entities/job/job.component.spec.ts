/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyappTestModule } from '../../../test.module';
import { JobComponent } from 'app/entities/job/job.component';
import { JobService } from 'app/entities/job/job.service';
import { Job } from 'app/shared/model/job.model';

describe('Component Tests', () => {
    describe('Job Management Component', () => {
        let comp: JobComponent;
        let fixture: ComponentFixture<JobComponent>;
        let service: JobService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyappTestModule],
                declarations: [JobComponent],
                providers: []
            })
                .overrideTemplate(JobComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(JobComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Job(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.jobs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
