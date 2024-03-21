import boto3
import logging
import os

logger = logging.getLogger(__name__)
dynamodb = boto3.resource("dynamodb", region_name='us-east-1', endpoint_url="http://127.0.0.1:8000")


def create_table(table_name):
    params = {
        "TableName": table_name,
        "KeySchema": [
            {"AttributeName": "pk", "KeyType": "HASH"},
            {"AttributeName": "sk", "KeyType": "RANGE"},
        ],
        "AttributeDefinitions": [
            {"AttributeName": "pk", "AttributeType": "S"},
            {"AttributeName": "sk", "AttributeType": "S"},
        ],
        "ProvisionedThroughput": {"ReadCapacityUnits": 10, "WriteCapacityUnits": 10},
    }
    print(f"Creating table {table_name}...")
    table = dynamodb.create_table(**params)
    table.wait_until_exists()
    return table


def create_message(table):
    table.put_item(
        Item={
            'pk': 'groupUUID#channelName',
            'sk': '2024-03-13T19:33:26.510919600-05:00[America/Chicago]#messageUUID',
            'created': '2024-03-13T19:33:26.510919600-05:00[America/Chicago]',
            'id': 'messageUUID',
            'media': [],
            'sender': 'bpalomo@traverse.zone',
            'text': 'Hello World!',
            'type': 'GROUP_MESSAGE',
            'updated': '2024-03-13T19:33:26.510919600-05:00[America/Chicago]'
        }
    )


def create_notification(table):
    table.put_item(
        Item={
            'pk': 'recipientUUID',
            'sk': '2024-03-13T19:33:26.510919600-05:00[America/Chicago]#notificationUUID',
            'id': 'notificationUUID',
            'created': '2024-03-13T19:33:26.510919600-05:00[America/Chicago]',
            'sender': 'bpalomo@traverse.zone',
            'type': 'FRIEND_REQUEST',
        }
    )


if __name__ == "__main__":
    notification_table = create_table("Notifications")
    messages_table = create_table("Messages")
    create_message(messages_table)
    create_notification(notification_table)
