import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { RequestTs } from './request-ts.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<RequestTs>;

@Injectable()
export class RequestTsService {

    private resourceUrl =  SERVER_API_URL + 'api/requests';

    constructor(private http: HttpClient) { }

    create(request: RequestTs): Observable<EntityResponseType> {
        const copy = this.convert(request);
        return this.http.post<RequestTs>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    process(): Observable<HttpResponse<any>> {
        return this.http.post<any>(`${this.resourceUrl}/process`, {}, { observe: 'response' });
    }

    update(request: RequestTs): Observable<EntityResponseType> {
        const copy = this.convert(request);
        return this.http.put<RequestTs>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<RequestTs>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RequestTs[]>> {
        const options = createRequestOption(req);
        return this.http.get<RequestTs[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RequestTs[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RequestTs = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<RequestTs[]>): HttpResponse<RequestTs[]> {
        const jsonResponse: RequestTs[] = res.body;
        const body: RequestTs[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to RequestTs.
     */
    private convertItemFromServer(request: RequestTs): RequestTs {
        const copy: RequestTs = Object.assign({}, request);
        return copy;
    }

    /**
     * Convert a RequestTs to a JSON which can be sent to the server.
     */
    private convert(request: RequestTs): RequestTs {
        const copy: RequestTs = Object.assign({}, request);
        return copy;
    }
}
