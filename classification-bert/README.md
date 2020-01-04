# classification-bert

## トレーニング環境
- AI Platform Notebooks
- Machine type: 4 vCPUs, 15 GB RAM
- GPU: NVIDIA Tesla K80 x 1
- Tensorflow Enterprise 1.15

## 実行手順
1. GoogleのAI Platformの管理コンソールにログイン
2. 新しいノートブックインスタンスを作成して開始
3. ノートブックインスタンス内でターミナルを起動
4. このリポジトリをクローン
```
$ git clone https://github.com/FookieMonster/news-classification-bert.git
```

## ノートブックの概要
<dl>
  <dt>bert-fine-tuning.ipynb</dt>
  <dd>日本語ニュース記事で再学習させるコードです。</dd>
  <dt>bert-model-deploy.ipynb</dt>
  <dd>エクスポートしたSavedModel形式のモデルをモデルサーバにデプロイするコードです。</dd>
  <dt>convert-text-to-features.ipynb</dt>
  <dd>予測リクエスト用のjsonファイルを作成するコードです。テキストを単語埋め込みベクトルに変換します。</dd>
</dl>

## トレーニングスクリプト（run_classifier.py）の修正箇所

### DataProcessorの追加
BERT公式の再学習用トレーニングスクリプト（run_classifier.py）は標準でMRPCやCoLAなどのデータセットのみに対応しているので、独自のデータセットで再学習したい場合は自分でDataProcessorを追加する必要があります。今回はJpNewsProcessorを追加しています。

```Python
processors = {
    "cola": ColaProcessor,
    "mnli": MnliProcessor,
    "mrpc": MrpcProcessor,
    "xnli": XnliProcessor,
    "jpnews": JpNewsProcessor,
}
```

```Python
class JpNewsProcessor(DataProcessor):
  """Processor for the Japanese News data set."""

  def read_tsv(self, path):
    df = pd.read_csv(path, sep="\t")
    return [(str(text), str(label)) for text,label in zip(df['text'], df['label'])]

  def get_train_examples(self, data_dir):
    """See base class."""
    return self._create_examples(
        self.read_tsv(os.path.join(data_dir, "train.tsv")), "train")

  def get_dev_examples(self, data_dir):
    """See base class."""
    return self._create_examples(
        self.read_tsv(os.path.join(data_dir, "dev.tsv")), "dev")

  def get_test_examples(self, data_dir):
    """See base class."""
    return self._create_examples(
      self.read_tsv(os.path.join(data_dir, "test.tsv")), "test")

  def get_labels(self):
    """See base class."""
    return ["0", "1", "2", "3", "4", "5", "6", "7", "8"]

  def _create_examples(self, lines, set_type):
    """Creates examples for the training and dev sets."""
    examples = []
    for (i, line) in enumerate(lines):
      guid = "%s-%s" % (set_type, i)
      text_a = tokenization.convert_to_unicode(line[0])
      label = tokenization.convert_to_unicode(line[1])
      examples.append(
          InputExample(guid=guid, text_a=text_a, text_b=None, label=label))
    return examples
```

### do_exportオプションの追加
AI Platform Predictionのモデルサーバにデプロイするには、TensorflowのSavedModel形式でモデルをエクスポートする必要があるので、do_exportというオプションを追加しています。

```Python
if FLAGS.do_export:
  def json_serving_input_fn():
      inputs = {}
      inputs["input_ids"] = tf.placeholder(shape=[None, FLAGS.max_seq_length], dtype=tf.int64)
      inputs["input_mask"] = tf.placeholder(shape=[None, FLAGS.max_seq_length], dtype=tf.int64)
      inputs["segment_ids"] = tf.placeholder(shape=[None, FLAGS.max_seq_length], dtype=tf.int64)
      inputs["label_ids"] = tf.placeholder(shape=None, dtype=tf.int64)
      return tf.estimator.export.ServingInputReceiver(inputs, inputs)

  estimator._export_to_tpu = False
  estimator.export_saved_model("./saved_model", json_serving_input_fn)
```

```Shell
# SavedModel形式でモデルをエクスポート
TASK = 'JpNews'
BERT_BASE_DIR = 'bert_checkpoint/multi_cased_L-12_H-768_A-12'
GLUE_DIR = 'news_data'
OUTPUT_DIR = 'output_news'
TRAINED_CLASSIFIER = 'output_news'

! python3 ./bert_repo/run_classifier.py \
  --task_name=$TASK \
  --do_export=true \            <<============================= 追加
  --do_lower_case=False \
  --data_dir=$GLUE_DIR \
  --vocab_file=$BERT_BASE_DIR/vocab.txt \
  --bert_config_file=$BERT_BASE_DIR/bert_config.json \
  --init_checkpoint=$TRAINED_CLASSIFIER \
  --max_seq_length=128 \
  --output_dir=$OUTPUT_DIR
```
