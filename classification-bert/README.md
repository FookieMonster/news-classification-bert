# classification-bert

### トレーニング環境
- AI Platform Notebooks
- GPU: Tesla K80 x 1

### 概要
<dl>
  <dt>bert-fine-tuning.ipynb</dt>
  <dd>日本語ニュース記事で再学習させるコードです。</dd>
  <dt>bert-model-deploy.ipynb</dt>
  <dd>エクスポートしたSavedModel形式のモデルをモデルサーバにデプロイするコードです。</dd>
  <dt>convert-text-to-features.ipynb</dt>
  <dd>予測リクエスト用のjsonファイルを作成するコードです。テキストを単語埋め込みベクトルに変換します。</dd>
</dl>
