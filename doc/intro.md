# Introduction

## Command Line Interface

The program requires 4 command line arguments:

``` sh
lein run $GITHUB_BLOG_ID $INDEX_TEMPLATE_PATH $INDEX_POST_TEMPLATE_PATH $POST_TEMPLATE_PATH
```

where

```
$GITHUB_BLOG_ID is the identifier for the Github blog that will be compiled . It will look like "$USERNAME/$REPOSITORY".

$INDEX_TEMPLATE_PATH is the path to an HTML file with the template for the index.

$INDEX_POST_TEMPLATE_PATH is the path to an HTML with the template for each post's entry in the index file.

$POST_TEMPLATE_PATH is the path to an HTML with the template for each post.
```

The program will:

- Download all files present on the `index.blog.json` file
- Convert markdown and text files to HTML; everything else will be left as is
- Store the files in the current directory.

