(ns br.bsb.liberdade.github-local-blog
  (:gen-class)
  (:require [br.bsb.liberdade.github-local-blog-compiler :as compiler]))

; TODO complete me!
(defn parse-args [args]
  {})

; TODO parse command line arguments
; TODO use local directory when required
; TODO give precedence to github download
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
        data (compiler/draw params)]
    (println "--- # github local blog")
    (println (str "blog id: " blog-id))
    (println "generated files:")
    (mapv (fn [[path contents]]
            (do
              (println (str "- " path))
              (spit path contents)))
          data)
    (println "...")))
