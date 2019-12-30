import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from 'rxjs';
import { environment } from "../../environments/environment.prod";
import { TwitterUserResponse } from "../model/twitter-user-response";

const options = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ClassificationService {

  private tweetedUsersUrl = environment.urlBase + "/_ah/api/classification/v1/tweetedusers";

  constructor(private http: HttpClient) {
  }

  tweetedUsers(): Observable<TwitterUserResponse> {

    return this.http.post<TwitterUserResponse>(this.tweetedUsersUrl, options);
  }
}
