(defproject editor "0.2.0-SNAPSHOT"
  :description "A collaborative editor"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/test.check "0.9.0"]
                 [org.clojure/clojurescript "1.9.76"]
                 [binaryage/devtools "0.7.0"]
                 [reagent "0.6.0-rc"]
                 [cljsjs/quill "0.20.0-1"]]

  :plugins [[lein-doo "0.1.6"]
            [lein-figwheel "0.5.4-7"]
            [lein-cljsbuild "1.1.1"]]

  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[figwheel-sidecar "0.5.4-3"]
                                  [com.cemerick/piggieback "0.2.1"]]}
             :user {:plugins [[lein-ancient "0.6.10"]]}}

  :figwheel {:css-dirs ["resources/public/css"]}

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

  :cljsbuild {:builds {:dev  {:source-paths ["src"]
                              :figwheel true
                              :compiler {:main "editor.core"
                                         :output-to "resources/public/js/main.js"
                                         :output-dir "resources/public/js"
                                         :asset-path "js"
                                         :pretty-print true
                                         :source-map-timestamp true}}

                       :test {:compiler     {:main                 editor.runner
                                             :output-to            "resources/public/js/runner.js"
                                             :output-dir           "resources/public/js/test"
                                             :optimizations        :none
                                             :source-map-timestamp true}
                              :source-paths ["src" "test"]}}}

  :clean-targets ^{:protect false} ["resources/public/js"
                                    :target-path])
