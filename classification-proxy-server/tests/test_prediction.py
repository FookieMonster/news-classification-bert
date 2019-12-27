import unittest
import numpy as np
import json
from googleapiclient import discovery
from google.oauth2 import service_account


class TestPrediction(unittest.TestCase):

    def test_predict(self):
        json_str = '{{"input_ids":{0},"input_mask":{1},"segment_ids":{2},"label_ids":{3}}}' \
            .format(np.zeros((128), int).tolist(), np.zeros((128), int).tolist(), np.zeros((128), int).tolist(), [0])
        print(json_str)

        instance = json.loads(json_str)
        print(instance)

        predictions = self.predict_json('news-classification-2020', 'news_classification', [instance], 'v1')
        print(predictions)

    def predict_json(self, project, model, instances, version=None):
        credentials = service_account.Credentials.from_service_account_file(
            '../news-classification-2020-1221a9735c2a.json',
            scopes=["https://www.googleapis.com/auth/cloud-platform"],
        )

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
