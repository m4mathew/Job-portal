/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyappTestModule } from '../../../test.module';
import { SkillsetDetailComponent } from 'app/entities/skillset/skillset-detail.component';
import { Skillset } from 'app/shared/model/skillset.model';

describe('Component Tests', () => {
    describe('Skillset Management Detail Component', () => {
        let comp: SkillsetDetailComponent;
        let fixture: ComponentFixture<SkillsetDetailComponent>;
        const route = ({ data: of({ skillset: new Skillset(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyappTestModule],
                declarations: [SkillsetDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SkillsetDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SkillsetDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.skillset).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
