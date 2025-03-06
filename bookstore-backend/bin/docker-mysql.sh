#!/bin/sh

docker run -d \
  --name mysql-container \
  -e MYSQL_ROOT_PASSWORD=admin \
  -e MYSQL_DATABASE=bookstore \
  -e MYSQL_USER=admin \
  -e MYSQL_PASSWORD=admin \
  -p 3306:3306 \
  mysql:latest