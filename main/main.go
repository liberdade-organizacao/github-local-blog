package main

import (
	"github.com/gomarkdown/markdown"
	"github.com/gomarkdown/markdown/parser"
	"fmt"
)

func main() {
	extensions := parser.CommonExtensions | parser.AutoHeadingIDs
	parser := parser.NewWithExtensions(extensions)
	md := []byte("# Test\n\nHello Joe!")
	output := markdown.ToHTML(md, parser, nil)
	fmt.Println(string(output[:]))
}

