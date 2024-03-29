# news-classification-bert

## 概要
BERTを使って日本語ニュース記事をテキスト分類するWebアプリケーションです。  

このプロジェクトは[DL4US](https://deeplearning.jp/lectures/dl4us_3rd/)の第3期の最終課題で開発したものです。

## スクリーンショット
![スクリーンショット 2020-01-03 16 18 08](https://user-images.githubusercontent.com/7298626/71711638-35e45900-2e45-11ea-8523-3b1341d46781.png)

## デモシステムの概要
| 項目 |  |
| --- | --- |
| 事前学習済み言語モデル |	BERT: multi_cased_L-12_H-768_A-12.zip（多言語版） |
| 再学習データセット |	livedoor ニュースコーパス: ldcc-20140209.tar.gz（2012年9月上旬） |
| トレーニングスクリプト |	run_classifier.py（[一部改変](https://github.com/FookieMonster/news-classification-bert/tree/master/classification-bert#%E3%83%88%E3%83%AC%E3%83%BC%E3%83%8B%E3%83%B3%E3%82%B0%E3%82%B9%E3%82%AF%E3%83%AA%E3%83%97%E3%83%88run_classifierpy%E3%81%AE%E4%BF%AE%E6%AD%A3%E7%AE%87%E6%89%80)） |
| トレーニング環境 | AI Platform Notebooks（Tesla K80） |
| モデルサーバ | AI Platform Prediction（TensorFlow 1.14.0） |
| Webバックエンド |	App Engine + Cloud Endpoints（Java） |
| Webフロントエンド | Angular（TypeScript） |

## アーキテクチャの概要図
![archtecture-2nd-draft](https://user-images.githubusercontent.com/7298626/72593142-94323100-3947-11ea-86a9-1b05cad0d1c6.png)

### アーキテクチャの特徴
<dl>
  <dt>REST API</dt>
  <dd>フロントエンド側はAPIベースなのでWebだけではなく、スマホアプリ(Android/iOS)にも簡単に対応できる。</dd>
  <dt>PaaS / NoOps</dt>
  <dd>バックエンド側はPaaSベースなのでインフラの管理や運用ではなく、アプリケーション開発に専念できる。</dd>
  <dt>Auto Scaling</dt>
  <dd>バックエンド側はオートスケーリングなのでユーザーがいない時の運用コストが低い。<br>
    オートスケーリングなので急激なユーザ数の増加に短時間で対応できる。</dd>
  <dt>Loose Coupling</dt>
  <dd>データサイエンティスト、Webフロントエンド、Webバックエンドの３人のエンジニアで開発することを想定。<br>
    ３人はREST-APIで疎結合なので同時並行して開発が可能なので開発期間を短縮できる。</dd>
</dl>

## ソースコードの概要
このリポジトリは以下の３つのプロジェクトから構成されています。
<dl>
  <dt>classification-bert（AI Platform Notebooks用）</dt>
  <dd>BERTの事前学習済みモデルを日本語ニュース記事のテキスト分類に再学習するノートブックが含まれます。</dd>
  <dt>classification-demo-server（App Engine for Java用）</dt>
  <dd>デモアプリケーションのWebバックエンド(Java)とWebフロントエンド(TypeScript)のコードが含まれます。</dd>
  <dt>classification-proxy-server（App Engine for Python用）</dt>
  <dd>モデルサーバに予測リクエストを送る前に、前処理を行うプロキシーです。(※1)</dd>
</dl>

詳細は各プロジェクト配下のREADME.mdファイルを参照してください。

<sub>(※1) AI Platform Predictionにはカスタム予測ルーチンという仕組みで前処理をモデルサーバー側に含ませることができるのですが、カスタム予測ルーチンはβ版でモデルサイズ500MB以下の制限があるので今回は使用することができませんでした。なので今回は前処理を行うだけのPythonプロキシー環境を設置することにしました。</sub>
