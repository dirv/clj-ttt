(ns clj-ttt.computer-player
  (:require [clj-ttt.board :refer :all]))

(def ^:const infinity 1000)

(defn score [board depth]
  (if (won? board) (- depth) 0))

(defn all-moves [board mark squares]
  (map #(play-move board % mark) squares))

(defn initial-score [alpha beta]
  {:score alpha :board nil :alpha alpha :beta beta})

(defn play [board mark depth alpha beta]
  (if (or (= 0 depth) (finished? board))
    {:score (score board depth) :board board :alpha alpha :beta beta}

    (let [next-player (last-player board)
          next-depth (dec depth)]
      (reduce (fn [best-move board]
              (if (>= (:alpha best-move) (:beta best-move))
                (reduced best-move)
                (let [new-alpha (- (:beta best-move))
                      new-beta (- (:alpha best-move))
                      new-move (play board next-player next-depth new-alpha new-beta)
                      new-score (- (:score new-move))]
                  (if (> new-score (:score best-move))
                    {:score new-score :board board :alpha new-score :beta (:beta best-move)}
                    best-move))))
            (initial-score alpha beta)
            (all-moves board mark (playable-squares board))))))

(defn choose-next-move [board mark]
  (:board (play
            board
            mark
            (inc (count (playable-squares board)))
            (- infinity)
            infinity)))

