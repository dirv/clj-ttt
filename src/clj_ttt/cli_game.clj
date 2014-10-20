(ns clj-ttt.cli-game
  (:require [clj-ttt.board :refer :all]))

(defn output-board-line [square-strings]
  (str (clojure.string/join " " square-strings) "\n"))

(defn number-or-mark [index mark playable-squares]
  (if (some #{index} playable-squares)
    (str (inc index))
    mark))

(defn numbers-or-marks [board]
  (map-indexed #(number-or-mark %1 %2 (playable-squares board)) board))

(defn board-string [board]
  (apply str (map output-board-line (partition (size board) (numbers-or-marks board)))))

(defn play-message [board]
  (if (won? board)
    (str (last-player board) " wins!")
    (if (finished? board)
      (str "It's a draw!")
      (str (next-player board) "'s go, choose a square: "))))

(defn display [board]
  (str (board-string board) (play-message board)))

(defn read-move []
  (dec (read-string (read-line))))

(defn print-board [board]
  (println (display board)))

(defn play-cli-move [board]
  (print-board board)
  (play-move board (read-move) (next-player board)))

(defn play-until-finish [board]
  (let [new-board (play-cli-move board)]
    (if (finished? new-board)
      (print-board new-board)
      (play-until-finish new-board))))
