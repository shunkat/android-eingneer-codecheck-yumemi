# 株式会社ゆめみ Android エンジニアコードチェック課題

## アプリ仕様

本アプリは GitHub のリポジトリを検索するアプリです。

### 動作

1. 何かしらのキーワードを入力
2. GitHub API（`search/repositories`）でリポジトリを検索し、結果一覧を概要（リポジトリ名）で表示
3. 特定の結果を選択したら、該当リポジトリの詳細（リポジトリ名、オーナーアイコン、プロジェクト言語、Star 数、Watcher 数、Fork 数、Issue
   数）を表示
   

https://github.com/shunkat/android-eingneer-codecheck-yumemi/assets/53886981/ac2774f8-e740-48e6-a286-00a04a550b4c



### 環境

- IDE：Android Studio Flamingo | 2022.2.1 Patch 2
- Kotlin：1.6.21
- Java：17
- Gradle：8.0
- minSdk：23
- targetSdk：31


### Build Variantsの概要
| Product Flavor | Build Type | 説明                                  | 特記事項                     |
|----------------|------------|---------------------------------------|-----------------------------|
| Dev            | Debug      | 開発中の機能テスト用。デバッグログ有効。  | デバッグログが有効           |
| Dev            | Release    | 開発中の機能をリリース環境でテスト。難読化有り。 | 難読化処理とパフォーマンス最適化 |
| Prod           | Debug      | 本番環境設定でのデバッグ。デバッグログ有効。    | デバッグログが有効           |
| Prod           | Release    | 最終リリース用。難読化と最適化処理実施。        | 難読化とパフォーマンス最適化 |

モックサーバー等は用意していないのでAPIのエンドポイント等は共通です。

### 初期設定
Android Studioで最初に開いた時に、人によってはktlintのプラグインを導入するかどうかを尋ねるポップアップが出るので許可してください。

## gitに関する取り決め
[github flow](https://zenn.dev/ryo_4947123/books/497459787cb294/viewer/branchstrategy_githubflow)を使います。
コード修正を始める際には必ずブランチを切り分けるようにしてください。
コード修正が終わったら、加藤（shunkat）をreviewerに指定してmainに向けたPRを作成してください。

ブランチ名のルールは以下のようにしましょう。
{対応のカテゴリ}/{対応するissue番号}/{対応内容を簡潔に表した文言をハイフン区切りで書く}

### 対応のカテゴリについて
新規機能追加→feature
バグ対応→fix
リファクタ→refactor

## アーキテクチャについて
[公式developerガイド](https://developer.android.com/topic/architecture?hl=ja)を参考にui層とdata層に分ける形で行きます。
かなりシンプルなアプリなのでdomain層は冗長だと判断しています。

#### ui層
- ui element(見た目) => fragment
- state holders(ユーザーからの入力や表示するデータ) => view model

#### data層
- entity(データ型) => model
- data source(大元のデータ) => github(api経由) 
- repository(データへのアクセスをするインターフェース部分) => repository

### フォルダ構成
```
root
  |- data
  |  |- model
  |  \- repository
  |- di
  \- ui
     |- search(レポジトリ検索画面)
     |  |- fragment
     |  \- viewModel...
     |
     \- detail(レポジトリ詳細画面)
        \- fragment
```

`こんな構造の理由`

今回のアプリはレポジトリ検索（search）画面とレポジトリ詳細（detail）画面の2ページ構成です。
また、detail画面では、前のページから受け渡されたデータを表示しているだけなのでdetail画面はui層しか必要ありません。
なのでdata層はページごとに分けず、ui層だけページごとに分けています。

### コーディング規約
新規の部分は以下を参考にしてください。
https://kotlinlang.org/docs/coding-conventions.html#avoid-redundant-constructs

既存に似たようなコードが存在する場合はそちらを尊重してください。

`xmlのレイアウトファイルのid`について

idのプレフィックスとして、そのviewの種類をつけるようにします。以前経験したプロダクトで便利だったので。

例：

```
textView →　tv
imageView → iv
recyclerView → rv
```



# こだわりポイント
### 1. 地味に使いやすいアプリ

時間があまり取れないということもあり、派手な機能の追加はせず、安全で実際に使いやすいアプリを目指しました。

- アプリをバックグラウンドにしたときの検索結果保持
- 画面回転の禁止
- ローディングインジケーターや、エラーメッセージの表示で、ユーザーが困惑しないようにする
- 適切なタイミングでソフトウェアキーボードを表示、非表示
- 流石に詳細画面が見づらかったのでその部分はデザインも調整しています
- ダークモードに対応
- 他のアプリとの差別化のためのアプリ名やアイコンの変更

### 2. 今後の開発を想定

今後もこのアプリの開発が続くという想定で、開発しやすいプロダクトを目指しています

- gitやcodingの開発ルールや文書化
- unit test導入
- ui test導入
- CI作成
- buildVariantsの整備とドキュメント化
- formatterの導入と、自動フォーマットの仕組み作り

### 3. クオリティの高いコード

安全性、可読性の高いコードを目指しました。

- MVVMアーキテクチャの採用
- 誤解を与えず、すぐに理解できる変数名
- 適切なコメント
- 適切に分割されているプログラム構造
- nullが入る可能性や、型が違うことによるエラーの削減

# レビュワーさんへのメッセージ
issueの中でラベルに「初期課題」と入っているものが、元々のレポジトリでissueとして挙げられていたものに対応しています。

また元々のissueで存在した難易度の設定はマイルストーンという形でissueに設定しました。


「初期課題」というラベルがついていないものは、自分が実装しながら追加したissueです。
お時間があれば、ご確認お願いします。


実装途中でWIPにしてしまったPRがあったり、issueは立てたものの時間の都合上対応しきれなかった部分も多くあります。

読みづらくてお手数おかけするとは思いますが、何卒レビューよろしくお願いいたします。
