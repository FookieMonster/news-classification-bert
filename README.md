# news-classification-bert

## 説明
BERTを使って日本語ニュース記事をテキスト分類するデモWebアプリケーションです。 

## デモサイト
https://web-dot-news-classification-2020.appspot.com/

## スクリーンショット
![スクリーンショット 2020-01-03 16 18 08](https://user-images.githubusercontent.com/7298626/71711638-35e45900-2e45-11ea-8523-3b1341d46781.png)

## システムの概要
| 項目 |  |
| --- | --- |
| 事前学習済み言語モデル |	BERT: multi_cased_L-12_H-768_A-12.zip（多言語版） |
| 再学習データセット |	livedoor ニュースコーパス: ldcc-20140209.tar.gz（2012年9月上旬） |
| トレーニングスクリプト |	run_classifier.py（一部改変） |
| トレーニング環境 | AI Platform Notebooks（Tesla K80） |
| モデルサーバ | AI Platform Prediction（TensorFlow 1.14.0） |
| モデルマシンタイプ |	n1-standard-2 |
| モデル名 | news_classification |
| モデルバージョン | v1 |
| モデルAPIエンドポイント（REST/JSON） | https://ml.googleapis.com/v1/projects/news-classification-2020/models/news_classification/versions/v1:predict |
| Webバックエンド |	App Engine + Cloud Endpoints（Java） |
| Webフロントエンド | Angular（TypeScript） |

## アーキテクチャ概要図
![architecture-1st-draft](https://user-images.githubusercontent.com/7298626/71759862-48b76600-2ef7-11ea-80a6-2ad358643e18.png)

## ソースコードの概要
このリポジトリは以下の３つのプロジェクトから構成されています。
<dl>
  <dt>classification-bert（AI Platform Notebooks用）</dt>
  <dd>BERTの事前学習済みモデルを使って日本語ニュース記事のテキスト分類に再学習させるコードが含まれます。</dd>
  <dt>classification-demo-server（App Engine for Java用）</dt>
  <dd>デモアプリケーションのWebバックエンドとWebフロントエンドのコードが含まれます。</dd>
  <dt>classification-proxy-server（App Engine for Python用）</dt>
  <dd>モデルサーバに予測リクエストを送る前に、前処理を行うだけのプロキシーです。</dd>
</dl>

>AI Platform Predictionにはカスタム予測ルーチンという仕組みで前処理をモデルサーバー側に含ませることができるのですが、カスタム予測ルーチンはβ版でモデルサイズ500MB以下の制限があるので今回は使用することができませんでした。なので今回は前処理を行うだけのPythonプロキシー環境を設置することにしました。

その他の詳細はそれぞれのプロジェクト配下のREADME.mdファイルを参照してください。
