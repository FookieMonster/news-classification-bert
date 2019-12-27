import numpy as np
import json
from googleapiclient import discovery
from flask import Flask, jsonify
from oauth2client.client import GoogleCredentials

app = Flask(__name__)


@app.route('/predict', methods=['GET', 'POST'])
def predict():
    features = '{{"input_ids":{0},"input_mask":{1},"segment_ids":{2},"label_ids":{3}}}' \
        .format(np.zeros((128), int).tolist(), np.zeros((128), int).tolist(), np.zeros((128), int).tolist(), [0])

    instance = json.loads(features)

    predictions = predict_json('news-classification-2020', 'news_classification', [instance], 'v1')

    return jsonify(predictions)


def convert_text_to_feature(title):
    return ""


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


if __name__ == '__main__':
    app.run(host='127.0.0.1', port=8080, debug=True)
