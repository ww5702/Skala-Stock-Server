#!/bin/bash
NAME=sk047
IMAGE_NAME="stock-backend"
VERSION="1.0.0"

# Docker 이미지 빌드
docker build \
  --tag ${NAME}-${IMAGE_NAME}:${VERSION} \
  --file Dockerfile \
  ${IS_CACHE} .