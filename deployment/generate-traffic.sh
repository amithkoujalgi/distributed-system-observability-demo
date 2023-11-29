#!/bin/bash

http POST http://localhost:9903/api/trade/order \
  accept:*/\* \
  access-token:c96cf9ae-a0ce-40ba-91b8-8a636a38a87d \
  Content-Type:application/json \
  instrument=AAPL \
  price:=10 \
  quantity:=20 \
  userId=1 \
  type=BUY

http GET http://localhost:9902/api/instrument/stocks \
  accept:application/json \
  access-token:c96cf9ae-a0ce-40ba-91b8-8a636a38a87d