(ns clj-ttt.computer-player
  (:require [clj-ttt.board :refer :all]))

(def ^:const no-score {:score -1 :board nil :alpha -1000 :beta 1000})

(defn score [board depth]
  (if (won? board)
    depth
    0))

(defn all-moves [board mark squares]
  (map #(play-move board % mark) squares))

(declare play)

(defn best-move [current board depth]
  (let
    [next-player (next-player board)
     new-move (play board next-player (dec depth))
     new-score (- (:score new-move))]
    (if (> new-score (:score current))
      {:score new-score :board board :alpha -1000 :beta 1000}
      current)))

(defn play [board mark depth]
  (if (or (= 0 depth) (finished? board))
    {:score (- (score board depth)) :board board}
    (reduce (fn [current board] 
              (if (>= (:alpha current) (:beta current))
                (reduced current)
                (best-move current board depth)))
            no-score
            (all-moves board mark (playable-squares board)))))

(defn choose-next-move [board mark]
  (:board (play board mark (inc (count (playable-squares board))))))
