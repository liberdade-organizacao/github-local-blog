(ns br.bsb.liberdade.github-local-blog-test
  (:require [clojure.test :refer :all]
            [br.bsb.liberdade.github-local-blog :refer :all]))

(deftest parse-args-verification
  (testing "can parse arguments for Github download"
    (let [args ["-b" "liberdade-organizacao/posts"
                "-i" "index.template.html"
                "-ip" "index_post.template.html"
                "-p" "post.template.html"]
          expected-params {:blog-id "liberdade-organizacao/posts"
                           :index-template-path "index.template.html"
                           :index-post-template-path "index_post.template.html"
                           :post-template-path "post.template.html"}]
      (is (= expected-params (parse-args args)))))
  (testing "can parse arguments for local operation"
    (let [args ["-d" "./posts"
                "-i" "index.template.html"
                "-ip" "index_post.template.html"
                "-p" "post.template.html"]
          expected-params {:directory "./posts"
                           :index-template-path "index.template.html"
                           :index-post-template-path "index_post.template.html"
                           :post-template-path "post.template.html"}]
      (is (= expected-params (parse-args args)))))
 (testing "can give precedence over Github downloads"
    (let [args ["-d" "./posts"
                "-b" "liberdade-organizacao/posts"
                "-i" "index.template.html"
                "-ip" "index_post.template.html"
                "-p" "post.template.html"]
          expected-params {:blog-id "liberdade-organizacao/posts"
                           :directory nil
                           :index-template-path "index.template.html"
                           :index-post-template-path "index_post.template.html"
                           :post-template-path "post.template.html"}]
      (is (= expected-params (parse-args args))))))

