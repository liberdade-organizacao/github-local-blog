package github_local_blog

import (
	"testing"
)

func TestConvertFromMarkdownToHTML(t *testing.T) {
	md := "# Test\n\nHello Joe!"
	expectedResult := "<h1 id=\"test\">Test</h1>\n\n<p>Hello Joe!</p>\n"
	result := MarkdownToHTML(md)
	if result != expectedResult {
		t.Fatalf("Couldn't convert properly from Markdown to HTML\nExpected:\n```\n%s\n```\nGotten:\n```\n%s\n```", expectedResult, result)
	}
}

func TestCanDownloadBlogIndex(t *testing.T) {
	blogId := "liberdade-organizacao/posts"
	_, ok := DownloadIndex(blogId)
	if ok != nil {
		t.Fatalf("Couldn't download index: %s", ok)
	}
}

func TestMainProcedure(t *testing.T) {
	blogId := "liberdade-organizacao/posts"
	indexTemplate := "<html><head></head><body><ul>{{index}}</ul></body></html>"
	indexPostTemplate := "<li><a href=\"./{{path}}\">{{title}}<></li>"
	postTemplate := "<html><head></head><body>{{content}}</body></html>"
	_, ok := DownloadAndCompile(blogId, indexTemplate, indexPostTemplate, postTemplate)
	if ok != nil {
		t.Fatalf("Couldn't download and compile: %s", ok)
	}
}

