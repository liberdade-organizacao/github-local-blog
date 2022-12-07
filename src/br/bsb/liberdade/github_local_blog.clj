(ns br.bsb.liberdade.github-local-blog
  (:gen-class)
  (:require [br.bsb.liberdade.github-local-blog-compiler :as compiler]))

(defn -main
  "Receives the blog id; and templates for the index, index posts, and post files"
  [& args]
  (let [blog-id (first args)
        index-template-path (nth args 1)
        index-post-template-path (nth args 2)
        post-template-path (nth args 3)
        index-template (slurp index-template-path)
        index-post-template (slurp index-post-template-path)
        post-template (slurp post-template-path)
        params {:blog-id blog-id
                :index-template index-template
                :index-post-template index-post-template
                :post-template post-template}
        data (compiler/download-and-compile params)]
    (println "--- # github local blog")
    (println (str "blog id: " blog-id))
    (mapv (fn [[path contents]]
            (spit path contents))
          data)
    (println "...")))
