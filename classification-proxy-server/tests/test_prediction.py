import unittest
import pandas as pd
import json

from googleapiclient import discovery
from oauth2client.client import GoogleCredentials
from feature import convert_text_to_features


class TestPrediction(unittest.TestCase):

    def test_predict(self):
        input_df = pd.read_json('./input_text.jsonl', orient='records', lines=True)

        label = "0"
        labels = ["0", "1", "2", "3", "4", "5", "6", "7", "8"]
        max_seq_length = 128

        instances = []

        for text in input_df['text']:
            features = convert_text_to_features(text=text, label=label, label_list=labels,
                                                        max_seq_length=max_seq_length, vocab_file='../vocab.txt')
            json_str = '{{"input_ids":{0},"input_mask":{1},"segment_ids":{2},"label_ids":{3}}}' \
                .format(features.input_ids, features.input_mask, features.segment_ids, [features.label_id])
            instance = json.loads(json_str)
            instances.append(instance)

        print(instances)

        predictions = self.predict_json('news-classification-2020', 'news_classification', instances, 'v2')
        print(predictions)

    def predict_json(self, project, model, instances, version=None):
        # gcloud auth application-default login
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
    unittest.main()