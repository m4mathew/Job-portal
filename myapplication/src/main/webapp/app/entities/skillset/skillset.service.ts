import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISkillset } from 'app/shared/model/skillset.model';

type EntityResponseType = HttpResponse<ISkillset>;
type EntityArrayResponseType = HttpResponse<ISkillset[]>;

@Injectable({ providedIn: 'root' })
export class SkillsetService {
    private resourceUrl = SERVER_API_URL + 'api/skillsets';

    constructor(private http: HttpClient) {}

    create(skillset: ISkillset): Observable<EntityResponseType> {
        return this.http.post<ISkillset>(this.resourceUrl, skillset, { observe: 'response' });
    }

    update(skillset: ISkillset): Observable<EntityResponseType> {
        return this.http.put<ISkillset>(this.resourceUrl, skillset, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISkillset>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISkillset[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
