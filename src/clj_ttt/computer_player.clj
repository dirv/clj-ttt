(ns clj-ttt.computer-player
  (:require [clj-ttt.board :refer :all]))

(def ^:const infinity 1000)

(defn score [board depth]
  (if (won? board) depth 0))

(defn all-moves [board mark squares]
  (map #(play-move board % mark) squares))

(defn initial-score [alpha beta]
  {:score alpha :board nil :alpha alpha :beta beta})

(declare play)

(defn best-move [current board depth]
  (let
    [next-player (next-player board)
     new-move (play board next-player (dec depth) (- (:beta current)) (- (:alpha current)))
     new-score (- (:score new-move))]
    (if (> new-score (:score current))
      {:score new-score :board board :alpha new-score :beta (:beta current)}
      current)))

(defn play [board mark depth alpha beta]
  (if (or (= 0 depth) (finished? board))
    {:score (- (score board depth)) :board board :alpha alpha :beta beta}
    (reduce (fn [current board]
              (if (>= (:alpha current) (:beta current))
                (reduced current)
                (best-move current board depth)))
            (initial-score alpha beta)
            (all-moves board mark (playable-squares board)))))

(defn choose-next-move [board mark]
  (:board (play
            board
            mark
            (inc (count (playable-squares board)))
            (- infinity)
            infinity)))
