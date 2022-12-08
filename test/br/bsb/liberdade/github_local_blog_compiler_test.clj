(ns br.bsb.liberdade.github-local-blog-compiler-test
  (:require [clojure.test :refer :all]
            [br.bsb.liberdade.github-local-blog-compiler :as compiler]))

(def blog-id "liberdade-organizacao/posts")
(def index-template 
  "<html><body><h1>Posts</h1><ul>%{content}</ul></body></html>")
(def index-post-template 
  "<li><a href=\"%{path}\">%{title}</a>: %{description}</li>")
(def post-template 
  "<html><body>%{content}</body></html>")
(def default-args {:blog-id blog-id
                   :origin :github
                   :index-template index-template
                   :index-post-template index-post-template
                   :post-template post-template})
(def default-offline-args (assoc default-args
                                 :blog-id "./posts"
                                 :origin :local))

(deftest create-new-links
  (testing "Creates new links from old ones"
    (is (= "hello.html" (compiler/make-new-link "hello.md")))
    (is (= "joe.html" (compiler/make-new-link "joe.txt")))
    (is (= "index.html" (compiler/make-new-link "index.html")))))

(deftest github-downloads
  (testing "can donwload files from github"
    (is (some? (compiler/download-from-github blog-id "index.blog.json")))))

(deftest generate-index
  (testing "can generate the index file"
    (let [index (compiler/download-index blog-id)]
      (is (some? (compiler/generate-index-contents index
                                                   index-template
                                                   index-post-template))))))

(deftest link-between-files
  (testing "can replace links between files"
    (let [text "Link to [this file](./page1.md)\nLink to [Another file](./page2.txt)\n"
          links-replacement {"page1.md" "page1.html"
                             "page2.txt" "page2.html"}
          expected-result "Link to [this file](./page1.html)\nLink to [Another file](./page2.html)\n"
          obtained-result (compiler/replace-links text links-replacement)]
      (is (= expected-result obtained-result)))))

(deftest main-flow
  (testing "It can execute the online main flow"
    (let [result (compiler/draw default-args)]
      ; (println result)
      (is (some? result))))
  (testing "It can execute the offline main flow"
    (let [result (compiler/draw default-offline-args)]
      ; (println result)
      (is some? result))))

