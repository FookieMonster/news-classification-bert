import { Component, OnInit } from '@angular/core';
import { NewsClassificationService } from '../service/news-classification.service';
import { NewsRssService } from '../service/news-rss.service';
import { NewsRssResult } from '../model/news-rss-result';
import { NewsClassificationResult } from '../model/news-classification-result';
import { NewsRssRequest } from '../model/news-rss-request';

@Component({
  selector: 'app-news-table',
  templateUrl: './news-table.component.html',
  styleUrls: ['./news-table.component.css']
})
export class NewsTableComponent implements OnInit {

  feeds: NewsRssResult[];
  predictions: NewsClassificationResult[];
  cols: any[];

  tabSelectedIndex: number;
  visibleProgressBar: boolean;

  constructor(private newsClassificationService: NewsClassificationService, private newsRssService: NewsRssService) {

    this.tabSelectedIndex = 0;
  }

  ngOnInit() {

    this.cols = [
      { field: 'title', header: 'Title' },
      { field: 'label', header: 'Label' }
    ];
  }

  loadRssFeeds() {

    this.visibleProgressBar = true;

    const req = new NewsRssRequest();
    req.feedUrl = 'https://news.livedoor.com/rss/article/vender/kadench';

    this.newsRssService.feed(req)
      .subscribe(results => {
        this.feeds = results['results'];
        this.visibleProgressBar = false;
      });
  }

  onStart() {

    this.loadRssFeeds();
  }
}
