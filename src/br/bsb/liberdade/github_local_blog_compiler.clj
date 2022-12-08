(ns br.bsb.liberdade.github-local-blog-compiler
  (:gen-class)
  (:require [clojure.string :as s]
            [clojure.data.json :as json]
            [markdown.core :as markdown]
            [br.bsb.liberdade.strint :as strint]))

(def default-index-file "index.blog.json")
(def text-files-regex #"(\.md|\.txt)")

(defn download-from-github [blog-id filename]
  (slurp (str "https://raw.githubusercontent.com/"
              blog-id
             "/master/"
              filename)))

(defn download-index [blog-id]
  (-> blog-id
      (download-from-github default-index-file)
      json/read-str))

(defn make-new-link [old-link]
  (s/replace old-link text-files-regex ".html"))

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

(defn generate-post-from-github [blog-id index-post post-template]
  (let [path (get index-post "path")
        raw-post (download-from-github blog-id path)]
    (cond (re-find text-files-regex path)
            (markdown/md-to-html-string raw-post)
          :else raw-post)))

(defn download-and-compile 
  [blog-id index index-template index-post-template post-template]
  (reduce (fn [outlet index-post]
            (assoc outlet 
                   (make-new-link (get index-post "path"))
                   (generate-post-from-github blog-id index-post post-template)))
          {"index.html" (generate-index-contents index 
                                                 index-template 
                                                 index-post-template)}
          index))

(defn read-index [blog-path]
  (-> blog-path
      (str "/" default-index-file)
      slurp
      json/read-str))

(defn generate-post [blog-path index-post post-template]
  (let [path (str blog-path "/" (get index-post "path"))
        raw-post (slurp path)]
    (if (re-find text-files-regex path)
      (markdown/md-to-html-string raw-post)
      raw-post)))

(defn read-and-compile 
  [blog-path index index-template index-post-template post-template]
  (reduce (fn [outlet index-post]
            (assoc outlet
                   (-> index-post (get "path") make-new-link)
                   (generate-post blog-path index-post post-template)))
          {"index.html" (generate-index-contents index
                                                 index-template
                                                 index-post-template)}
          index))

(defn generate-links-for-replacement [index]
  (reduce (fn [state index-post]
            (let [old-link (get index-post "path")]
              (assoc state
                     old-link
                     (make-new-link old-link))))
          {}
          index))

(defn replace-links [text links-replacement]
  (reduce (fn [inlet [from-link to-link]]
            (s/replace inlet from-link to-link))
          text
          links-replacement))

(defn replace-all-links [inlet links-replacement]
  (reduce (fn [outlet [filename contents]]
            (assoc outlet 
                   filename 
                   (replace-links contents
                                  links-replacement)))
          {}
          inlet))

(defn draw [params]
  (let [origin (get params :origin)
        blog-id (get params :blog-id)
        index-template (get params :index-template)
        index-post-template (get params :index-post-template)
        post-template (get params :post-template)
        index (if (= origin :github)
                (download-index blog-id)
                (read-index blog-id))
        main-operation (if (= origin :github)
                         download-and-compile
                         read-and-compile)]
    (-> (main-operation blog-id 
                        index
                        index-template
                        index-post-template
                        post-template)
        (replace-all-links (generate-links-for-replacement index)))))

