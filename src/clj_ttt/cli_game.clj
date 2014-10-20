(ns clj-ttt.cli-game
  (:require [clj-ttt.board :refer :all]))

(defn board-string [board]
  (clojure.string/join " " (map #(str ( inc %)) (playable-squares board))))
