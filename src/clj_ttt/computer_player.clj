(ns clj-ttt.computer-player
  (:require [clj-ttt.board :refer :all]))

(def ^:const no-score {:score 0 :board nil})

(defn score [board depth]
  (if (won? board)
    depth
    0))

(declare play)

(defn best-move [current board depth]
  (let
    [next-player (next-player board)
     new-move (play board next-player (dec depth))
     new-score (- (:score new-move))]
    (if (> new-score (:score current))
      {:score new-score :board board}
      current)))


(defn all-moves [board mark squares]
  (map #(play-move board % mark) squares))

(defn play [board mark depth]
  (if (or (= 0 depth) (finished? board))
    {:score (- (score board depth)) :board board}
    (reduce #(best-move %1 %2 depth)
            no-score
            (all-moves board mark (playable-squares board)))))

(defn choose-next-move [board mark]
  (:board (play board mark (count (playable-squares board)))))
