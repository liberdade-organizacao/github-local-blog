package github_local_blog

import (
	"github.com/gomarkdown/markdown"
	"github.com/gomarkdown/markdown/parser"
	"errors"
)

/*
Compiles Markdown to HTML.
*/
func MarkdownToHTML(md string) string {
	extensions := parser.CommonExtensions | parser.AutoHeadingIDs
	parser := parser.NewWithExtensions(extensions)
	output := markdown.ToHTML([]byte(md), parser, nil)
	return string(output[:])
}

/*
Downloads blog index and stores its data in a Go data structure. 

Each post's main fields should be:
- link: post's file name
- title: the post's 
- description: post description
*/
func DownloadIndex(blogId string) ([]map[string]interface{}, error) {
	// TODO complete me!
	return nil, errors.New("Not implemented yet!")
}

/*
Downloads blog from an ID and populates the template strings with compiled content.
Returns a map relating the new file name to their contents.
*/
func DownloadAndCompile(blogId, indexTemplate, indexPostTemplate, postTemplate string) (map[string]string, error) {
	// TODO complete me!
	// TODO download blog index
	// TODO fill index template
	// TODO download posts
	// TODO fill post template
	return nil, errors.New("Not implemented yet!")
}

