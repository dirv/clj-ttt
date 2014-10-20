(ns clj-ttt.board)

(def ^:const unplayed \-)
(def ^:const row-wins '([0 1 2] [3 4 5] [6 7 8]))
(def ^:const col-wins '([0 3 6] [1 4 7] [2 5 8]))
(def ^:const diag-wins '([0 4 8] [2 4 6]))
(def ^:const all-wins (concat row-wins col-wins diag-wins))

(defn create-board [size]
  (apply str (replicate (* size size) unplayed)))

(defn play-move [board square mark]
   (let [bv (vec board)]
     (if (= unplayed (nth bv square))
       (apply str (assoc bv square mark))
       board)))

(defn distinct-square-values [board squares]
  (distinct (map #(get board %) squares)))

(defn single-player-in-subset? [subset]
  (and (= (count subset) 1) (not= unplayed (first subset))))

(defn won-subset? [board squares]
  (single-player-in-subset? (distinct-square-values board squares)))

(defn won? [board]
  (some #(won-subset? board %) all-wins))

(defn playable-squares [board]
  (filter #(= unplayed (get board %)) (range 0 (count board))))

(defn next-player [board]
  "X")
