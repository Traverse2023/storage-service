#!/bin/bash

echo Creating local Messages and Notifications dynamodb tables...

awslocal dynamodb create-table --table-name Messages --attribute-definitions AttributeName=pk,AttributeType=S AttributeName=sk,AttributeType=S --key-schema AttributeName=pk,KeyType=HASH AttributeName=sk,KeyType=RANGE --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 --region us-east-1 && echo Successfully created table Messages. || echo Did not create table Messages. Table may already exist.

awslocal dynamodb create-table --table-name Notifications --attribute-definitions AttributeName=pk,AttributeType=S AttributeName=sk,AttributeType=S --key-schema AttributeName=pk,KeyType=HASH AttributeName=sk,KeyType=RANGE --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 --region us-east-1 && echo Successfully created table Notifications. || echo Did not create table Notifications. Table may already exist.

echo Creating local KMS key for pagination serialization

awslocal kms create-key --tags '[{"TagKey":"_custom_id_","TagValue":"00000000-0000-0000-0000-000000000001"}]'