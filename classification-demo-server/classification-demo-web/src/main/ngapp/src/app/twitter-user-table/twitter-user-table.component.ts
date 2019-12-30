import { Component, OnInit } from '@angular/core';
import { ClassificationService } from "../service/classification.service";
import { TwitterUserResponse } from "../model/twitter-user-response";

@Component({
  selector: 'app-twitter-user-table',
  templateUrl: './twitter-user-table.component.html',
  styleUrls: ['./twitter-user-table.component.css']
})
export class TwitterUserTableComponent implements OnInit {

  users: TwitterUserResponse[];
  allUsers: TwitterUserResponse[];
  cols: any[];

  tabTypes;
  tabTypeKeys;
  tabIcons;

  tabSelectedIndex: number;
  visibleProgressBar: boolean

  constructor(private classificationService: ClassificationService) {

    this.tabSelectedIndex = 0;
  }

  ngOnInit() {

    this.tabTypes = {
      all: 'ALL',
      poi: 'POI'
    };
    this.tabTypeKeys = Object.keys(this.tabTypes);
    this.tabIcons = {
      all: 'fas fa-user',
      poi: 'fas fa-map-marker-alt'
    };

    this.cols = [
      { field: 'userName', header: 'Name' },
      { field: 'label', header: 'Label' }
    ];
  }

  loadUsers() {

    this.visibleProgressBar = true;

    this.classificationService.tweetedUsers()
      .subscribe(users => {
        this.allUsers = users['items'];
        this.visibleProgressBar = false;
        if (this.tabSelectedIndex == 0) {
          this.filterAllUsers();
        } else {
          this.filterPoiUsers();
        }
      });
  }

  filterAllUsers() {

    this.users = this.allUsers;
  }

  filterPoiUsers() {

    this.users = this.allUsers.filter(user => user.label === 'poi');
  }

  onStart() {

    this.loadUsers();
  }

  onTabChange(e) {
    console.log("onTabChange");
    this.tabSelectedIndex = e.index
    if (e.index == 0) {
      this.filterAllUsers();
    } else {
      this.filterPoiUsers();
    }
  }
}
