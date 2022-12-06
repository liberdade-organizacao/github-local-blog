.PHONY: default
default: run

.PHONY: build
build:
	go build -o main.exe main/main.go

.PHONY: run
run: build
	./main.exe
	rm main.exe

