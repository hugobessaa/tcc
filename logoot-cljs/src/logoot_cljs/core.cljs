(ns logoot-cljs.core)

;; (defonce conn
;;   (repl/connect "http://localhost:9000/repl"))

;; (enable-console-print!)

(println "Hello world!")

;; logoot implementation in cljs

;; insert(pos line)
;; delete(pos)

;; a document is defined by lines
;; line: [pid content]
;; a line identifier is a couple (pos, hs)
;; pos: i(0).i(1)....i(n-1)
;; hs: value of logical clock

;; there are two virtual lines
;; lb and le, to represent the beggining and the end of the document
;; we always need to be able the generate a line A where P < A < N

;; pids are unique
;; pids: (pi, si)
;; pi: integer
;; si: site identifier

;; a position is a list of identifier


;; each site has a logical clock, incremented each time a line is created

(def MAX_INT 32767)


(defn compare-pid
  "Compare two pids. If all intersecting pos identifier are equal, the bigger
  pos vector will win."
  [[pos1] [pos2]]
  ;; 1 if (> (nth 0 pos1) (nth 0 pos2)), -1 if not. if 0, will iterate
  ;; going up until the end of the position vector
  (if-let [result (first (filter #(not= 0 %)
                                 (map compare pos1 pos2)))]
    result
    ;; if every pos was the same, let the bigger pos vector win
    ;; e.g. (> [[1 2] [3 4]] [[1 2]]) => true
    (if (> (count pos1) (count pos2))
      1
      -1)))

;; a logoot document would be like

(def document
  ;; a hashmap were keys are the pid of the line
  ;; and the values are the content
  (sorted-map-by
   ;; created with this sort fn
   compare-pid
   ;; vector of pid
   ;;|line |site |clock
   [[[0     0]]   nil]
   ;; content
   :lb

   [[[2 1]] 0]
   "This is an example of a Logoot document"

   [[[1 1] [2 2]] 0]
   "This is a line inserted between [1 1] and [2 2]"

   [[[MAX_INT 0]] nil]
   :le
   ))

(defn create-doc
  "Creates an empty logoot document, with its beggining and finish lines"
  []
  (sorted-map-by
   compare-pid
   [[[0 0]] nil]       :lb
   [[[MAX_INT 0]] nil] :le))

(compare-pid [[[2 4]] 5] [[[2 8] [3 5]] 5])

(defn insert
  [doc pid content]
  (assoc doc pid content))

;; in this case, should this position be even possible? would it be before or after [[2 1]]?
(insert document [[[1 2] [3 4]] 5] "New content")

(defn delete
  [doc pid]
  (dissoc doc pid))

(delete document [[[2 1]] 0])
