(ns clj-ttt.cli-game
  (:require [clj-ttt.board :refer :all]))

(defn output-board-line [square-strings]
  (str (clojure.string/join " " (map #(str (inc %)) square-strings)) "\n"))

(defn board-string [board size]
  (let [squares (playable-squares board)]
    (apply str (map output-board-line (vec (partition size squares))))
    ))

