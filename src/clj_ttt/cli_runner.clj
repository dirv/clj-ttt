(ns clj-ttt.cli-runner
  (:gen-class)
  (:require [clj-ttt.cli-game :refer :all]
            [clj-ttt.board :refer :all]))

(defn -main []
  (prompt-and-play))
