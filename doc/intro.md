# Introduction

## Command Line Interface

The program requires some command line arguments:

``` sh
-b   blog id: will look like "$GITHUB_USERNAME/$REPOSITORY".
-d   path to github blog local directory
-i   index file template: HTML file with index contents
-ip  index post file template: HTML file with contents for each post on indeex
-p   post file template: HTML file wiht contents for each post
```

The program will:

- Either:
  - Download all files present on the `index.blog.json` file
  - Load files in the chosen directory
- Convert markdown and text files to HTML; everything else will be left as is
- Store the files in the current directory.

