# classification-bert

### トレーニング環境
- AI Platform Notebooks
- Machine type: 4 vCPUs, 15 GB RAM
- GPU: NVIDIA Tesla K80 x 1
- Tensorflow Enterprise 1.15

### 実行手順
1. GoogleのAI Platformの管理コンソールにログイン
2. 新しいノートブックインスタンスを作成して開始
3. ノートブックインスタンス内でターミナルを起動
4. このリポジトリをクローン
```
$ git clone https://github.com/FookieMonster/news-classification-bert.git
```

### ノートブックの概要
<dl>
  <dt>bert-fine-tuning.ipynb</dt>
  <dd>日本語ニュース記事で再学習させるコードです。</dd>
  <dt>bert-model-deploy.ipynb</dt>
  <dd>エクスポートしたSavedModel形式のモデルをモデルサーバにデプロイするコードです。</dd>
  <dt>convert-text-to-features.ipynb</dt>
  <dd>予測リクエスト用のjsonファイルを作成するコードです。テキストを単語埋め込みベクトルに変換します。</dd>
</dl>
