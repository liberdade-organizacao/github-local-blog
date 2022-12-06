package main

import (
	"fmt"
	"github.com/liberdade-organizacao/github-local-blog"
)

func main() {
	md := "# Test\n\nHello Joe!"
	html := github_local_blog.MarkdownToHTML(md)
	fmt.Println(html)
}

