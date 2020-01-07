# classification-demo-server

## ビルドとデプロイ

ビルドするには以下のソフトウェアが必要です。
- Maven
- Google Cloud SDK (gcloud コマンドラインツール)

### GCPプロジェクトIDの編集
pom.xmlの[YOUR_PROJECT_ID]を自分のGCPプロジェクトIDに置換します。
```
<properties>
    ...省略...
    <appengine.project.id>[YOUR_PROJECT_ID]</appengine.project.id>
    <appengine.project.version>v100-alpha1</appengine.project.version>
    <proxy.server.url>https://proxy-dot-${appengine.project.id}.appspot.com/predict</proxy.server.url>
    <angular.build.environment>prod</angular.build.environment>
</properties>
```

### Mavenビルド
```
$ mvn clean install
```

### EARファイルのデプロイ (App Engine for Java)
```
$ mvn appengine:update -pl classification-demo
```

### エンドポイントデプロイ (Cloud Endpoints)
```
$ mvn endpoints-framework:openApiDocs -pl classification-demo-endpoints
$ gcloud endpoints services deploy classification-demo-endpoints/target/openapi-docs/openapi.json
```

### アクセスURL
EARファイルとエンドポイントのデプロイが完了すると、以下のURLでWebフロントエンドにアクセスできるはずです。
```
https://web-dot-[YOUR_PROJECT_ID].appspot.com/
```
