# classification-demo-server

## ビルドとリリース

### Mavenビルド
```
$ mvn clean install
```

### EARアプリのデプロイ (App Engine for Java)
```
$ mvn appengine:update -pl classification-demo
```

### APIの生成＆デプロイ (Cloud Endpoints)
```
$ mvn endpoints-framework:openApiDocs -pl classification-demo-endpoints
$ gcloud endpoints services deploy classification-demo-endpoints/target/openapi-docs/openapi.json
```
