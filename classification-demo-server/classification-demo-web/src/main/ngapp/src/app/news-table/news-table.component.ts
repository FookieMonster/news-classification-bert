import { Component, OnInit } from '@angular/core';
import { NewsClassificationService } from '../service/news-classification.service';
import { NewsRssService } from '../service/news-rss.service';
import { NewsRssResult } from '../model/news-rss-result';
import { NewsClassificationResult } from '../model/news-classification-result';
import { NewsRssRequest } from '../model/news-rss-request';
import { NewsClassificationRequest } from '../model/news-classification-request';
import { Message, SelectItem } from 'primeng/api';

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
  msgs: Message[] = [];

  constructor(private newsClassificationService: NewsClassificationService, private newsRssService: NewsRssService) {
  }

  ngOnInit() {
    this.cols = [
      { field: 'title', header: 'ニュースタイトル', width: '75%' },
      { field: 'label', header: '分類結果', width: '25%' }
    ];

    this.newsSites = [
      { label: '独女通信', value: { id: 0, label: 0, url: null } },
      { label: 'ITライフハック', value: { id: 1, label: 1, url: 'https://news.livedoor.com/rss/article/vender/ithack' } },
      { label: '家電チャンネル', value: { id: 2, label: 2, url: 'https://news.livedoor.com/rss/article/vender/kadench' } },
      { label: 'livedoor HOMME', value: { id: 3, label: 3, url: null } },
      { label: 'MOVIE ENTER', value: { id: 4, label: 4, url: 'https://news.livedoor.com/rss/article/vender/movie_enter' } },
      { label: 'Peachy', value: { id: 5, label: 5, url: 'https://news.livedoor.com/rss/article/vender/saishoku_kenbi' } },
      { label: 'エスマックス', value: { id: 6, label: 6, url: 'https://news.livedoor.com/rss/article/vender/smax' } },
      { label: 'Sports Watch', value: { id: 7, label: 7, url: 'https://news.livedoor.com/rss/article/vender/sports_watch' } },
      { label: 'トピックニュース', value: { id: 8, label: 8, url: 'https://news.livedoor.com/rss/article/vender/news' } }
    ];
    this.newsSite = this.newsSites[6].value;

    this.descriptions = [
      { name: '事前学習済み言語モデル', value: 'BERT: multi_cased_L-12_H-768_A-12.zip（多言語版）' },
      { name: '再学習データセット', value: 'livedoor ニュースコーパス: ldcc-20140209.tar.gz（2012年9月上旬）' },
      { name: 'トレーニングスクリプト', value: 'run_classifier.py（一部改変）' },
      { name: 'トレーニング環境', value: 'AI Platform Notebooks（Tesla K80）' },
      { name: 'モデルサーバ', value: 'AI Platform Prediction（TensorFlow 1.14.0）' },
      { name: 'モデルマシンタイプ', value: 'n1-standard-2' },
      { name: 'モデル名', value: 'news_classification' },
      { name: 'モデルバージョン', value: 'v1' },
      { name: 'モデルAPIエンドポイント（REST/JSON）', value: 'https://ml.googleapis.com/v1/projects/[PROJECT_ID]/models/news_classification/versions/v1:predict' },
      { name: 'Webバックエンド', value: 'App Engine + Cloud Endpoints（Java）' },
      { name: 'Webフロントエンド', value: 'Angular（TypeScript）' },
    ];
  }

  loadRssFeeds() {
    this.feeds = [];
    this.predictions = [];
    this.visibleProgressBar = true;

    const req = new NewsRssRequest();
    req.feedUrl = this.newsSite.url;

    if (req.feedUrl == null) {
      this.msgs = [{
        severity: 'error',
        summary: 'エラー',
        detail: 'ニュースサイトは既に閉鎖されています。'
      }];
      this.visibleProgressBar = false;
      return;
    }

    this.newsRssService.feed(req)
      .subscribe(results => {
        this.feeds = results['results'];
        this.loadPredictions();
      });
  }

  loadPredictions() {
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
