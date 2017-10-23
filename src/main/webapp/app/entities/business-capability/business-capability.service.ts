import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { BusinessCapability } from './business-capability.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BusinessCapabilityService {

    private resourceUrl = SERVER_API_URL + 'api/business-capabilities';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(businessCapability: BusinessCapability): Observable<BusinessCapability> {
        const copy = this.convert(businessCapability);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(businessCapability: BusinessCapability): Observable<BusinessCapability> {
        const copy = this.convert(businessCapability);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<BusinessCapability> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to BusinessCapability.
     */
    private convertItemFromServer(json: any): BusinessCapability {
        const entity: BusinessCapability = Object.assign(new BusinessCapability(), json);
        entity.dateAdded = this.dateUtils
            .convertDateTimeFromServer(json.dateAdded);
        entity.dateModified = this.dateUtils
            .convertDateTimeFromServer(json.dateModified);
        return entity;
    }

    /**
     * Convert a BusinessCapability to a JSON which can be sent to the server.
     */
    private convert(businessCapability: BusinessCapability): BusinessCapability {
        const copy: BusinessCapability = Object.assign({}, businessCapability);

        copy.dateAdded = this.dateUtils.toDate(businessCapability.dateAdded);

        copy.dateModified = this.dateUtils.toDate(businessCapability.dateModified);
        return copy;
    }
}
