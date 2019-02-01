export interface ISkillset {
    id?: number;
    skill?: string;
}

export class Skillset implements ISkillset {
    constructor(public id?: number, public skill?: string) {}
}
