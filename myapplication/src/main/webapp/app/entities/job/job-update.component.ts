import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IJob } from 'app/shared/model/job.model';
import { JobService } from './job.service';
import { ISkillset } from 'app/shared/model/skillset.model';
import { SkillsetService } from 'app/entities/skillset/skillset.service';

@Component({
    selector: 'jhi-job-update',
    templateUrl: './job-update.component.html'

    
})
export class JobUpdateComponent implements OnInit {
    private _job: IJob;
    isSaving: boolean;
    workTypeArray: Array<string>;
    skillsArray: Array<string>;
    savedSkillsArry: Array<string>;
    savedDesiredskillsArry: Array<string>;
    skillStr:string;
    page1:boolean;
    page2:boolean;
    page3:boolean;
    skillsets: ISkillset[];
    constructor(private jobService: JobService,private skillsetService: SkillsetService, 
    		private activatedRoute: ActivatedRoute,  private jhiAlertService: JhiAlertService ) {}
    loadAll() {
            this.skillsetService.query().subscribe(
                (res: HttpResponse<ISkillset[]>) => {
                    this.skillsets = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    ngOnInit() {
    	
        this.isSaving = false;
        this.page1=true; this.page2=false; this.page3=false;
        this.workTypeArray=['Full Time','Part Time'];
        this.loadAll();
        this.savedSkillsArry=[];
        this.savedDesiredskillsArry=[];
        this.skillStr='';
        this.activatedRoute.data.subscribe(({ job }) => {
            this.job = job;
        });
        if(this.job.skills){
        this.savedSkillsArry = this.job.skills.split(',');
        this.savedDesiredskillsArry = this.job.desiredskills.split(',');
        }
    	
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.skillStr = this.savedSkillsArry.join(', ');
        this.isSaving = true;
        this.job.skills=this.skillStr;
        this.job.desiredskills=this.savedDesiredskillsArry.join(', ');
        if (this.job.id !== undefined) {
            this.subscribeToSaveResponse(this.jobService.update(this.job));
        } else {
            this.subscribeToSaveResponse(this.jobService.create(this.job));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IJob>>) {
        result.subscribe((res: HttpResponse<IJob>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get job() {
        return this._job;
    }

    set job(job: IJob) {
        this._job = job;
    }
    
    addSkill(item: string){
    	this.savedSkillsArry.push(item);
    	
    }
    removeSkill(item: string){
    	const index: number = this.savedSkillsArry.indexOf(item);
		    if (index !== -1) {
		        this.savedSkillsArry.splice(index, 1);
		    
		    }    
       }
    addDesiredSkill(item: string){
    	this.savedDesiredskillsArry.push(item);
    	
    }
    removeDesiredSkill(item: string){
    	const index: number = this.savedDesiredskillsArry.indexOf(item);
    if (index !== -1) {
        this.savedDesiredskillsArry.splice(index, 1);
    
    }    
    }
    toggle(pagenum: number){
    	if(pagenum===0){
    		this.page2=false;
    		this.page1=true;
    	}
    	if(pagenum===1){
    		this.page1=false;
    		this.page2=true;
    	}
    	if(pagenum===2){
    		this.page2=false;
    		this.page3=true;
    	}
    	if(pagenum===3){
    		this.page3=false;
    		this.page2=true;
    	}
    }
    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    
}
