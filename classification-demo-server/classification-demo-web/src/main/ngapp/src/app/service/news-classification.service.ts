import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.prod';
import { NewsClassificationResponse } from '../model/news-classification-response';
import { NewsClassificationRequest } from '../model/news-classification-request';

const options = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class NewsClassificationService {

  private newsclassifyUrl = environment.urlBase + '/_ah/api/newsclassification/v1/classify';

  constructor(private http: HttpClient) {
  }

  classify(req: NewsClassificationRequest): Observable<NewsClassificationResponse> {

    return this.http.post<NewsClassificationResponse>(this.newsclassifyUrl, req, options);
  }
}
