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

