(ns clj-ttt.cli-game
  (:require [clj-ttt.board :refer :all]))

(defn output-board-line [square-strings]
  (str (clojure.string/join " " square-strings) "\n"))

(defn number-or-mark [mark index playable-squares]
  (if (some #{index} playable-squares)
    (str (inc index))
    mark
    ))

(defn numbers-or-marks [board]
  (let [playable-squares (playable-squares board)]
    (map-indexed (fn [index mark] (number-or-mark mark index playable-squares)) (seq board))))

(defn board-string [board size]
  (let [square-strings (numbers-or-marks board)]
    (apply str (map output-board-line (vec (partition size square-strings))))
    ))
