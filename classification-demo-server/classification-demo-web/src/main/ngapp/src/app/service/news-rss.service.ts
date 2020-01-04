import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.prod';
import { NewsRssResponse } from '../model/news-rss-response';
import { NewsRssRequest } from '../model/news-rss-request';

const options = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class NewsRssService {

  private newsFeedUrl = environment.urlBase + '/_ah/api/newsrss/v1/feed';

  constructor(private http: HttpClient) {
  }

  feed(req: NewsRssRequest): Observable<NewsRssResponse> {

    return this.http.post<NewsRssResponse>(this.newsFeedUrl, req, options);
  }
}
