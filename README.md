# 株式会社ゆめみ Android エンジニアコードチェック課題

## アプリ仕様

本アプリは GitHub のリポジトリを検索するアプリです。

<img src="docs/app.gif" width="320">

### 環境

- IDE：Android Studio Flamingo | 2022.2.1 Patch 2
- Kotlin：1.6.21
- Java：17
- Gradle：8.0
- minSdk：23
- targetSdk：31

※ ライブラリの利用はオープンソースのものに限ります。
※ 環境は適宜更新してください。

### 初期設定
Android Studioで最初に開いた時に、人によってはktlintのプラグインを導入するかどうかを尋ねるポップアップが出るので許可してください。

### 動作

1. 何かしらのキーワードを入力
2. GitHub API（`search/repositories`）でリポジトリを検索し、結果一覧を概要（リポジトリ名）で表示
3. 特定の結果を選択したら、該当リポジトリの詳細（リポジトリ名、オーナーアイコン、プロジェクト言語、Star 数、Watcher 数、Fork 数、Issue
   数）を表示

## 実装スケジュール

1日目

```
- git周りやエディター周りの初期設定
- アーキテクチャ修正
```

2日目

```
- クラス設計
- 適切なまとまりにコード分割
- クラス名 / 変数名の修正
```

3日目

```
- バグ修正
```

4日目

```
- テスト追加
- CI整備
- 機能追加
```

イメージとしては
粒度の大きい順にリファクタ→細かいバグ修正

バグ修正から始めようとしたが、少し見たらコードが酷すぎるので、リファクタのほうが優先度が高いと判断した。

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