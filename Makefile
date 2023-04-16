#!make

build:
	mvn clean install

up: build
	docker-compose up --build