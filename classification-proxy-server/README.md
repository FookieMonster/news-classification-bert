# classification-proxy-server

以下のソフトウェアが必要です。
- Google Cloud SDK (gcloud CLI)

### GCPプロジェクトIDの編集
main.pyの[YOUR_PROJECT_ID]を自分のGCPプロジェクトIDに置換します。
```
PROJECT_ID = '[YOUR_PROJECT_ID]'
MODEL_NAME = 'news_classification'
MODEL_VERSION = 'v1'
```

### PROXYサーバのデプロイ （App Engine for Python）
```
$ gcloud app deploy --version=v100-alpha1
```
