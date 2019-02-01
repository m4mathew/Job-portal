/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyappTestModule } from '../../../test.module';
import { SkillsetComponent } from 'app/entities/skillset/skillset.component';
import { SkillsetService } from 'app/entities/skillset/skillset.service';
import { Skillset } from 'app/shared/model/skillset.model';

describe('Component Tests', () => {
    describe('Skillset Management Component', () => {
        let comp: SkillsetComponent;
        let fixture: ComponentFixture<SkillsetComponent>;
        let service: SkillsetService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyappTestModule],
                declarations: [SkillsetComponent],
                providers: []
            })
                .overrideTemplate(SkillsetComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SkillsetComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SkillsetService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Skillset(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.skillsets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
