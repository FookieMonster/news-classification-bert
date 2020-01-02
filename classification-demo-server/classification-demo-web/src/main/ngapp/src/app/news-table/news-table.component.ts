import { Component, OnInit } from '@angular/core';
import { NewsClassificationService } from '../service/news-classification.service';
import { NewsRssService } from '../service/news-rss.service';
import { NewsRssResult } from '../model/news-rss-result';
import { NewsClassificationResult } from '../model/news-classification-result';
import { NewsRssRequest } from '../model/news-rss-request';
import { NewsClassificationRequest } from '../model/news-classification-request';
import { SelectItem } from 'primeng/api';

interface Description {
  name: string;
  value: string;
}

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

  descriptions: Description[];
  visibleProgressBar: boolean;

  constructor(private newsClassificationService: NewsClassificationService, private newsRssService: NewsRssService) {
  }

  ngOnInit() {

    this.cols = [
      { field: 'title', header: 'ニュースタイトル', width: '75%' },
      { field: 'label', header: '分類結果', width: '25%' }
    ];

    this.newsSites = [
      { label: '独女通信', value: { id: 0, label: 0, name: 'dokujo-tsushin', url: null } },
      { label: 'ITライフハック', value: { id: 1, label: 1, name: 'it-life-hack', url: 'https://news.livedoor.com/rss/article/vender/ithack' } },
      { label: '家電チャンネル', value: { id: 2, label: 2, name: 'kaden-channel', url: 'https://news.livedoor.com/rss/article/vender/kadench' } },
      { label: 'livedoor HOMME', value: { id: 3, label: 3, name: 'livedoor-homme', url: null } },
      { label: 'MOVIE ENTER', value: { id: 4, label: 4, name: 'movie-enter', url: 'https://news.livedoor.com/rss/article/vender/movie_enter' } },
      { label: 'Peachy', value: { id: 5, label: 5, name: 'peachy', url: 'https://news.livedoor.com/rss/article/vender/saishoku_kenbi' } },
      { label: 'エスマックス', value: { id: 6, label: 6, name: 'smax', url: 'https://news.livedoor.com/rss/article/vender/smax' } },
      { label: 'Sports Watch', value: { id: 7, label: 7, name: 'sports-watch', url: 'https://news.livedoor.com/rss/article/vender/sports_watch' } },
      { label: 'トピックニュース', value: { id: 8, label: 8, name: 'topic-news', url: 'https://news.livedoor.com/rss/article/vender/news' } }
    ];
    this.newsSite = this.newsSites[6].value;

    this.descriptions = [
      { name: '事前学習済み言語モデル', value: 'BERT: multi_cased_L-12_H-768_A-12.zip（多言語版）' },
      { name: '再学習データセット', value: 'livedoor ニュースコーパス: ldcc-20140209.tar.gz（2012年9月上旬）' },
      { name: 'モデルサーバ', value: 'Google AI Platform Prediction' },
      { name: 'モデル名', value: 'news_classification' },
      { name: 'モデルバージョン', value: 'v1' },
      { name: 'APIエンドポイント（REST/JSON）', value: 'https://ml.googleapis.com/v1/projects/news-classification-2020/models/news_classification/versions/v1:predict' },
    ];
  }

  loadRssFeeds() {

    this.feeds = [];
    this.predictions = [];
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

  onChange(e) {
    this.feeds = [];
    this.predictions = [];
  }
}
