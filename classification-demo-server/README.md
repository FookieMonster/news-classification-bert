# classification-demo-server

## ビルドとリリース

### node.jsのパッケージをインストールする
```
$ cd ./classification-demo-web/src/main/ngapp
$ npm install
```

### Mavenビルド
```
$ mvn clean install
```

### OpenAPIの生成
```
$ mvn endpoints-framework:openApiDocs -pl classification-demo-endpoints
```

### APIのデプロイ (Cloud Endpoints)
```
$ gcloud endpoints services deploy classification-demo-endpoints/target/openapi-docs/openapi.json
```

### EARアプリのデプロイ (App Engine for Java)
```
$ mvn clean install
$ mvn appengine:update -pl classification-demo
```

### 新しい開発バージョンへの変更
```
$ mvn versions:set -DnewVersion=1.0.0.Alpha2-SNAPSHOT -DgenerateBackupPoms=false
```
```
<appengine.project.version>v100-alpha2</appengine.project.version>
```
```
environment.prod.ts
export const environment = {
    production: true,
    urlBase: "https://news-classification-2020.appspot.com",
    version: "Classification Demo Server 1.0.0.Alpha2-SNAPSHOT"
};
```
