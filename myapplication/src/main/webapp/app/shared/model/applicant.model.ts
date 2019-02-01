import { IJob } from 'app/shared/model//job.model';

export interface IApplicant {
    id?: number;
    name?: string;
    email?: string;
    resumeContentType?: string;
    resume?: any;
    phone?: string;
    job?: IJob;
}

export class Applicant implements IApplicant {
    constructor(
        public id?: number,
        public name?: string,
        public email?: string,
        public resumeContentType?: string,
        public resume?: any,
        public phone?: string,
        public job?: IJob
    ) {}
}
