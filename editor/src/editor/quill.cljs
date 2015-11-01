(ns editor.quill
  (:require [cljsjs.quill]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]))

(defui Editor
  Object
  (componentDidMount [this]
                     (let [props (om/props this)
                           {:keys [on-text-change content]} props
                           editor (js/Quill. "#editor")]
                       (when on-text-change
                         (.on editor "text-change" on-text-change))
                       (when content
                         (.setText editor content))
                       (om/set-state! this {:editor editor})))
  (componentWillReceiveProps [this next]
                             (when (not= (-> this om/props :content)
                                         (-> next :content))
                               (let [editor (om/get-state this :editor)
                                     selection (.getSelection editor)]
                                 (.setText editor (:content next))
                                 (.setSelection editor selection))))
  (render [_]
          (dom/div #js {:id "editor"} nil)))

