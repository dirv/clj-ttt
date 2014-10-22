(ns clj-ttt.board)

(def ^:const unplayed \-)

(defn- row-wins [size]
  (map #(range % (+ % size)) (map #(* % size) (range size))))

(defn- col-wins [size]
  (map #(range % (* size size) size) (range size)))

(defn- diag-wins [size]
  [(range 0 (* size size) (inc size))
    (range (dec size) (dec (* size size)) (dec size))])

(defn- all-wins [size]
  (concat (row-wins size) (col-wins size) (diag-wins size)))

(defn create-board [size]
  (apply str (replicate (* size size) unplayed)))

(defn size [board]
  (int (Math/sqrt (count board))))

(defn play-move [board square mark]
   (let [bv (vec board)]
     (if (= unplayed (nth bv square))
       (apply str (assoc bv square (name mark)))
       board)))

(defn- distinct-square-values [board squares]
  (distinct (map #(get board %) squares)))

(defn- single-player-in-subset? [subset]
  (and (= (count subset) 1) (not= unplayed (first subset))))

(defn- won-subset? [board squares]
  (single-player-in-subset? (distinct-square-values board squares)))

(defn won? [board]
  (some #(won-subset? board %) (all-wins (size board))))

(defn playable-squares [board]
  (filter #(= unplayed (get board %)) (range 0 (count board))))

(defn finished? [board]
  (or (won? board) (= 0 (count (playable-squares board)))))

(defn- count-of [mark board]
  (count (filter #(= mark %) (vec board))))

(defn next-player [board]
  (if (> (count-of \X board) (count-of \O board)) :O :X))

(defn if-x-is-next [board x o]
  (if (= :X (next-player board)) x o))

(defn last-player [board]
  (if-x-is-next board :O :X))
