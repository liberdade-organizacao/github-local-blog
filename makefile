.PHONY: default
default: run

.PHONY: test
test:
	go test

.PHONY: build
build: test
	go build -o main.exe main/main.go

.PHONY: run
run: build
	./main.exe
	rm main.exe

