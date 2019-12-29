import pandas as pd
import json

from flask import Flask, request
from googleapiclient import discovery
from oauth2client.client import GoogleCredentials
from feature import convert_text_to_features

PROJECT_ID = 'news-classification-2020'
MODEL_NAME = 'news_classification'
MODEL_VERSION = 'v1'

FEATURES_FORMAT = '{{"input_ids": {0}, "input_mask": {1}, "segment_ids": {2}, "label_ids":{3}}}'
LABELS = ["0", "1", "2", "3", "4", "5", "6", "7", "8"]
MAX_SEQ_LEN = 128
VOCAB_FILE = 'vocab.txt'

app = Flask(__name__)


@app.route('/predict', methods=['GET', 'POST'])
def predict():
    jsonl = request.data
    input_df = pd.read_json(jsonl, orient='records', lines=True)

    instances = []

    for text in input_df['text']:
        features = convert_text_to_features(text=text, label=LABELS[0], label_list=LABELS, max_seq_length=MAX_SEQ_LEN, vocab_file=VOCAB_FILE)
        json_str = FEATURES_FORMAT.format(features.input_ids, features.input_mask, features.segment_ids, [features.label_id])
        instance = json.loads(json_str)
        instances.append(instance)

    predictions = predict_json(PROJECT_ID, MODEL_NAME, instances, MODEL_VERSION)

    return convert_to_jsonl(predictions)


def predict_json(project, model, instances, version=None):
    credentials = GoogleCredentials.get_application_default()
    service = discovery.build('ml', 'v1', credentials=credentials)
    name = 'projects/{}/models/{}'.format(project, model)

    if version is not None:
        name += '/versions/{}'.format(version)

    response = service.projects().predict(
        name=name,
        body={'instances': instances}
    ).execute()

    if 'error' in response:
        raise RuntimeError(response['error'])

    return response['predictions']


def convert_to_jsonl(predictions):
    json_str = ''
    for pred in predictions:
        json_str += '{{"prediction": {0}}}\n'.format(pred)
    return json_str


if __name__ == '__main__':
    app.run(host='127.0.0.1', port=8080, debug=True)
