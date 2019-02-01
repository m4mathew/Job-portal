/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyappTestModule } from '../../../test.module';
import { SkillsetUpdateComponent } from 'app/entities/skillset/skillset-update.component';
import { SkillsetService } from 'app/entities/skillset/skillset.service';
import { Skillset } from 'app/shared/model/skillset.model';

describe('Component Tests', () => {
    describe('Skillset Management Update Component', () => {
        let comp: SkillsetUpdateComponent;
        let fixture: ComponentFixture<SkillsetUpdateComponent>;
        let service: SkillsetService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyappTestModule],
                declarations: [SkillsetUpdateComponent]
            })
                .overrideTemplate(SkillsetUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SkillsetUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SkillsetService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Skillset(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.skillset = entity;
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
                    const entity = new Skillset();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.skillset = entity;
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
