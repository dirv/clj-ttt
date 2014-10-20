(ns clj-ttt.cli-runner
  (:gen-class)
  (:require [clj-ttt.cli-game :refer :all]
            [clj-ttt.board :refer :all]))

(defn -main []
  (play-until-finish (create-board 3) 3))
