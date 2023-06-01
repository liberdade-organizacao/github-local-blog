(defproject br.bsb.liberdade.github-local-blog "0.0.2"
  :description "Generate local github blog site"
  :url "http://www.liberdade.bsb.br"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/data.json "2.4.0"]
                 [markdown-clj "1.11.4"]
                 [org.clojars.liberdade/strint "0.0.1"]]
  :main ^:skip-aot br.bsb.liberdade.github-local-blog
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
