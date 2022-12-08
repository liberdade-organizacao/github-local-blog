(ns br.bsb.liberdade.github-local-blog-compiler
  (:gen-class)
  (:require [clojure.data.json :as json]
            [markdown.core :as markdown]
            [br.bsb.liberdade.strint :as strint]))

(def default-index-file "index.blog.json")
(def text-files-regex #"(\.md|\.txt)")

(defn download-from-github [blog-id filename]
  (slurp (str "https://raw.githubusercontent.com/"
              blog-id
             "/master/"
              filename)))

(defn load-index [blog-id]
  (-> blog-id
      (download-from-github default-index-file)
      json/read-str))

(defn make-new-link [old-link]
  (clojure.string/replace old-link text-files-regex ".html"))

(defn- make-index-post [index-post index-post-template]
  (strint/strint index-post-template 
                 (update index-post "path" make-new-link)))

(defn generate-index-contents [index index-template index-post-template]
  (strint/strint index-template
                 {"content" (->> index
                                 (map #(make-index-post % index-post-template))
                                 (reduce (fn [state item]
                                           (str state item)) 
                                         ""))}))

(defn generate-post [blog-id index-post post-template]
  (let [path (get index-post "path")
        raw-post (download-from-github blog-id path)]
    (cond (re-find text-files-regex path)
            (markdown/md-to-html-string raw-post)
          :else raw-post)))

(defn download-and-compile 
  [{:keys [blog-id index-template index-post-template post-template]}]
  (let [index (load-index blog-id)]
    (reduce (fn [outlet index-post]
              (assoc outlet 
                     (make-new-link (get index-post "path"))
                     (generate-post blog-id index-post post-template)))
            {"index.html" (generate-index-contents index 
                                                   index-template 
                                                   index-post-template)}
            index)))

