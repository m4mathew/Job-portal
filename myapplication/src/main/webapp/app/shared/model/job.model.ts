export interface IJob {
    id?: number;
    title?: string;
    company?: string;
    location?: string;
    skills?: string;
    desc?: string;
    worktype?: string;
    online?: boolean;
    tele?: boolean;
    ftf?: boolean;
    hr?: boolean;
    psychometric?: boolean;
    desiredskills?: string;
}

export class Job implements IJob {
    constructor(
        public id?: number,
        public title?: string,
        public company?: string,
        public location?: string,
        public skills?: string,
        public desc?: string,
        public worktype?: string,
        public online?: boolean,
        public tele?: boolean,
        public ftf?: boolean,
        public hr?: boolean,
        public psychometric?: boolean,
        public desiredskills?: string
    ) {
        this.online = false;
        this.tele = false;
        this.ftf = false;
        this.hr = false;
        this.psychometric = false;
    }
}
