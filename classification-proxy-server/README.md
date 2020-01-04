# classification-proxy-server

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
