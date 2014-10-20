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

(defn board-string [board size]
  (apply str (map output-board-line (partition size (numbers-or-marks board)))))

(defn play-message [board]
  (if (won? board)
    (str (last-player board) " wins!")
    (str (next-player board) "'s go, choose a square: ")))

(defn display [board size]
  (str (board-string board size) (play-message board)))

(defn read-move []
  (dec (read-string (read-line))))

(defn play-cli-move [board size]
  (println (display board size))
  (play-move board (read-move) (next-player board)))

