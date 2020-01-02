import { Component, OnInit } from '@angular/core';
import { NewsClassificationService } from '../service/news-classification.service';
import { NewsRssService } from '../service/news-rss.service';
import { NewsRssResult } from '../model/news-rss-result';
import { NewsClassificationResult } from '../model/news-classification-result';
import { NewsRssRequest } from '../model/news-rss-request';
import { NewsClassificationRequest } from '../model/news-classification-request';
import { SelectItem } from 'primeng/api';

interface NewsSite {
  label: number;
  name: string;
  url: string;
}

@Component({
  selector: 'app-news-table',
  templateUrl: './news-table.component.html',
  styleUrls: ['./news-table.component.css']
})
export class NewsTableComponent implements OnInit {

  newsSites: SelectItem[];
  newsSite: NewsSite;

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
      { field: 'title', header: 'ニュースタイトル' },
      { field: 'label', header: '分類結果' }
    ];

    this.newsSites = [
      { label: '0 独女通信', value: { id: 0, label: 0, name: 'dokujo-tsushin', url: null } },
      { label: '1 ITライフハック', value: { id: 1, label: 1, name: 'it-life-hack', url: 'https://news.livedoor.com/rss/article/vender/ithack' } },
      { label: '2 家電チャンネル', value: { id: 2, label: 2, name: 'kaden-channel', url: 'https://news.livedoor.com/rss/article/vender/kadench' } },
      { label: '3 livedoor HOMME', value: { id: 3, label: 3, name: 'livedoor-homme', url: null } },
      { label: '4 MOVIE ENTER', value: { id: 4, label: 4, name: 'movie-enter', url: 'https://news.livedoor.com/rss/article/vender/movie_enter' } },
      { label: '5 Peachy', value: { id: 5, label: 5, name: 'peachy', url: 'https://news.livedoor.com/rss/article/vender/saishoku_kenbi' } },
      { label: '6 エスマックス', value: { id: 6, label: 6, name: 'smax', url: 'https://news.livedoor.com/rss/article/vender/smax' } },
      { label: '7 Sports Watch', value: { id: 7, label: 7, name: 'sports-watch', url: 'https://news.livedoor.com/rss/article/vender/sports_watch' } },
      { label: '8 トピックニュース', value: { id: 8, label: 8, name: 'topic-news', url: 'https://news.livedoor.com/rss/article/vender/news' } }
    ];
    this.newsSite = this.newsSites[1].value;
  }

  loadRssFeeds() {

    this.visibleProgressBar = true;

    const req = new NewsRssRequest();
    req.feedUrl = this.newsSite.url;

    this.newsRssService.feed(req)
      .subscribe(results => {
        this.feeds = results['results'];
        this.visibleProgressBar = false;
        this.loadPredictions();
      });
  }

  loadPredictions() {

    this.visibleProgressBar = true;

    const req = new NewsClassificationRequest();
    for (const feed of this.feeds) {
      req.titles.push(feed.title);
    }

    this.newsClassificationService.classify(req)
      .subscribe(results => {
        this.predictions = results['results'];
        this.visibleProgressBar = false;
        this.mergeLabels();
      });
  }

  mergeLabels() {
    let i = 0;
    for (const feed of this.feeds) {
      feed.label = this.predictions[i].label;
      i++;
    }
  }

  onStart() {

    this.loadRssFeeds();
  }
}
