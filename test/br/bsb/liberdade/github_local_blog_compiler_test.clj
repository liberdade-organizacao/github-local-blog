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
                   :index-template index-template
                   :index-post-template index-post-template
                   :post-template post-template})

(deftest github-downloads
  (testing "can donwload files from github"
    (is (some? (compiler/download-from-github blog-id "index.blog.json")))))

(deftest generate-index
  (testing "can generate the index file"
    (let [index (compiler/load-index blog-id)]
      (is (some? (compiler/generate-index-contents index
                                                   index-template
                                                   index-post-template))))))

(deftest main-flow
  (testing "It can execute the main flow"
    (let [result (compiler/download-and-compile default-args)]
      (println result)
      (is (some? result)))))
