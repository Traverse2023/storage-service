#!/bin/bash

echo Creating Messages and Notifications dynamodb tables...

aws dynamodb create-table --table-name Messages --attribute-definitions AttributeName=pk,AttributeType=S AttributeName=sk,AttributeType=S --key-schema AttributeName=pk,KeyType=HASH AttributeName=sk,KeyType=RANGE --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 --endpoint-url "${DYNAMODB_ENDPOINT}" --region us-east-1 && echo Successfully creted table Messages. || echo Did not create table Messages. Table may already exist.

aws dynamodb create-table --table-name Notifications --attribute-definitions AttributeName=pk,AttributeType=S AttributeName=sk,AttributeType=S --key-schema AttributeName=pk,KeyType=HASH AttributeName=sk,KeyType=RANGE --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 --endpoint-url "${DYNAMODB_ENDPOINT}" --region us-east-1 && echo Successfully creted table Notifications. || echo Did not create table Notifications. Table may already exist.


